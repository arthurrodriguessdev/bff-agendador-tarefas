package agendador.bffagendadortarefas.infraestructure.handler;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Erro", description = "Formato padrão das respostas de erro da API.")
public record GlobalErrorMessage(
        @Schema(description = "Código HTTP da resposta", example = "404")
        String status,
        @Schema(description = "Descrição do erro", example = "Recurso não encontrado.")
        String message
) {}