package agendador.bffagendadortarefas.infraestructure.client.config;

import agendador.bffagendadortarefas.exception.ConflictException;
import agendador.bffagendadortarefas.exception.ErroInternoException;
import agendador.bffagendadortarefas.exception.ResourceNotFound;
import agendador.bffagendadortarefas.exception.UnauthorizedException;
import agendador.bffagendadortarefas.infraestructure.handler.GlobalErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class FeignError implements ErrorDecoder {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String s, Response response) {
        try {
            if(response.body() != null) {
                GlobalErrorMessage globalErrorMessage = objectMapper.readValue(
                        response.body().asInputStream(),
                        GlobalErrorMessage.class
                );

                return switch(response.status()) {
                    case 400 -> new IllegalArgumentException(globalErrorMessage.getMessage());
                    case 401 -> new UnauthorizedException(globalErrorMessage.getMessage());
                    case 404 -> new ResourceNotFound(globalErrorMessage.getMessage());
                    case 409 -> new ConflictException(globalErrorMessage.getMessage());
                    default -> new RuntimeException(globalErrorMessage.getMessage());
                };
            }

        } catch(IOException e) {
            log.error(e.getMessage());
            throw new ErroInternoException("Erro ao ler JSON do microsserviço.");
        }

        return new ErroInternoException("Erro interno desconhecido.");
    }
}