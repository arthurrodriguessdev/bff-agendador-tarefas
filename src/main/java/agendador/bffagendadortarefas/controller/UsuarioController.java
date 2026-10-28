package agendador.bffagendadortarefas.controller;

import agendador.bffagendadortarefas.business.UsuarioService;
import agendador.bffagendadortarefas.business.dto.request.*;
import agendador.bffagendadortarefas.business.dto.response.*;
import agendador.bffagendadortarefas.infraestructure.handler.GlobalErrorMessage;
import agendador.bffagendadortarefas.infraestructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@ApiResponse(
        responseCode = "default",
        description = "Erros não listados na operação usam o formato padrão de erro.",
        content = @Content(schema = @Schema(implementation = GlobalErrorMessage.class))
)
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Operation(summary = "Cadastrar usuário", description = "Cria uma conta de usuário.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado"),
            @ApiResponse(responseCode = "400", description = "Dados do usuário inválidos"),
            @ApiResponse(responseCode = "409", description = "Usuário já cadastrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PostMapping
    public ResponseEntity<UsuarioDTOResponse> salvarUsuario(@RequestBody UsuarioDTORequest usuarioDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.salvarUsuario(usuarioDto));
    }

    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna os dados do usuário identificado pelo ID. Requer autenticação."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTOResponse> buscarUsuario(
            @Parameter(description = "ID do usuário", required = true, example = "1")
            @PathVariable Long id,

            @Parameter(description = "Token de acesso", required = false, example = "Bearer H83798402803...")
            @RequestHeader(name= "Authorization", required = false) String token){
        return ResponseEntity.ok(usuarioService.buscarUsuario(id, token));
    }

    @Operation(
            summary = "Buscar usuário por e-mail",
            description = "Retorna os dados do usuário correspondente ao e-mail informado. Requer autenticação."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "400", description = "E-mail não informado ou inválido"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
    @GetMapping
    public ResponseEntity<UsuarioDTOResponse> buscarUsuarioPorEmail(
            @Parameter(description = "E-mail do usuário", example = "usuario@exemplo.com", required = true)
            @RequestParam("email") String email,
            @RequestHeader(name= "Authorization", required = false) String token){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email, token));
    }

    @Operation(
            summary = "Excluir usuário",
            description = "Remove o usuário identificado pelo ID. Requer autenticação."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário removido"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(
            @Parameter(description = "Identificador do usuário", example = "1")
            @PathVariable Long id,
            @RequestHeader(name= "Authorization", required = false) String token){
        usuarioService.deletarUsuario(id, token);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza os dados do usuário indicado pelo ID. Requer autenticação."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado"),
            @ApiResponse(responseCode = "400", description = "Dados do usuário inválidos"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito ao atualizar o usuário"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTOResponse> atualizarDadosUsuario(
            @Parameter(description = "Identificador do usuário", example = "1")
            @PathVariable Long id,
            @RequestBody UsuarioDTORequest usuarioDto,
            @RequestHeader(name= "Authorization", required = false) String token){
        return ResponseEntity.ok(usuarioService.atualizarDadosUsuario(usuarioDto, id, token));
    }

    @Operation(summary = "Atualizar endereço", description = "Atualiza o endereço identificado pelo ID. Requer autenticação.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Endereço atualizado"),
            @ApiResponse(responseCode = "400", description = "Dados do endereço inválidos"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
    @PutMapping("/enderecos/{id}")
    public ResponseEntity<EnderecoDTOResponse> atualizarDadosEndereco(
            @Parameter(description = "Identificador do endereço", example = "1")
            @PathVariable Long id,
            @RequestBody EnderecoDTORequest enderecoDTO,
            @RequestHeader(name= "Authorization", required = false) String token){
        return ResponseEntity.ok(usuarioService.atualizarDadosEndereco(id, enderecoDTO, token));
    }

    @Operation(summary = "Atualizar telefone", description = "Atualiza o telefone identificado pelo ID. Requer autenticação.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Telefone atualizado"),
            @ApiResponse(responseCode = "400", description = "Dados do telefone inválidos"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Telefone não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
    @PutMapping("/telefones/{id}")
    public ResponseEntity<TelefoneDTOResponse> atualizarDadosTelefone(
            @Parameter(description = "Identificador do telefone", example = "1")
            @PathVariable Long id,
            @RequestBody TelefoneDTORequest telefoneDTO,
            @RequestHeader(name= "Authorization", required = false) String token){
        return ResponseEntity.ok(usuarioService.atualizarDadosTelefone(id, telefoneDTO, token));
    }

    @Operation(summary = "Cadastrar endereço", description = "Adiciona um endereço ao usuário autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Endereço cadastrado"),
            @ApiResponse(responseCode = "400", description = "Dados do endereço inválidos"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
    @PostMapping("/enderecos")
    public ResponseEntity<EnderecoDTOResponse> cadastrarEndereco(
            @RequestBody EnderecoDTORequest enderecoDTO, @RequestHeader(name= "Authorization", required = false) String token){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrarEndereco(enderecoDTO, token));
    }

    @Operation(summary = "Cadastrar telefone", description = "Adiciona um telefone ao usuário autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Telefone cadastrado"),
            @ApiResponse(responseCode = "400", description = "Dados do telefone inválidos"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
    @PostMapping("/telefones")
    public ResponseEntity<TelefoneDTOResponse> cadastrarTelefone(
            @RequestBody TelefoneDTORequest telefoneDTO, @RequestHeader(name= "Authorization", required = false) String token){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrarTelefone(telefoneDTO, token));
    }

    @Operation(summary = "Autenticar usuário", description = "Valida as credenciais e retorna o token de acesso.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token emitido"),
            @ApiResponse(responseCode = "400", description = "Credenciais não informadas"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTORequest loginDTO){
        return ResponseEntity.ok(usuarioService.login(loginDTO));
    }

    @Operation(summary = "Consultar CEP", description = "Consulta os dados de endereço correspondentes ao CEP informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dados do CEP retornados"),
            @ApiResponse(responseCode = "400", description = "CEP não informado ou inválido"),
            @ApiResponse(responseCode = "500", description = "Falha ao consultar o serviço de CEP")
    })
    @GetMapping("/enderecos/cep")
    public ResponseEntity<ViaCepDTO> buscarDadosCep(
            @Parameter(description = "CEP a consultar", example = "01001000", required = true)
            @RequestParam String cep){
        return ResponseEntity.ok(usuarioService.buscarDadosCep(cep));
    }
}