package gestionInventario.com.service.implementations;

import gestionInventario.com.exception.NotFoundException;
import gestionInventario.com.http.BuyCartResponse;
import gestionInventario.com.http.PurchasedProduct;
import gestionInventario.com.model.entity.Cart;
import gestionInventario.com.model.entity.Customer;
import gestionInventario.com.model.entity.OrderEntity;
import gestionInventario.com.model.enumerator.cart.CartStatus;
import gestionInventario.com.notification.NotificationService;
import gestionInventario.com.repository.ICartRepository;
import gestionInventario.com.repository.ICustomerRepository;
import gestionInventario.com.repository.IOrderRepository;
import gestionInventario.com.service.interfaces.IOrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class OrderServiceImpl implements IOrderService {
    ICartRepository cartRepository;
    ICustomerRepository customerRepository;
    IOrderRepository orderRepository;

    NotificationService notificationService;

    //refactoriazar para que tenga menos lineas
    @Override
    public void createOrder(Long idCustomer){
        Customer customer = customerRepository.findById(idCustomer)
                .orElseThrow(() -> new NotFoundException("Customer with id: "+idCustomer+ ", not found"));
        List<Cart> carts = cartRepository.findCartsInProgress(idCustomer);

        Double orderPrice = 0.0;
        Integer quantity = 0;

        for (Cart cart : carts) {
            orderPrice += cart.getPriceTotal();
            cart.setCartStatus(CartStatus.FINISHED);
            quantity += cart.getQuantity();
        }

        OrderEntity order = OrderEntity
                .builder()
                .customer(customer)
                .address(customer.getAddress())
                .priceTotal(orderPrice)
                .quantity(quantity)
                .date(LocalDate.now())
                .build();

        notificationService.sendEmail("federicopardo944@gmail.com,",
                "Pedido realizado con exito","Gracias por comprar en nuestra pagina");

        orderRepository.save(order);
    }

    @Override
    public BuyCartResponse getPurchasedHistory(Long idCustomer) {
        List<PurchasedProduct> products = cartRepository.findProductsByCustomer(idCustomer,CartStatus.FINISHED);

        Customer customer = customerRepository.findById(idCustomer).orElseThrow(()->
                new NotFoundException("don't founded customer with id: "+idCustomer));
        Double totalSpent = cartRepository.findTotalSpentOfCartBuy(idCustomer,CartStatus.FINISHED);

        return BuyCartResponse.builder()
                .nameCustomer(customer.getUsername())
                .products(products)
                .totalSpent(totalSpent)
                .build();
    }

}