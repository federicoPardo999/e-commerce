package gestionInventario.com.model.dto.product;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponseDTO {
    Long idProduct;
    String name;
    Double price;
    String description;
    Integer stock;
    String image;
}