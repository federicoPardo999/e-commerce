package gestionInventario.com.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ApiError {
    String error ;
    String message;
    Integer status;
}
