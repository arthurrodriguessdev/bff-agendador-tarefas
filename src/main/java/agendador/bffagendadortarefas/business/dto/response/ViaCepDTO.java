package agendador.bffagendadortarefas.business.dto.response;

public record ViaCepDTO(
        String cep,
        String logradouro,
        String complemento,
        String bairro,
        String localidade,
        String uf,
        String estado,
        String regiao
){}
