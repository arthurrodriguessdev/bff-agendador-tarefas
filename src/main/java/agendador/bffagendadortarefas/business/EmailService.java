package agendador.bffagendadortarefas.business;

import agendador.bffagendadortarefas.business.dto.request.LoginDTORequest;
import agendador.bffagendadortarefas.business.dto.response.TarefaDTOResponse;
import agendador.bffagendadortarefas.infraestructure.client.NotificacaoClient;
import agendador.bffagendadortarefas.infraestructure.client.TarefaClient;
import agendador.bffagendadortarefas.infraestructure.client.UsuarioClient;
import agendador.bffagendadortarefas.infraestructure.enums.StatusNotificacaoEnum;
import agendador.bffagendadortarefas.infraestructure.mapper.TarefaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final TarefaClient tarefaClient;
    private final NotificacaoClient notificacaoClient;
    private final UsuarioClient usuarioClient;
    private final TarefaMapper tarefaMapper;

    @Value("${admin.email}")
    private String emailAdmin;
    @Value("${admin.senha}")
    private String senhaAdmin;

    @Scheduled(cron = "${cron.notificacao}")
    public void enviarEmail(){
        String token = usuarioClient.login(new LoginDTORequest(emailAdmin, senhaAdmin));
        String tokenFormatado = formatarToken(token);

        List<TarefaDTOResponse> tarefasNotificar = getTarefasNotificar(formatarToken(tokenFormatado));
        tarefasNotificar.forEach(tarefa->{
            tarefaClient.atualizarStatusTarefa(StatusNotificacaoEnum.NOTIFICADO, tarefa.getId(), tokenFormatado);
            notificacaoClient.enviarEmail(tarefaMapper.toTarefaDTORequest(tarefa));
        });
    }

    public List<TarefaDTOResponse> getTarefasNotificar(String token){
        LocalDateTime horaAtual = LocalDateTime.now();
        LocalDateTime horaAtualMaisUma = horaAtual.plusHours(1);
        List<TarefaDTOResponse> tarefasPeriodo = tarefaClient.buscarTarefasPorIntervaloDatas(
                token, horaAtual, horaAtualMaisUma);

        // Filtrando somente as com status de pendência
        List<TarefaDTOResponse> tarefasNotificar = new ArrayList<>();
        tarefasPeriodo.forEach(tarefa->{
            if(tarefa.getStatus() == StatusNotificacaoEnum.PENDENTE){
                tarefasNotificar.add(tarefa);
            }
        });

        return tarefasNotificar;
    }

    public String formatarToken(String tokenFormatar){
        if(tokenFormatar.startsWith("Bearer ")){
            return tokenFormatar;
        }

        return "Bearer " + tokenFormatar;
    }
}