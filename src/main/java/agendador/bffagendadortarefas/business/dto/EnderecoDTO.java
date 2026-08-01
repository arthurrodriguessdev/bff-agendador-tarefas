package agendador.bffagendadortarefas.business.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoDTO {
    private Long id;
    private String rua;
    private String bairro;
    private String cep;
    private String cidade;
}