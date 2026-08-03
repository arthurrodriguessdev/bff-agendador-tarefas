package agendador.bffagendadortarefas.controller;

import agendador.bffagendadortarefas.business.UsuarioService;
import agendador.bffagendadortarefas.business.dto.request.*;
import agendador.bffagendadortarefas.business.dto.response.*;
import agendador.bffagendadortarefas.infraestructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuário", description = "Cadastro e login de usuários")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Operation(summary = "Salvar Usuários", description = "Cria um novo usuário")
    @ApiResponse(responseCode = "200", description = "Usuário salvo com sucesso")
    @ApiResponse(responseCode = "400", description = "Usuário já cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    @PostMapping
    public ResponseEntity<UsuarioDTOResponse> salvarUsuario(@RequestBody UsuarioDTORequest usuarioDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.salvarUsuario(usuarioDto));
    }

    @Operation(summary = "Buscar Dados Usuário Por ID", description = "Busca um usuário pelo ID")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTOResponse> buscarUsuario(@Parameter(description = "ID do usuário", required = true, example = "1")
                                                        @PathVariable Long id,
                                                            @Parameter(description = "Token de acesso", required = false, example = "Bearer H83798402803...")
                                                        @RequestHeader(name= "Authorization", required = false) String token){
        return ResponseEntity.ok(usuarioService.buscarUsuario(id, token));
    }

    @Operation(summary = "Buscar Usuário por E-mail", description = "Busca um usuário pelo e-mail")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping
    public ResponseEntity<UsuarioDTOResponse> buscarUsuarioPorEmail(@RequestParam("email") String email,
                                                                    @RequestHeader(name= "Authorization", required = false) String token){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email, token));
    }

    @Operation(summary = "Excluir Usuário", description = "Remove um usuário pelo ID")
    @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "401", description = "Token inválido")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id,
                                               @RequestHeader(name= "Authorization", required = false) String token){
        usuarioService.deletarUsuario(id, token);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Atualizar Usuário", description = "Atualiza os dados do usuário")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "401", description = "Token inválido")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTOResponse> atualizarDadosUsuario(@PathVariable Long id,
                                                                    @RequestBody UsuarioDTORequest usuarioDto,
                                                                    @RequestHeader(name= "Authorization", required = false) String token){
        return ResponseEntity.ok(usuarioService.atualizarDadosUsuario(usuarioDto, id, token));
    }

    @Operation(summary = "Atualizar Endereço", description = "Atualiza um endereço existente")
    @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    @ApiResponse(responseCode = "401", description = "Token inválido")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PutMapping("/enderecos/{id}")
    public ResponseEntity<EnderecoDTOResponse> atualizarDadosEndereco(@PathVariable Long id,
                                                                      @RequestBody EnderecoDTORequest enderecoDTO,
                                                                      @RequestHeader(name= "Authorization", required = false) String token){
        return ResponseEntity.ok(usuarioService.atualizarDadosEndereco(id, enderecoDTO, token));
    }

    @Operation(summary = "Atualizar Telefone", description = "Atualiza um telefone existente")
    @ApiResponse(responseCode = "200", description = "Telefone atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Telefone não encontrado")
    @ApiResponse(responseCode = "401", description = "Token inválido")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PutMapping("/telefones/{id}")
    public ResponseEntity<TelefoneDTOResponse> atualizarDadosTelefone(@PathVariable Long id,
                                                                      @RequestBody TelefoneDTORequest telefoneDTO,
                                                                      @RequestHeader(name= "Authorization", required = false) String token){
        return ResponseEntity.ok(usuarioService.atualizarDadosTelefone(id, telefoneDTO, token));
    }

    @Operation(summary = "Cadastrar Endereço", description = "Adiciona um endereço ao usuário autenticado")
    @ApiResponse(responseCode = "201", description = "Endereço cadastrado com sucesso")
    @ApiResponse(responseCode = "401", description = "Token inválido")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PostMapping("/enderecos")
    public ResponseEntity<EnderecoDTOResponse> cadastrarEndereco(
            @RequestBody EnderecoDTORequest enderecoDTO, @RequestHeader(name= "Authorization", required = false) String token){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrarEndereco(enderecoDTO, token));
    }

    @Operation(summary = "Cadastrar Telefone", description = "Adiciona um telefone ao usuário autenticado")
    @ApiResponse(responseCode = "201", description = "Telefone cadastrado com sucesso")
    @ApiResponse(responseCode = "401", description = "Token inválido")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PostMapping("/telefones")
    public ResponseEntity<TelefoneDTOResponse> cadastrarTelefone(
            @RequestBody TelefoneDTORequest telefoneDTO, @RequestHeader(name= "Authorization", required = false) String token){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrarTelefone(telefoneDTO, token));
    }

    @Operation(summary = "Login Usuários", description = "Realiza o login do usuário")
    @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTORequest loginDTO){
        return ResponseEntity.ok(usuarioService.login(loginDTO));
    }

    // Refact
//    @GetMapping("/token/validade")
//    public ResponseEntity<Boolean> verificarValidadeToken(@RequestParam("token") String token){
//        return ResponseEntity.ok(usuarioService.verificarValidadeToken(token));
//    }
}