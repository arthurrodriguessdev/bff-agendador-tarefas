package agendador.bffagendadortarefas.business.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoDTORequest {
    private String rua;
    private String bairro;
    private String cep;
    private String cidade;
}