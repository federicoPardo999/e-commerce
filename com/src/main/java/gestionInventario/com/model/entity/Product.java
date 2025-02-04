package gestionInventario.com.model.entity;

import gestionInventario.com.model.enumerator.product.Category;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    Category category;

    String name;
    Double price;
    Integer stock;
    String description;
    String urlImage;

    public void decreaseStock(Integer stockDecrease){this.stock-=stockDecrease;}

}