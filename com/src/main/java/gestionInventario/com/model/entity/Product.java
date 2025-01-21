package gestionInventario.com.model.entity;

import gestionInventario.com.model.enumerator.product.Category;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

    String name;
    Double price;
    Integer stock;
    String description;

    String urlImage;

    @Enumerated(EnumType.STRING)
    Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
     List<PurchasedProduct> purchasedProducts;

    public void decreaseStock(Integer stockDecrease){this.stock-=stockDecrease;}

}
