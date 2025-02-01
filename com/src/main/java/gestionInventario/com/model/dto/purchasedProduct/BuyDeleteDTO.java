package gestionInventario.com.model.dto.purchasedProduct;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BuyDeleteDTO {
    Long idCustomer;
    Long idProduct;
}