package agendador.bffagendadortarefas.infraestructure.handler;

/**
 * Centraliza as mensagens padrão de erro utilizadas pela aplicação.
 *
 * Estas mensagens são utilizadas pelo tratamento global de exceções
 * quando não há uma mensagem específica disponível na resposta do serviço
 * OU quando é necessário retornar uma mensagem genérica ao cliente.
 */
public class GlobalErrorMessages {
    public static final String ERRO_INTERNO = "Ocorreu um erro interno. Tente novamente mais tarde.";
    public static final String REQUISICAO_INVALIDA = "A requisição contém dados inválidos.";
    public static final String ACESSO_NEGADO = "Você não tem permissão para acessar este recurso.";
    public static final String NAO_AUTORIZADO = "Autenticação necessária ou inválida.";
    public static final String RECURSO_NAO_ENCONTRADO = "Recurso não encontrado.";
    public static final String CONFLITO = "A operação não pode ser concluída devido a um conflito.";

    private GlobalErrorMessages(){}
}