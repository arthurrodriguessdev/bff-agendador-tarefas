package agendador.bffagendadortarefas.business.dto.request;

public record EnderecoDTORequest(
        String rua,
        String bairro,
        String cep,
        String cidade
){}