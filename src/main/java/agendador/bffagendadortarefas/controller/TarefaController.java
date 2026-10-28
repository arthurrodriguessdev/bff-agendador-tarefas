package agendador.bffagendadortarefas.controller;

import agendador.bffagendadortarefas.business.TarefaService;
import agendador.bffagendadortarefas.business.dto.request.TarefaDTORequest;
import agendador.bffagendadortarefas.business.dto.response.TarefaDTOResponse;
import agendador.bffagendadortarefas.infraestructure.enums.StatusNotificacaoEnum;
import agendador.bffagendadortarefas.infraestructure.handler.GlobalErrorMessage;
import agendador.bffagendadortarefas.infraestructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tarefas")
@Tag(name = "Tarefa", description = "Cadastro de tarefas do usuário")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
@ApiResponse(
        responseCode = "default",
        description = "Erros não listados na operação usam o formato padrão de erro.",
        content = @Content(schema = @Schema(implementation = GlobalErrorMessage.class))
)
public class TarefaController {
    private final TarefaService tarefaService;

    @Operation(summary = "Cadastrar tarefa", description = "Cria uma tarefa para o usuário autenticado. A data do evento deve seguir o formato dd-MM-yyyy HH:mm:ss.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da tarefa inválidos"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "409", description = "Conflito ao cadastrar a tarefa"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PostMapping
    public ResponseEntity<TarefaDTOResponse> adicionarTarefa(
            @RequestBody TarefaDTORequest tarefaDTO,
            @RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(tarefaService.adicionarTarefa(tarefaDTO, token));
    }

    @Operation(summary = "Listar tarefas do usuário", description = "Retorna as tarefas associadas ao usuário autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping
    public ResponseEntity<List<TarefaDTOResponse>> buscarTarefasPorEmail(
            @RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(tarefaService.buscarTarefasPorEmail(token));
    }

    @Operation(summary = "Buscar tarefas por período", description = "Retorna as tarefas do usuário autenticado cujos eventos estão no intervalo informado. Use dataInicio e dataFim no formato ISO-8601, por exemplo 2026-10-28T10:00:00.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada"),
            @ApiResponse(responseCode = "400", description = "Período inválido"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/eventos")
    public ResponseEntity<List<TarefaDTOResponse>> buscarTarefasPorIntervaloDatas(
            @RequestHeader(name = "Authorization", required = false) String token,
            @Parameter(description = "Início do intervalo em ISO-8601", example = "2026-10-28T10:00:00")
            @RequestParam("dataInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @Parameter(description = "Fim do intervalo em ISO-8601", example = "2026-10-28T18:00:00")
            @RequestParam("dataFim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim){
        return ResponseEntity.ok(tarefaService.buscarTarefasPorIntervaloDatas(token, dataInicio, dataFim));
    }

    @Operation(summary = "Excluir tarefa", description = "Remove a tarefa identificada pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tarefa removida"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(
                                              @Parameter(description = "Identificador da tarefa", example = "1")
                                              @PathVariable Long id,
                                              @RequestHeader(name = "Authorization", required = false) String token){
        tarefaService.deletarTarefa(id, token);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Atualizar tarefa", description = "Substitui os dados da tarefa identificada pelo ID. A data do evento deve seguir o formato dd-MM-yyyy HH:mm:ss.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada"),
            @ApiResponse(responseCode = "400", description = "Dados da tarefa inválidos"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflito ao atualizar a tarefa"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TarefaDTOResponse> atualizarDadosTarefa(
            @RequestBody TarefaDTORequest tarefaDTO,
            @Parameter(description = "Identificador da tarefa", example = "1")
            @PathVariable Long id,
            @RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(tarefaService.atualizarDadosTarefa(tarefaDTO, id, token));
    }

    @Operation(summary = "Atualizar status da tarefa", description = "Altera o status da tarefa indicada pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status atualizado"),
            @ApiResponse(responseCode = "400", description = "Status inválido"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<TarefaDTOResponse> atualizarStatusTarefa(
            @Parameter(description = "Novo status da notificação")
            @RequestParam("status") StatusNotificacaoEnum status,
            @Parameter(description = "Identificador da tarefa", example = "1")
            @PathVariable Long id,
            @RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(tarefaService.atualizarStatusTarefa(status, id, token));
    }
}