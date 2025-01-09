package gestionInventario.com.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchasedProduct {
    String name;
    Double price;
    Integer stockToBuy;
}