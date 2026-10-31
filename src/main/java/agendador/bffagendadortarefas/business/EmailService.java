package agendador.bffagendadortarefas.business;

import agendador.bffagendadortarefas.business.dto.request.LoginDTORequest;
import agendador.bffagendadortarefas.business.dto.request.UsuarioDTORequest;
import agendador.bffagendadortarefas.business.utils.TokenUtil;
import agendador.bffagendadortarefas.infraestructure.client.TarefaClient;
import agendador.bffagendadortarefas.infraestructure.client.UsuarioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final TarefaClient tarefaClient;
    private final UsuarioClient usuarioClient;

    @Value("${admin.email}")
    private String emailAdmin;
    @Value("${admin.senha}")
    private String senhaAdmin;
    @Value("${admin.nome}")
    private String nomeAdmin;

//    @Scheduled(cron = "${cron.notificacao}")
    @Scheduled(fixedDelay = 1000)
    public void enviarEmail() {
        String token = null;

        // Autentica com as credenciais admin para enviar a requisição
        try {
            token = usuarioClient.login(new LoginDTORequest(emailAdmin, senhaAdmin));
        } catch (Exception e) {
            usuarioClient.salvarUsuario(new UsuarioDTORequest(
                    nomeAdmin,
                    emailAdmin,
                    senhaAdmin,
                    null,
                    null)
            );

            token = usuarioClient.login(new LoginDTORequest(emailAdmin, senhaAdmin));
        }

        tarefaClient.notificarProximasTarefas(TokenUtil.formatarToken(token));
    }
}