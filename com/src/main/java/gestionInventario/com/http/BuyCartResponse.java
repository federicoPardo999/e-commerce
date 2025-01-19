package gestionInventario.com.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuyCartResponse {
    String nameCustomer;
    List<PurchasedProduct> products;
    Double totalSpent;
}
