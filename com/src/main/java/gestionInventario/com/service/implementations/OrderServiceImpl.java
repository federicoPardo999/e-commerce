package gestionInventario.com.service.implementations;

import gestionInventario.com.exception.NotFoundException;
import gestionInventario.com.mapper.order.OrderItemMapper;
import gestionInventario.com.mapper.order.OrderMapper;
import gestionInventario.com.model.dto.order.OrderResponseDTO;

import gestionInventario.com.model.entity.*;
import gestionInventario.com.model.enumerator.cart.CartStatus;
import gestionInventario.com.repository.ICartItemRepository;
import gestionInventario.com.repository.ICartRepository;
import gestionInventario.com.repository.IOrderRepository;
import gestionInventario.com.repository.IUserRepository;
import gestionInventario.com.service.interfaces.IOrderItemService;
import gestionInventario.com.service.interfaces.IOrderService;
import gestionInventario.com.service.interfaces.IProductService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class OrderServiceImpl implements IOrderService {
    IOrderRepository orderRepository;
    ICartRepository cartRepository;
    IUserRepository userRepository;
    ICartItemRepository cartItemRepository;
    IOrderItemService orderItemService;
    IProductService productService;
    OrderMapper orderMapper;

    @Override
    @Transactional
    public void createOrder(Long cartId,Long idCustomer) {
        Cart cart = getCart(cartId);
        UserEntity user = getUser(idCustomer);
        Double orderAmount = cartItemRepository.calcAmountCart(cartId);
        Set<CartItem> purchaseds = cart.getCartItems();

        OrderEntity order = OrderEntity.builder()
                .user(user)
                .priceTotal(orderAmount)
                .date(LocalDate.now())
                .build();

        cart.setCartStatus(CartStatus.COMPLETED);
        cartRepository.save(cart);
        orderItemService.addOrderItems(purchaseds,order);
        orderRepository.save(order);
        productService.decreaseStock(purchaseds);
    }

    @Override
    public List<OrderResponseDTO> getOrderHistory(Long idCustomer) {
        UserEntity user = getUser(idCustomer);
        Set<OrderEntity> orders = user.getOrders();
        List<OrderResponseDTO> ordersDTO  = orderMapper.ordersToResponseDTO(orders);
        return ordersDTO;
    }

    private UserEntity getUser(Long idCustomer) {
        return userRepository.findById(idCustomer).orElseThrow(
                () -> new NotFoundException("user not founded")
        );
    }

    private Cart getCart(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(
                () -> new NotFoundException("cart not founded")
        );
    }

}