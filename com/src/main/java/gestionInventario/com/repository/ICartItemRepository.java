package gestionInventario.com.repository;

import gestionInventario.com.model.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartItemRepository extends JpaRepository<CartItem,Long> {
    @Query("SELECT SUM(ci.priceItem) " +
            "FROM CartItem ci " +
            "WHERE ci.cart.id = :idCart")
    Double calcAmountCart(@Param("idCart") Long idCart);
}