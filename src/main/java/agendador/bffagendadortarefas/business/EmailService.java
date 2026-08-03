package agendador.bffagendadortarefas.business;

import agendador.bffagendadortarefas.business.dto.request.TarefaDTORequest;
import agendador.bffagendadortarefas.infraestructure.client.NotificacaoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final NotificacaoClient notificacaoClient;

    public void enviarEmail(TarefaDTORequest tarefaDTORequest){
        notificacaoClient.enviarEmail(tarefaDTORequest);
    }
}
