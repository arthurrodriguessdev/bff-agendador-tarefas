package agendador.bffagendadortarefas.infraestructure.handler;

import agendador.bffagendadortarefas.business.dto.request.LoginDTORequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GlobalExceptionHandlerTest {
    private MockMvc mockMvc;

    @BeforeEach
    void configurarMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TestController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void erroInesperadoRetornaMensagemGenericaSemDetalhesInternos() throws Exception {
        mockMvc.perform(get("/test/erro"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value("500"))
                .andExpect(jsonPath("$.message").value(GlobalErrorMessages.ERRO_INTERNO));
    }

    @Test
    void jsonMalformadoRetornaErroDeRequisicaoSemDetalhesDoParser() throws Exception {
        mockMvc.perform(post("/test/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.message").value(GlobalErrorMessages.REQUISICAO_INVALIDA));
    }

    @RestController
    static class TestController {
        @GetMapping("/test/erro")
        void erro() {
            throw new IllegalStateException("detalhe interno");
        }

        @PostMapping("/test/login")
        LoginDTORequest login(@RequestBody LoginDTORequest request) {
            return request;
        }
    }
}
