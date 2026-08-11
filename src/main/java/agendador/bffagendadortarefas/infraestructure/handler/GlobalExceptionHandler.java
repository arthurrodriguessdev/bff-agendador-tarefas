package agendador.bffagendadortarefas.infraestructure.handler;

import agendador.bffagendadortarefas.exception.ConflictException;
import agendador.bffagendadortarefas.exception.ResourceNotFound;
import agendador.bffagendadortarefas.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConflictException.class)
    private ResponseEntity<GlobalErrorMessage> conflictExceptionHandler(ConflictException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new GlobalErrorMessage(Integer.toString(HttpStatus.CONFLICT.value()), ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFound.class)
    private ResponseEntity<GlobalErrorMessage> resourceNotFoundHandler(ResourceNotFound ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new GlobalErrorMessage(Integer.toString(HttpStatus.NOT_FOUND.value()), ex.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    private ResponseEntity<GlobalErrorMessage> unauthorizedExceptionHandler(UnauthorizedException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new GlobalErrorMessage(Integer.toString(HttpStatus.UNAUTHORIZED.value()), ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<GlobalErrorMessage> illegalArgumentExceptionHandler(IllegalArgumentException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GlobalErrorMessage(Integer.toString(HttpStatus.BAD_REQUEST.value()), ex.getMessage()));
    }
}