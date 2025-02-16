package gestionInventario.com.service.interfaces;

import gestionInventario.com.model.dto.purchasedProduct.CartItemRequestDTO;
import gestionInventario.com.model.dto.purchasedProduct.UpdateItemRequestDTO;
import gestionInventario.com.model.entity.Cart;

public interface ICartItemService {

    void addItem(CartItemRequestDTO cartItemRequestDTO,Long customerId);
    void updateStockItem(UpdateItemRequestDTO updateItemRequestDTO, Long customerId);
    void deleteItem(Long cartItemID);
    void updatePriceOfCart(Cart cart);
}
