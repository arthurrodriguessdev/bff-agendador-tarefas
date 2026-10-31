package agendador.bffagendadortarefas.business.utils;

public class TokenUtil {
    public static String formatarToken(String tokenFormatar){
        if(tokenFormatar.startsWith("Bearer ")){
            return tokenFormatar;
        }

        return "Bearer " + tokenFormatar;
    }
}