package gestionInventario.com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiError> handlerException(Exception e){
        ApiError apiError = ApiError.builder()
                .error("internal_server_error")
                .message(e.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

        return  ResponseEntity.status(apiError.getStatus()).body(apiError);

    }
    @ExceptionHandler(RegisterException.class)
    protected ResponseEntity<ApiError> handlerBadRequestException(RegisterException e){
        ApiError apiError = ApiError.builder()
                .error("register_exception")
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return  ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler(LoginException.class)
    protected ResponseEntity<ApiError> handlerBadRequestException(LoginException e){
        ApiError apiError = ApiError.builder()
                .error("login_exception")
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return  ResponseEntity.status(apiError.getStatus()).body(apiError);

    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ApiError> handlerBadRequestException(BadRequestException e){
        ApiError apiError = ApiError.builder()
                .error("bad_request")
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return  ResponseEntity.status(apiError.getStatus()).body(apiError);

    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ApiError> handlerNotFoundExceptionException(NotFoundException e){
        ApiError apiError = ApiError.builder()
                .error("not_found")
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();

        return  ResponseEntity.status(apiError.getStatus()).body(apiError);

    }

    @ExceptionHandler(MailSendException.class)
    protected ResponseEntity<ApiError> handlerMailException(MailSendException e){
        ApiError apiError = ApiError.builder()
                .error("mail_exception")
                .message(e.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

        return  ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler(StockException.class)
    protected ResponseEntity<ApiError> handlerStockException(StockException e){
        ApiError apiError = ApiError.builder()
                .error("stock_exception")
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return  ResponseEntity.status(apiError.getStatus()).body(apiError);
    }
}
