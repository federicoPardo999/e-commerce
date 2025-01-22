package gestionInventario.com.mapper.purchasedProduct;

import gestionInventario.com.model.dto.purchasedProduct.CartResponseDTO;
import gestionInventario.com.model.entity.PurchasedProduct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PurchasedProductMapper {

    public CartResponseDTO cartToCartResponseDTO(PurchasedProduct purchasedProduct){
        return CartResponseDTO
                .builder()
                .nameProduct(purchasedProduct.getProduct().getName())
                .quantity(purchasedProduct.getQuantity())
                .priceTotal(purchasedProduct.getPriceTotal())
                .build();
    }

    public List<CartResponseDTO> cartsToCartResponseDTO(List<PurchasedProduct> purchasedProducts){
        return purchasedProducts.stream().map(this::cartToCartResponseDTO).toList();
    }
}
