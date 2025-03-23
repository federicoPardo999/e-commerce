package gestionInventario.com.repository;

import gestionInventario.com.model.entity.Cart;
import gestionInventario.com.model.entity.CartItem;
import gestionInventario.com.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.user.id = :customerId AND c.cartStatus = 'ACTIVE'")
    Optional<Cart> findActiveCartByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :idCart")
    List<CartItem> findItems(@Param("idCart") Long idCart);
}