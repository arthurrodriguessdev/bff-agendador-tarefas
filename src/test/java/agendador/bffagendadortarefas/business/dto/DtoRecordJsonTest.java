package agendador.bffagendadortarefas.business.dto;

import agendador.bffagendadortarefas.business.dto.request.EnderecoDTORequest;
import agendador.bffagendadortarefas.business.dto.request.TarefaDTORequest;
import agendador.bffagendadortarefas.business.dto.request.TelefoneDTORequest;
import agendador.bffagendadortarefas.business.dto.request.UsuarioDTORequest;
import agendador.bffagendadortarefas.business.dto.response.UsuarioDTOResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DtoRecordJsonTest {
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void serializaEDesserializaTarefaMantendoFormatoDaData() throws Exception {
        TarefaDTORequest request = new TarefaDTORequest(
                "Consulta",
                "Consulta médica",
                LocalDateTime.of(2026, 10, 28, 14, 30, 0),
                "usuario@exemplo.com"
        );

        String json = objectMapper.writeValueAsString(request);

        assertEquals(
                "{\"nomeTarefa\":\"Consulta\",\"descricaoTarefa\":\"Consulta médica\","
                        + "\"dataEvento\":\"28-10-2026 14:30:00\",\"emailUsuario\":\"usuario@exemplo.com\"}",
                json
        );
        assertEquals(request, objectMapper.readValue(json, TarefaDTORequest.class));
    }

    @Test
    void serializaEDesserializaUsuarioMantendoNomesDosComponentes() throws Exception {
        UsuarioDTORequest request = new UsuarioDTORequest(
                "Ana",
                "ana@exemplo.com",
                "senha",
                List.of(new EnderecoDTORequest("Rua A", "Centro", "01001000", "São Paulo")),
                List.of(new TelefoneDTORequest("123456789", "11"))
        );

        String json = objectMapper.writeValueAsString(request);

        assertEquals(request, objectMapper.readValue(json, UsuarioDTORequest.class));
    }

    @Test
    void naoSerializaSenhaNoDtoDeRespostaDoUsuario() throws Exception {
        UsuarioDTOResponse response = new UsuarioDTOResponse(
                "Ana",
                "ana@exemplo.com",
                "hash-da-senha",
                List.of(),
                List.of()
        );

        String json = objectMapper.writeValueAsString(response);

        assertEquals(
                "{\"nome\":\"Ana\",\"email\":\"ana@exemplo.com\",\"enderecos\":[],\"telefones\":[]}",
                json
        );
    }
}
