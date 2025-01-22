package gestionInventario.com.repository;

import gestionInventario.com.model.dto.purchasedProduct.PurchasedProductDTO;
import gestionInventario.com.model.entity.PurchasedProduct;
import gestionInventario.com.model.enumerator.cart.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPurchasedProductRepository extends JpaRepository<PurchasedProduct, PurchasedProduct> {

    @Query("SELECT pp FROM PurchasedProduct pp where pp.customer.id = :customerId " +
            "AND pp.product.id = :productId")
    PurchasedProduct findPurchasedProduct(@Param("customerId") Long customerId,
                                          @Param("productId") Long productId);

    @Query("SELECT pp FROM PurchasedProduct pp WHERE pp.customer.id = :idCustomer AND pp.cartStatus = 'IN_PROGRESS'")
    List<PurchasedProduct> findCartsInProgress(@Param("idCustomer") Long idCustomer);

    @Query("SELECT pp FROM PurchasedProduct pp WHERE pp.customer.id = :idCustomer AND pp.cartStatus = 'FINISHED'")
    List<PurchasedProduct> getCartsFinished(@Param("idCustomer") Long idCustomer);

    @Query("SELECT new  gestionInventario.com.model.dto.purchasedProduct.PurchasedProductDTO" +
            "(p.name, p.price, pp.quantity,p.price * pp.quantity) " +
            "FROM Product p JOIN p.purchasedProducts pp " +
            "WHERE pp.customer.id = :idCustomer AND pp.cartStatus = :cartStatus")
    List<PurchasedProductDTO> findProductsByCustomer(@Param("idCustomer") Long idCustomer,
                                                     @Param("cartStatus")CartStatus cartStatus);

    @Query("SELECT sum(p.price * pp.quantity) FROM PurchasedProduct pp JOIN pp.product p  where pp.customer.id = :idCustomer " +
            "AND pp.cartStatus = :cartStatus")
    Double findTotalSpentOfCartBuy(@Param("idCustomer") Long idCustomer,
                                   @Param("cartStatus")CartStatus cartStatus);

    @Modifying
    @Query("UPDATE PurchasedProduct pp SET pp.cartStatus = 'CANCELED' WHERE pp.cartStatus = 'IN_PROGRESS' AND pp.product.id = :idProduct AND pp.customer.id = :idCustomer")
    void deleteCart(Long idCustomer, Long idProduct);

    @Query("SELECT p.price * pp.quantity FROM PurchasedProduct pp JOIN pp.product p" +
            " where pp.customer.id = :idCustomer" +
            " AND pp.product.id = :idProduct" +
            " AND pp.cartStatus = :cartStatus")
    Double findTotalSpentOfIndividualBuy(@Param("idCustomer") Long idCustomer,
                                         @Param("idProduct") Long idProduct,
                                         @Param("cartStatus") CartStatus cartStatus);
}