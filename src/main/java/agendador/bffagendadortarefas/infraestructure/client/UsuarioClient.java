package agendador.bffagendadortarefas.infraestructure.client;

import agendador.bffagendadortarefas.business.dto.response.*;
import agendador.bffagendadortarefas.business.dto.request.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "usuario", url = "${spring.client.url.usuario}")
public interface UsuarioClient {
    @GetMapping("/usuarios")
    UsuarioDTOResponse buscarUsuarioPorEmail(@RequestParam("email") String email,
                                             @RequestHeader("Authorization") String token);

    @PostMapping
    UsuarioDTOResponse salvarUsuario(@RequestBody UsuarioDTORequest usuarioDto);

    @GetMapping("/{id}")
    UsuarioDTOResponse buscarUsuario(@PathVariable Long id,
                                     @RequestHeader("Authorization") String token);

    @DeleteMapping("/{id}")
    void deletarUsuario(@PathVariable Long id, @RequestHeader("Authorization") String token);

    @PutMapping("/{id}")
    UsuarioDTOResponse atualizarDadosUsuario(@PathVariable Long id,
                                             @RequestBody UsuarioDTORequest usuarioDto,
                                             @RequestHeader("Authorization") String token);

    @PutMapping("/enderecos/{id}")
    EnderecoDTOResponse atualizarDadosEndereco(@PathVariable Long id,
                                               @RequestBody EnderecoDTORequest enderecoDTO,
                                               @RequestHeader("Authorization") String token);

    @PutMapping("/telefones/{id}")
    TelefoneDTOResponse atualizarDadosTelefone(@PathVariable Long id,
                                               @RequestBody TelefoneDTORequest telefoneDTO,
                                               @RequestHeader("Authorization") String token);

    @PostMapping("/enderecos")
    EnderecoDTOResponse cadastrarEndereco(@RequestBody EnderecoDTORequest enderecoDTO,
                                          @RequestHeader("Authorization") String token);

    @PostMapping("/telefones")
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
