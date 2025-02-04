package gestionInventario.com.model.entity;

import gestionInventario.com.model.enumerator.cart.CartStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(mappedBy = "Cart", cascade = CascadeType.PERSIST)
    Set<CartItem> cartItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    UserEntity userEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "cart_status")
    CartStatus cartStatus;

    @Column(name = "cart_price")
    Double totalCartPrice;

    public void addItem(CartItem item) {
        cartItems.add(item);
        item.setCart(this);
    }

}