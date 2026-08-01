package agendador.bffagendadortarefas.business.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTORequest {
    private String email;
    private String senha;
}