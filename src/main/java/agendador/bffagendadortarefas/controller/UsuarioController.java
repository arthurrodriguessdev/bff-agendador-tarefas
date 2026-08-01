package agendador.bffagendadortarefas.controller;

import agendador.bffagendadortarefas.business.UsuarioService;
import agendador.bffagendadortarefas.business.dto.EnderecoDTO;
import agendador.bffagendadortarefas.business.dto.LoginDTO;
import agendador.bffagendadortarefas.business.dto.TelefoneDTO;
import agendador.bffagendadortarefas.business.dto.UsuarioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuário", description = "Cadastro e login de usuários")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Operation(summary = "Salvar Usuários", description = "Cria um novo usuário")
    @ApiResponse(responseCode = "200", description = "Usuário salvo com sucesso")
    @ApiResponse(responseCode = "400", description = "Usuário já cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    @PostMapping
    public ResponseEntity<UsuarioDTO> salvarUsuario(@RequestBody UsuarioDTO usuarioDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.salvarUsuario(usuarioDto));
    }

    @Operation(summary = "Buscar Dados Usuário Por ID", description = "Busca um usuário pelo ID")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarUsuario(@Parameter(description = "ID do usuário", required = true, example = "1")
                                                        @PathVariable Long id,
                                                    @Parameter(description = "Token de acesso", required = true, example = "Bearer H83798402803...")
                                                        @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.buscarUsuario(id, token));
    }

    @Operation(summary = "Buscar Usuário por E-mail", description = "Busca um usuário pelo e-mail")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorEmail(@RequestParam("email") String email,
                                                            @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email, token));
    }

    @Operation(summary = "Excluir Usuário", description = "Remove um usuário pelo ID")
    @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "401", description = "Token inválido")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id,
                                               @RequestHeader("Authorization") String token){
        usuarioService.deletarUsuario(id, token);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Atualizar Usuário", description = "Atualiza os dados do usuário")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "401", description = "Token inválido")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizarDadosUsuario(@PathVariable Long id,
                                                            @RequestBody UsuarioDTO usuarioDto,
                                                            @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizarDadosUsuario(usuarioDto, id, token));
    }

    @Operation(summary = "Atualizar Endereço", description = "Atualiza um endereço existente")
    @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    @ApiResponse(responseCode = "401", description = "Token inválido")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PutMapping("/enderecos/{id}")
    public ResponseEntity<EnderecoDTO> atualizarDadosEndereco(@PathVariable Long id,
                                                              @RequestBody EnderecoDTO enderecoDTO,
                                                              @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizarDadosEndereco(id, enderecoDTO, token));
    }

    @Operation(summary = "Atualizar Telefone", description = "Atualiza um telefone existente")
    @ApiResponse(responseCode = "200", description = "Telefone atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Telefone não encontrado")
    @ApiResponse(responseCode = "401", description = "Token inválido")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PutMapping("/telefones/{id}")
    public ResponseEntity<TelefoneDTO> atualizarDadosTelefone(@PathVariable Long id,
                                                              @RequestBody TelefoneDTO telefoneDTO,
                                                              @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizarDadosTelefone(id, telefoneDTO, token));
    }

    @Operation(summary = "Cadastrar Endereço", description = "Adiciona um endereço ao usuário autenticado")
    @ApiResponse(responseCode = "201", description = "Endereço cadastrado com sucesso")
    @ApiResponse(responseCode = "401", description = "Token inválido")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PostMapping("/enderecos")
    public ResponseEntity<EnderecoDTO> cadastrarEndereco(
            @RequestBody EnderecoDTO enderecoDTO, @RequestHeader("Authorization") String token){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrarEndereco(enderecoDTO, token));
    }

    @Operation(summary = "Cadastrar Telefone", description = "Adiciona um telefone ao usuário autenticado")
    @ApiResponse(responseCode = "201", description = "Telefone cadastrado com sucesso")
    @ApiResponse(responseCode = "401", description = "Token inválido")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PostMapping("/telefones")
    public ResponseEntity<TelefoneDTO> cadastrarTelefone(
            @RequestBody TelefoneDTO telefoneDTO, @RequestHeader("Authorization") String token){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrarTelefone(telefoneDTO, token));
    }

    @Operation(summary = "Login Usuários", description = "Realiza o login do usuário")
    @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        return ResponseEntity.ok(usuarioService.login(loginDTO));
    }

    // Refact
//    @GetMapping("/token/validade")
//    public ResponseEntity<Boolean> verificarValidadeToken(@RequestParam("token") String token){
//        return ResponseEntity.ok(usuarioService.verificarValidadeToken(token));
//    }
}