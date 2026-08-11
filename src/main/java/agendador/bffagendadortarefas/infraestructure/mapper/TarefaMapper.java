package agendador.bffagendadortarefas.infraestructure.mapper;

import agendador.bffagendadortarefas.business.dto.request.TarefaDTORequest;
import agendador.bffagendadortarefas.business.dto.response.TarefaDTOResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefaMapper {
    TarefaDTORequest toTarefaDTORequest(TarefaDTOResponse tarefaDTOResponset);
}