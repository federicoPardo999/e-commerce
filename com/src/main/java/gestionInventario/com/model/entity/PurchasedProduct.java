package gestionInventario.com.model.entity;

import gestionInventario.com.model.enumerator.cart.purchaseStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "purchased_product")
public class PurchasedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product")
    Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
        UserEntity userEntity;

    Integer quantity;

    @Column(name = "price_total")
    Double priceTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "cart_status")
    purchaseStatus purchaseStatus;
}
