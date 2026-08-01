package agendador.bffagendadortarefas.infraestructure.client;

import agendador.bffagendadortarefas.business.dto.EnderecoDTO;
import agendador.bffagendadortarefas.business.dto.LoginDTO;
import agendador.bffagendadortarefas.business.dto.TelefoneDTO;
import agendador.bffagendadortarefas.business.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "usuario", url = "${spring.client.url.usuario}")
public interface UsuarioClient {
    @GetMapping("/usuarios")
    UsuarioDTO buscarUsuarioPorEmail(@RequestParam("email") String email,
                                     @RequestHeader("Authorization") String token);

    @PostMapping
    UsuarioDTO salvarUsuario(@RequestBody UsuarioDTO usuarioDto);

    // Refact
//    @PostMapping("/conta-servico")
//    public ResponseEntity<UsuarioServicoDTO> salvarUsuariosServico(@RequestBody UsuarioServicoDTO usuarioServicoDTO){
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(usuarioService.salvarUsuarioServico(usuarioServicoDTO));
//    }

    @GetMapping("/{id}")
    UsuarioDTO buscarUsuario(@PathVariable Long id,
                             @RequestHeader("Authorization") String token);

    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Long id, @RequestHeader("Authorization") String token);

    @PutMapping("/{id}")
    UsuarioDTO atualizarDadosUsuario(@PathVariable Long id,
                                     @RequestBody UsuarioDTO usuarioDto,
                                     @RequestHeader("Authorization") String token);

    @PutMapping("/enderecos/{id}")
    EnderecoDTO atualizarDadosEndereco(@PathVariable Long id,
                                       @RequestBody EnderecoDTO enderecoDTO,
                                       @RequestHeader("Authorization") String token);

    @PutMapping("/telefones/{id}")
    TelefoneDTO atualizarDadosTelefone(@PathVariable Long id,
                                       @RequestBody TelefoneDTO telefoneDTO,
                                       @RequestHeader("Authorization") String token);

    @PostMapping("/enderecos")
    EnderecoDTO cadastrarEndereco(@RequestBody EnderecoDTO enderecoDTO,
                                  @RequestHeader("Authorization") String token);

    @PostMapping("/telefones")
    TelefoneDTO cadastrarTelefone(@RequestBody TelefoneDTO telefoneDTO,
                                  @RequestHeader("Authorization") String token);

    @PostMapping("/usuarios/login")
    public String login(@RequestBody LoginDTO loginDTO);

    // Refact
//    @GetMapping("/token/validade")
//    public ResponseEntity<Boolean> verificarValidadeToken(@RequestParam("token") String token){
//        return ResponseEntity.ok(usuarioService.verificarValidadeToken(token));
//    }
}
