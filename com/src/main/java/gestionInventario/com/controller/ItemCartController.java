package gestionInventario.com.controller;

import gestionInventario.com.model.dto.purchasedProduct.UpdateItemRequestDTO;
import gestionInventario.com.model.entity.UserEntity;
import gestionInventario.com.service.interfaces.ICartItemService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static gestionInventario.com.controller.contextUser.GetUser.getUserFromToken;

@RestController
@RequiredArgsConstructor
@RequestMapping("item")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ItemCartController {
    ICartItemService iCartItemService;

    @PatchMapping("update-stock")
    public ResponseEntity<?> updateStockItem(@RequestBody UpdateItemRequestDTO updateItemRequestDTO){
        UserEntity user = getUserFromToken();
        iCartItemService.updateStockItem(updateItemRequestDTO,user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@PathVariable Long idItem){
        UserEntity user = getUserFromToken();
        iCartItemService.deleteItem(idItem );
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
