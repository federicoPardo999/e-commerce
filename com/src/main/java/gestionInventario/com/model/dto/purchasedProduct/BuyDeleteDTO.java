package gestionInventario.com.model.dto.purchasedProduct;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BuyDeleteDTO {
    Long idCustomer;
    Long idProduct;
}