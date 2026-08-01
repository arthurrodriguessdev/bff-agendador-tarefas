package agendador.bffagendadortarefas.business;

import agendador.bffagendadortarefas.business.dto.*;
import agendador.bffagendadortarefas.infraestructure.client.UsuarioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioClient usuarioClient;

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO){
        return usuarioClient.salvarUsuario(usuarioDTO);
    }

    // Refact
//    public boolean verificarValidadeToken(String token){
//        return jwtUtil.isTokenExpired(token);
//    }

    public UsuarioDTO buscarUsuario(Long id, String token){
        return usuarioClient.buscarUsuario(id, token);
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email, String token){
        return usuarioClient.buscarUsuarioPorEmail(email, token);
    }

    public void deletarUsuario(Long id, String token){
       usuarioClient.deletarUsuario(id, token);
    }

    public UsuarioDTO atualizarDadosUsuario(UsuarioDTO dto, Long id, String token){
        return usuarioClient.atualizarDadosUsuario(id, dto, token);
    }

    public EnderecoDTO atualizarDadosEndereco(Long idEndereco, EnderecoDTO enderecoDTO, String token){
        return usuarioClient.atualizarDadosEndereco(idEndereco, enderecoDTO, token);
    }

    public TelefoneDTO atualizarDadosTelefone(Long idTelefone, TelefoneDTO telefoneDTO, String token){
        return usuarioClient.atualizarDadosTelefone(idTelefone, telefoneDTO, token);
    }

    public EnderecoDTO cadastrarEndereco(EnderecoDTO enderecoDTO, String token){
        return usuarioClient.cadastrarEndereco(enderecoDTO, token);
    }

    public TelefoneDTO cadastrarTelefone(TelefoneDTO telefoneDTO, String token){
       return usuarioClient.cadastrarTelefone(telefoneDTO, token);
    }

    public String login(LoginDTO loginDTO){
        return usuarioClient.login(loginDTO);
    }
}