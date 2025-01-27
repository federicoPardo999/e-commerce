package gestionInventario.com.repository;

import gestionInventario.com.model.dto.purchasedProduct.PurchasedProductDTO;
import gestionInventario.com.model.entity.PurchasedProduct;
import gestionInventario.com.model.enumerator.cart.PurchaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPurchaseRepository extends JpaRepository<PurchasedProduct, PurchasedProduct> {
        
// creo que hay repetidos
    @Query("SELECT pp FROM PurchasedProduct pp where pp.userEntity.id = :userEntityId " +
            "AND pp.product.id = :productId AND pp.purchaseStatus = 'IN_PROGRESS'")
    PurchasedProduct findPurchasedProduct(@Param("userEntityId") Long userEntityId,
                                          @Param("productId") Long productId);

    @Query("SELECT pp FROM PurchasedProduct pp WHERE pp.userEntity.id = :idCustomer " +
            "AND pp.purchaseStatus = 'FINISHED'")
    List<PurchasedProduct> findOrders(@Param("idCustomer") Long idCustomer);

    @Query("SELECT pp FROM PurchasedProduct pp WHERE pp.userEntity.id = :idCustomer AND pp.purchaseStatus = 'IN_PROGRESS'")
    List<PurchasedProduct> findCartsInProgress(@Param("idCustomer") Long idCustomer);

    @Query("SELECT new  gestionInventario.com.model.dto.purchasedProduct.PurchasedProductDTO" +
            "(p.name, p.price, pp.quantity,p.price * pp.quantity) " +
            "FROM Product p JOIN p.purchasedProducts pp " +
            "WHERE pp.userEntity.id = :idCustomer AND pp.purchaseStatus = :purchaseStatus")
    List<PurchasedProductDTO> findProductsByCustomer(@Param("idCustomer") Long idCustomer,
                                                     @Param("purchaseStatus") PurchaseStatus purchaseStatus);

    @Query("SELECT sum(p.price * pp.quantity) FROM PurchasedProduct pp JOIN pp.product p  where pp.userEntity.id = :idCustomer " +
            "AND pp.purchaseStatus = :purchaseStatus")
    Double findTotalSpentOfCartBuy(@Param("idCustomer") Long idCustomer,
                                   @Param("purchaseStatus") PurchaseStatus purchaseStatus);

    @Modifying
    @Query("UPDATE PurchasedProduct pp SET pp.purchaseStatus = 'CANCELED' " +
            "WHERE pp.purchaseStatus = 'IN_PROGRESS' " +
            "AND pp.product.id = :idProduct AND pp.userEntity.id = :idCustomer")
    void cancelPurchase(Long idCustomer, Long idProduct);

    @Modifying
    @Query("UPDATE PurchasedProduct pp SET pp.purchaseStatus = 'FINISHED' " +
            "WHERE pp.purchaseStatus = 'IN_PROGRESS' AND pp.userEntity.id = :idCustomer")
    void doPurchase(Long idCustomer);

    @Query("SELECT sum(pp.priceTotal) FROM PurchasedProduct pp WHERE pp.userEntity.id = :idCustomer AND pp.purchaseStatus = 'IN_PROGRESS'")
    Double findTotalAmountCart(@Param("idCustomer") Long idCustomer);

    @Query("SELECT sum(pp.quantity) FROM PurchasedProduct pp WHERE pp.userEntity.id = :idCustomer AND pp.purchaseStatus = 'IN_PROGRESS'")
    Integer findTotalQuantityToBuy(@Param("idCustomer") Long idCustomer);

    @Query("SELECT p.price * pp.quantity FROM PurchasedProduct pp JOIN pp.product p" +
            " where pp.userEntity.id = :idCustomer" +
            " AND pp.product.id = :idProduct" +
            " AND pp.purchaseStatus = :purchaseStatus")
    Double findTotalSpentOfIndividualBuy(@Param("idCustomer") Long idCustomer,
                                         @Param("idProduct") Long idProduct,
                                         @Param("purchaseStatus") PurchaseStatus purchaseStatus);
}