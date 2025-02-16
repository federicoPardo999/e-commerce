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

    @OneToMany(mappedBy = "cart", cascade = CascadeType.PERSIST)
    Set<CartItem> cartItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "cart_status")
    CartStatus cartStatus;

    @Column(name = "cart_price")
    Double cartPrice;

}