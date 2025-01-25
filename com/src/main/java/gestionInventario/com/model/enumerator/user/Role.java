package gestionInventario.com.model.enumerator.user;

public enum Role {
    ADMIN,
    CUSTOMER;

    public static Role getRol(String rol) {
        return Role.valueOf(rol.toUpperCase());
    }

}
