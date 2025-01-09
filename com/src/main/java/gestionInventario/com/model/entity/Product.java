package gestionInventario.com.model.entity;

import gestionInventario.com.model.enumerator.product.Category;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Data
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

     @Enumerated(EnumType.STRING)
     Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
     List<Cart> carts;

    public void decreaseStock(Integer stockDecrease){this.stock-=stockDecrease;}

}
