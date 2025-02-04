package gestionInventario.com.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "order_entity")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    UserEntity customer;

    @OneToMany(mappedBy = "OrderEntity",cascade = CascadeType.ALL)
    Set<OrderItem> ordersItems;

    @Column(name = "price_total")
    Double priceTotal;

    LocalDate date;

    // Método helper para agregar items
    public void addItem(OrderItem item) {
        ordersItems.add(item);
        item.setOrderEntity(this);
    }

}