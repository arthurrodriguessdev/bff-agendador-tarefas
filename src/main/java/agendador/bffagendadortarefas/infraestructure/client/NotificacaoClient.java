package agendador.bffagendadortarefas.infraestructure.client;

import agendador.bffagendadortarefas.business.dto.request.TarefaDTORequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="notificacao", url="${spring.client.url.notificacao}")
public interface NotificacaoClient {
    @PostMapping("/email")
    void enviarEmail(@RequestBody TarefaDTORequest tarefaDTO);
}