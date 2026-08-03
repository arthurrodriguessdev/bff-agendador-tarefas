package agendador.bffagendadortarefas.controller;

import agendador.bffagendadortarefas.business.TarefaService;
import agendador.bffagendadortarefas.business.dto.request.TarefaDTORequest;
import agendador.bffagendadortarefas.business.dto.response.TarefaDTOResponse;
import agendador.bffagendadortarefas.infraestructure.enums.StatusNotificacaoEnum;
import agendador.bffagendadortarefas.infraestructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@Tag(name = "Tarefa", description = "Cadastro de tarefas do usuário")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class TarefaController {
    private final TarefaService tarefaService;

    @Operation(summary = "Cadastrar Tarefa de Usuário", description = "Cria uma nova tarefa")
    @ApiResponse(responseCode = "200", description = "Tarefa salva com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PostMapping
    public ResponseEntity<TarefaDTOResponse> adicionarTarefa(@RequestBody TarefaDTORequest tarefaDTO,
                                                             @RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(tarefaService.adicionarTarefa(tarefaDTO, token));
    }

    @Operation(summary = "Buscar Tarefas de Usuário Por E-mail",
            description = "Busca tarefas de um usuário cadastrado pelo e-mail")
    @ApiResponse(responseCode = "200", description = "Tarefas encontradas com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping
    public ResponseEntity<List<TarefaDTOResponse>> buscarTarefasPorEmail(@RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(tarefaService.buscarTarefasPorEmail(token));
    }

    @Operation(summary = "Buscar Tarefas Por Período",
            description = "Busca tarefas cadastradas por um intervalo de período")
    @ApiResponse(responseCode = "200", description = "Tarefas encontradas com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/eventos")
    public ResponseEntity<List<TarefaDTOResponse>> buscarTarefasPorIntervaloDatas(
            @RequestHeader(name = "Authorization", required = false) String token,
            @RequestParam("dataInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam("dataFim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim){
        return ResponseEntity.ok(tarefaService.buscarTarefasPorIntervaloDatas(token, dataInicio, dataFim));
    }

    @Operation(summary = "Deleta Tarefas Por ID", description = "Delete tarefa cadastrada por ID")
    @ApiResponse(responseCode = "200", description = "Tarefa deletada com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id,
                                              @RequestHeader(name = "Authorization", required = false) String token){
        tarefaService.deletarTarefa(id, token);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Altera Dados De Tarefa", description = "Altera dados de tarefas cadastradas")
    @ApiResponse(responseCode = "200", description = "Tarefa alterada com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PutMapping("/{id}")
    public ResponseEntity<TarefaDTOResponse> atualizarDadosTarefa(@RequestBody TarefaDTORequest tarefaDTO,
                                                                  @PathVariable Long id,
                                                                  @RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(tarefaService.atualizarDadosTarefa(tarefaDTO, id, token));
    }

    @Operation(summary = "Altera Status Da Tarefa", description = "Altera o status da tarefa")
    @ApiResponse(responseCode = "200", description = "Status da tarefa alterado com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/{id}")
    public ResponseEntity<TarefaDTOResponse> atualizarStatusTarefa(@RequestParam("status") StatusNotificacaoEnum status,
                                                                   @PathVariable Long id,
                                                                   @RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(tarefaService.atualizarStatusTarefa(status, id, token));
    }
}