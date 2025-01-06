package gestionInventario.com.repository;

import gestionInventario.com.http.PurchasedProduct;
import gestionInventario.com.model.entity.Cart;
import gestionInventario.com.model.entity.utils.CustomerProductId;
import gestionInventario.com.model.enu.cart.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICartRepository extends JpaRepository<Cart, CustomerProductId> {
    @Query("SELECT c FROM Cart c WHERE c.id.idCustomer = :idCustomer AND c.cartStatus = 'IN_PROGRESS'")
    List<Cart> findCartsInProgress(@Param("idCustomer") Long idCustomer);

    @Query("SELECT c FROM Cart c WHERE c.id.idCustomer = :idCustomer AND c.cartStatus = 'FINISHED'")
    List<Cart> getCartsFinished(@Param("idCustomer") Long idCustomer);

    @Query("SELECT new gestionInventario.com.http.PurchasedProduct(p.name, p.price, c.quantity) " +
            "FROM Product p JOIN p.carts c " +
            "WHERE c.id.idCustomer = :idCustomer AND c.cartStatus = :cartStatus")
    List<PurchasedProduct> findProductsByCustomer(@Param("idCustomer") Long idCustomer,
                                                  @Param("cartStatus")CartStatus cartStatus);

    @Query("SELECT SUM(c.priceTotal) FROM Cart c  where c.id.idCustomer = :idCustomer " +
            "AND c.cartStatus = :cartStatus")
    Double findTotalSpentOfCartBuy(@Param("idCustomer") Long idCustomer,
                                   @Param("cartStatus")CartStatus cartStatus);
}