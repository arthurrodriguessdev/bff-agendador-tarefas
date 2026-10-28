package agendador.bffagendadortarefas.business.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record UsuarioDTOResponse(
        String nome,
        String email,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        String senha,
        List<EnderecoDTOResponse> enderecos,
        List<TelefoneDTOResponse> telefones
){}