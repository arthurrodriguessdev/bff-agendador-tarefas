package agendador.bffagendadortarefas.infraestructure.handler;

import agendador.bffagendadortarefas.exception.ConflictException;
import agendador.bffagendadortarefas.exception.ForbiddenException;
import agendador.bffagendadortarefas.exception.ResourceNotFound;
import agendador.bffagendadortarefas.exception.UnauthorizedException;
import agendador.bffagendadortarefas.exception.ErroInternoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConflictException.class)
    private ResponseEntity<GlobalErrorMessage> conflictExceptionHandler(ConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error(HttpStatus.CONFLICT, mensagemOuPadrao(ex, GlobalErrorMessages.CONFLITO)));
    }

    @ExceptionHandler(ResourceNotFound.class)
    private ResponseEntity<GlobalErrorMessage> resourceNotFoundHandler(ResourceNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error(HttpStatus.NOT_FOUND, mensagemOuPadrao(ex, GlobalErrorMessages.RECURSO_NAO_ENCONTRADO)));
    }

    @ExceptionHandler(UnauthorizedException.class)
    private ResponseEntity<GlobalErrorMessage> unauthorizedExceptionHandler(UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(error(HttpStatus.UNAUTHORIZED, mensagemOuPadrao(ex, GlobalErrorMessages.NAO_AUTORIZADO)));
    }

    @ExceptionHandler(ForbiddenException.class)
    private ResponseEntity<GlobalErrorMessage> forbiddenExceptionHandler(ForbiddenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(error(HttpStatus.FORBIDDEN, mensagemOuPadrao(ex, GlobalErrorMessages.ACESSO_NEGADO)));
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class
    })
    private ResponseEntity<GlobalErrorMessage> badRequestExceptionHandler(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error(HttpStatus.BAD_REQUEST, ex instanceof IllegalArgumentException
                        ? mensagemOuPadrao(ex, GlobalErrorMessages.REQUISICAO_INVALIDA)
                        : GlobalErrorMessages.REQUISICAO_INVALIDA));
    }

    @ExceptionHandler(ResponseStatusException.class)
    private ResponseEntity<GlobalErrorMessage> responseStatusExceptionHandler(ResponseStatusException ex) {
        HttpStatusCode status = ex.getStatusCode();
        return ResponseEntity.status(status)
                .body(new GlobalErrorMessage(Integer.toString(status.value()), GlobalErrorMessages.REQUISICAO_INVALIDA));
    }

    @ExceptionHandler(ErroInternoException.class)
    private ResponseEntity<GlobalErrorMessage> internalExceptionHandler(ErroInternoException ex) {
        log.error("Erro interno ao comunicar com um serviço dependente.", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error(HttpStatus.INTERNAL_SERVER_ERROR, GlobalErrorMessages.ERRO_INTERNO));
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<GlobalErrorMessage> unexpectedExceptionHandler(Exception ex) {
        log.error("Erro inesperado ao processar a requisição.", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error(HttpStatus.INTERNAL_SERVER_ERROR, GlobalErrorMessages.ERRO_INTERNO));
    }

    private GlobalErrorMessage error(HttpStatus status, String message) {
        return new GlobalErrorMessage(Integer.toString(status.value()), message);
    }

    private String mensagemOuPadrao(Exception ex, String mensagemPadrao) {
        return ex.getMessage() == null || ex.getMessage().isBlank() ? mensagemPadrao : ex.getMessage();
    }
}