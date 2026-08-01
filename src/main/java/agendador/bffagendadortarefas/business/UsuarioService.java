package agendador.bffagendadortarefas.business;

import agendador.bffagendadortarefas.business.dto.request.EnderecoDTORequest;
import agendador.bffagendadortarefas.business.dto.request.LoginDTORequest;
import agendador.bffagendadortarefas.business.dto.request.TelefoneDTORequest;
import agendador.bffagendadortarefas.business.dto.request.UsuarioDTORequest;
import agendador.bffagendadortarefas.business.dto.response.EnderecoDTOResponse;
import agendador.bffagendadortarefas.business.dto.response.TelefoneDTOResponse;
import agendador.bffagendadortarefas.business.dto.response.UsuarioDTOResponse;
import agendador.bffagendadortarefas.infraestructure.client.UsuarioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioClient usuarioClient;

    public UsuarioDTOResponse salvarUsuario(UsuarioDTORequest usuarioDTO){
        return usuarioClient.salvarUsuario(usuarioDTO);
    }

    // Refact
//    public boolean verificarValidadeToken(String token){
//        return jwtUtil.isTokenExpired(token);
//    }

    public UsuarioDTOResponse buscarUsuario(Long id, String token){
        return usuarioClient.buscarUsuario(id, token);
    }

    public UsuarioDTOResponse buscarUsuarioPorEmail(String email, String token){
        return usuarioClient.buscarUsuarioPorEmail(email, token);
    }

    public void deletarUsuario(Long id, String token){
       usuarioClient.deletarUsuario(id, token);
    }

    public UsuarioDTOResponse atualizarDadosUsuario(UsuarioDTORequest dto, Long id, String token){
        return usuarioClient.atualizarDadosUsuario(id, dto, token);
    }

    public EnderecoDTOResponse atualizarDadosEndereco(Long idEndereco, EnderecoDTORequest enderecoDTO, String token){
        return usuarioClient.atualizarDadosEndereco(idEndereco, enderecoDTO, token);
    }

    public TelefoneDTOResponse atualizarDadosTelefone(Long idTelefone, TelefoneDTORequest telefoneDTO, String token){
        return usuarioClient.atualizarDadosTelefone(idTelefone, telefoneDTO, token);
    }

    public EnderecoDTOResponse cadastrarEndereco(EnderecoDTORequest enderecoDTO, String token){
        return usuarioClient.cadastrarEndereco(enderecoDTO, token);
    }

    public TelefoneDTOResponse cadastrarTelefone(TelefoneDTORequest telefoneDTO, String token){
       return usuarioClient.cadastrarTelefone(telefoneDTO, token);
    }

    public String login(LoginDTORequest loginDTO){
        return usuarioClient.login(loginDTO);
    }
}