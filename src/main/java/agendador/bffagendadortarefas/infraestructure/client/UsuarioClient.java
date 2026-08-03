package agendador.bffagendadortarefas.infraestructure.client;

import agendador.bffagendadortarefas.business.dto.request.EnderecoDTORequest;
import agendador.bffagendadortarefas.business.dto.request.LoginDTORequest;
import agendador.bffagendadortarefas.business.dto.request.TelefoneDTORequest;
import agendador.bffagendadortarefas.business.dto.request.UsuarioDTORequest;
import agendador.bffagendadortarefas.business.dto.response.EnderecoDTOResponse;
import agendador.bffagendadortarefas.business.dto.response.TelefoneDTOResponse;
import agendador.bffagendadortarefas.business.dto.response.UsuarioDTOResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "usuario", url = "${spring.client.url.usuario}")
public interface UsuarioClient {
    @GetMapping("/usuarios")
    UsuarioDTOResponse buscarUsuarioPorEmail(@RequestParam("email") String email,
                                             @RequestHeader("Authorization") String token);

    @PostMapping("/usuarios")
    UsuarioDTOResponse salvarUsuario(@RequestBody UsuarioDTORequest usuarioDto);

    @GetMapping("/usuarios/{id}")
    UsuarioDTOResponse buscarUsuario(@PathVariable Long id,
                                     @RequestHeader("Authorization") String token);

    @DeleteMapping("/usuarios/{id}")
    void deletarUsuario(@PathVariable Long id, @RequestHeader("Authorization") String token);

    @PutMapping("/usuarios/{id}")
    UsuarioDTOResponse atualizarDadosUsuario(@PathVariable Long id,
                                             @RequestBody UsuarioDTORequest usuarioDto,
                                             @RequestHeader("Authorization") String token);

    @PutMapping("/usuarios/enderecos/{id}")
    EnderecoDTOResponse atualizarDadosEndereco(@PathVariable Long id,
                                               @RequestBody EnderecoDTORequest enderecoDTO,
                                               @RequestHeader("Authorization") String token);

    @PutMapping("/usuarios/telefones/{id}")
    TelefoneDTOResponse atualizarDadosTelefone(@PathVariable Long id,
                                               @RequestBody TelefoneDTORequest telefoneDTO,
                                               @RequestHeader("Authorization") String token);

    @PostMapping("/usuarios/enderecos")
    EnderecoDTOResponse cadastrarEndereco(@RequestBody EnderecoDTORequest enderecoDTO,
                                          @RequestHeader("Authorization") String token);

    @PostMapping("/usuarios/telefones")
    TelefoneDTOResponse cadastrarTelefone(@RequestBody TelefoneDTORequest telefoneDTO,
                                          @RequestHeader("Authorization") String token);

    @PostMapping("/usuarios/login")
    String login(@RequestBody LoginDTORequest loginDTO);

    // Refact
//    @GetMapping("/token/validade")
//    public ResponseEntity<Boolean> verificarValidadeToken(@RequestParam("token") String token){
//        return ResponseEntity.ok(usuarioService.verificarValidadeToken(token));
//    }
}
