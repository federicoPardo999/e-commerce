package gestionInventario.com.service.implementations;

import gestionInventario.com.exception.NotFoundException;
import gestionInventario.com.mapper.order.OrderItemMapper;
import gestionInventario.com.mapper.order.OrderMapper;
import gestionInventario.com.model.dto.order.OrderItemResponseDTO;
import gestionInventario.com.model.dto.order.OrderResponseDTO;

import gestionInventario.com.model.entity.*;
import gestionInventario.com.model.enumerator.cart.CartStatus;
import gestionInventario.com.repository.ICartItemRepository;
import gestionInventario.com.repository.ICartRepository;
import gestionInventario.com.repository.IOrderRepository;
import gestionInventario.com.repository.IUserRepository;
import gestionInventario.com.service.interfaces.IOrderService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.query.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class OrderServiceImpl implements IOrderService {
    IOrderRepository orderRepository;
    ICartRepository cartRepository;
    IUserRepository userRepository;
    ICartItemRepository cartItemRepository;
    OrderItemMapper orderItemMapper;
    OrderMapper orderMapper;

    @Override
    @Transactional
    public void createOrder(Long cartId,Long idCustomer) {

        Cart cart = findCart(cartId);
        UserEntity user = findUser(idCustomer);
        Double orderAmount = cartItemRepository.calcAmountCart(cartId);

        OrderEntity order = OrderEntity.builder()
                .ordersItems(orderItemMapper.itemCartsToOrderItems(cart.getCartItems()))
                .user(user)
                .priceTotal(orderAmount)
                .date(LocalDate.now())
                .build();

        cart.setCartStatus(CartStatus.COMPLETED);

        cartRepository.save(cart);
        orderRepository.save(order);

    }

    @Override
    public List<OrderResponseDTO> getOrderHistory(Long idCustomer) {
        UserEntity user = findUser(idCustomer);
        Set<OrderEntity> orders = user.getOrders();
        List<OrderResponseDTO> ordersDTO  = orderMapper.ordersToResponseDTO(orders);
        return ordersDTO;
    }

    private UserEntity findUser(Long idCustomer) {
        return userRepository.findById(idCustomer).orElseThrow(
                () -> new NotFoundException("user not founded")
        );
    }

    private Cart findCart(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(
                () -> new NotFoundException("cart not founded")
        );
    }

}