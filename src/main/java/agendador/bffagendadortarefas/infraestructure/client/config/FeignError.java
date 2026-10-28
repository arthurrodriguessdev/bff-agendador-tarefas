package agendador.bffagendadortarefas.infraestructure.client.config;

import agendador.bffagendadortarefas.exception.ConflictException;
import agendador.bffagendadortarefas.exception.ErroInternoException;
import agendador.bffagendadortarefas.exception.ForbiddenException;
import agendador.bffagendadortarefas.exception.ResourceNotFound;
import agendador.bffagendadortarefas.exception.UnauthorizedException;
import agendador.bffagendadortarefas.infraestructure.handler.GlobalErrorMessage;
import agendador.bffagendadortarefas.infraestructure.handler.GlobalErrorMessages;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

public class FeignError implements ErrorDecoder {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        String message = mensagemPadrao(response.status());

        if(response.body() != null) {
            try(var body = response.body().asInputStream()){
                GlobalErrorMessage error = objectMapper.readValue(body, GlobalErrorMessage.class);
                if(error != null && error.message() != null && !error.message().isBlank()){
                    message = error.message();
                }
            } catch(IOException ex) {
                return criarExcecao(response.status(), mensagemPadrao(response.status()));
            }
        }

        return criarExcecao(response.status(), message);
    }

    private Exception criarExcecao(int status, String message) {
        return switch (status) {
            case 400, 422 -> new IllegalArgumentException(message);
            case 401 -> new UnauthorizedException(message);
            case 403 -> new ForbiddenException(message);
            case 404 -> new ResourceNotFound(message);
            case 409 -> new ConflictException(message);
            default -> status >= 400 && status < 500
                    ? new ResponseStatusException(
                            HttpStatusCode.valueOf(status),
                            GlobalErrorMessages.REQUISICAO_INVALIDA)
                    : new ErroInternoException(GlobalErrorMessages.ERRO_INTERNO);
        };
    }

    private String mensagemPadrao(int status) {
        return switch (status) {
            case 400, 422 -> GlobalErrorMessages.REQUISICAO_INVALIDA;
            case 401 -> GlobalErrorMessages.NAO_AUTORIZADO;
            case 403 -> GlobalErrorMessages.ACESSO_NEGADO;
            case 404 -> GlobalErrorMessages.RECURSO_NAO_ENCONTRADO;
            case 409 -> GlobalErrorMessages.CONFLITO;
            default -> GlobalErrorMessages.ERRO_INTERNO;
        };
    }
}