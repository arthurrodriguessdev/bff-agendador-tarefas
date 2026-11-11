package agendador.bffagendadortarefas.infraestructure.client;

import agendador.bffagendadortarefas.business.dto.request.TarefaDTORequest;
import agendador.bffagendadortarefas.business.dto.response.TarefaDTOResponse;
import agendador.bffagendadortarefas.infraestructure.enums.StatusNotificacaoEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "tarefa", url = "${spring.client.url.tarefa}")
public interface TarefaClient {
    @PostMapping()
    TarefaDTOResponse adicionarTarefa(@RequestBody TarefaDTORequest tarefaDTO,
                                      @RequestHeader("Authorization") String token);

    @GetMapping()
    List<TarefaDTOResponse> buscarTarefasPorEmail(@RequestHeader("Authorization") String token);

    @GetMapping("/eventos")
    List<TarefaDTOResponse> buscarTarefasPorIntervaloDatas(
            @RequestHeader("Authorization") String token,
            @RequestParam("dataInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam("dataFim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim);

    @DeleteMapping("/{id}")
    void deletarTarefa(@PathVariable Long id,
                       @RequestHeader("Authorization") String token);

    @PutMapping("/{id}")
    TarefaDTOResponse atualizarDadosTarefa(@RequestBody TarefaDTORequest tarefaDTO,
                                           @PathVariable Long id,
                                           @RequestHeader("Authorization") String token);

    @PatchMapping("/{id}")
    TarefaDTOResponse atualizarStatusTarefa(@RequestParam("status") StatusNotificacaoEnum status,
                                            @PathVariable Long id,
                                            @RequestHeader("Authorization") String token);

    @PostMapping("/proximas/notificar")
    void notificarProximasTarefas(@RequestHeader("Authorization") String token);
}