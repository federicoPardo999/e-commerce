package gestionInventario.com.model.entity;

import gestionInventario.com.model.entity.utils.CustomerProductId;
import gestionInventario.com.model.enu.cart.CartStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "cart")
public class Cart {
    @EmbeddedId
    CustomerProductId id = CustomerProductId.builder().build();

    @MapsId("idProduct")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product")
    Product product;

    @MapsId("idCustomer")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_customer")
    Customer customer;

    Integer quantity;

    @Column(name = "price_total")
    Double priceTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "cart_status")
    CartStatus cartStatus;
}
