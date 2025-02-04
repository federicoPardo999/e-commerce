package gestionInventario.com.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Product product;

    @JoinColumn(name = "cart_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Cart cart;

    @Column(name = "price_cart_item")
    Double cartItemPrice;

    Integer quantity;

    @PrePersist
    @PreUpdate
    private void setPriceFromProduct() {
        if (product != null && cartItemPrice == null) {
            this.cartItemPrice = product.getPrice();
        }
    }
}