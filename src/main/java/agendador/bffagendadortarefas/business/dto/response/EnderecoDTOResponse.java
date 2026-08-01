package agendador.bffagendadortarefas.business.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoDTOResponse {
    private Long id;
    private String rua;
    private String bairro;
    private String cep;
    private String cidade;
}