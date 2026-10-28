package agendador.bffagendadortarefas.business.dto.request;

import java.util.List;

public record UsuarioDTORequest(
        String nome,
        String email,
        String senha,
        List<EnderecoDTORequest> enderecos,
        List<TelefoneDTORequest> telefones
) {}