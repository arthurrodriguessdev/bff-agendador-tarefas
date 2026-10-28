package agendador.bffagendadortarefas.business.dto.response;

public record EnderecoDTOResponse(
        Long id,
        String rua,
        String bairro,
        String cep,
        String cidade
){}