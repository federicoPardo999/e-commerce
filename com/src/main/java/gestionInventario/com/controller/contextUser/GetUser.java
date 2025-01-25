package gestionInventario.com.controller.contextUser;

import gestionInventario.com.exception.NotFoundException;
import gestionInventario.com.model.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public  class GetUser {

    public static UserEntity getUserFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated())
            throw  new NotFoundException("Token does not belong to a user");

        return (UserEntity) authentication.getPrincipal();
    }

}
