package gestionInventario.com.service.implementations;

import gestionInventario.com.exception.NotFoundException;
import gestionInventario.com.model.dto.purchasedProduct.PurchasedProductResponseDTO;
import gestionInventario.com.model.dto.purchasedProduct.PurchasedProductDTO;
import gestionInventario.com.model.entity.UserEntity;
import gestionInventario.com.model.entity.OrderEntity;
import gestionInventario.com.model.enumerator.cart.PurchaseStatus;
import gestionInventario.com.notification.NotificationService;
import gestionInventario.com.repository.IPurchaseRepository;
import gestionInventario.com.repository.IUserRepository;
import gestionInventario.com.repository.IOrderRepository;
import gestionInventario.com.service.interfaces.IOrderService;
import jakarta.transaction.Transactional;
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
    IPurchaseRepository purchaseRepository;
    IUserRepository customerRepository;
    IOrderRepository orderRepository;
    NotificationService notificationService;

    @Override
    @Transactional
    public void createOrder(Long idCustomer){
        UserEntity customer = customerRepository.findById(idCustomer)
                .orElseThrow(() -> new NotFoundException("Customer with id: "+idCustomer+ ", not found"));

        purchaseRepository.doPurchase(idCustomer);
        Double orderPrice = purchaseRepository.findTotalAmountCart(idCustomer);
        Integer quantity = purchaseRepository.findTotalQuantityToBuy(idCustomer);

        OrderEntity order = OrderEntity
                .builder()
                .customer(customer)
                .address(customer.getAddress())
                .priceTotal(orderPrice)
                .quantity(quantity)
                .date(LocalDate.now())
                .build();

        orderRepository.save(order);
        notificationService.sendWelcomeEmail("pardofede04@gmail.com,",
               "Your order has been successfully placed. Thank you very much",
                customer.getUsername());

    }

    @Override
    public PurchasedProductResponseDTO getOrderHistory(Long idCustomer) {
        List<PurchasedProductDTO> products = purchaseRepository.findProductsByCustomer(idCustomer, PurchaseStatus.FINISHED);

        UserEntity customer = customerRepository.findById(idCustomer).orElseThrow(()->
                new NotFoundException("don't founded customer with id: "+idCustomer));
        Double totalSpent = purchaseRepository.findTotalSpentOfCartBuy(idCustomer, PurchaseStatus.FINISHED);

        return PurchasedProductResponseDTO.builder()
                .nameCustomer(customer.getUsername())
                .products(products)
                .totalSpent(totalSpent)
                .build();
    }

}