package agendador.bffagendadortarefas.business.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record TarefaDTORequest(
        String nomeTarefa,
        String descricaoTarefa,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime dataEvento,
        String emailUsuario
){}