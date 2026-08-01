package agendador.bffagendadortarefas.business;

import agendador.bffagendadortarefas.business.dto.request.TarefaDTORequest;
import agendador.bffagendadortarefas.business.dto.response.TarefaDTOResponse;
import agendador.bffagendadortarefas.infraestructure.client.TarefaClient;
import agendador.bffagendadortarefas.infraestructure.enums.StatusNotificacaoEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@Service
public class TarefaService {
    private final TarefaClient tarefaClient;

    public TarefaDTOResponse adicionarTarefa(TarefaDTORequest tarefaDTO, String token){
        return tarefaClient.adicionarTarefa(tarefaDTO, token);
    }

    public List<TarefaDTOResponse> buscarTarefasPorEmail(String token){
        return tarefaClient.buscarTarefasPorEmail(token);
    }

    public List<TarefaDTOResponse> buscarTarefasPorIntervaloDatas(
            String token,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim){
        return tarefaClient.buscarTarefasPorIntervaloDatas(token, dataInicio, dataFim);
    }

    public void deletarTarefa(Long id, String token){
        tarefaClient.deletarTarefa(id, token);
    }

    public TarefaDTOResponse atualizarDadosTarefa(TarefaDTORequest tarefaDTO, Long id, String token){
        return tarefaClient.atualizarDadosTarefa(tarefaDTO, id, token);
    }

    public TarefaDTOResponse atualizarStatusTarefa(StatusNotificacaoEnum status, Long id, String token){
        return tarefaClient.atualizarStatusTarefa(status, id, token);
    }
}