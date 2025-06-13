package view;

import controller.AutenticacaoController;
import model.usuario.Usuario;
import util.InputHelper;


public class LoginView {

    public static Usuario autenticar(AutenticacaoController controller){
        System.out.println("\n=== LOGIN ===");

        String email = InputHelper.lerString("Email: ");
        String senha = InputHelper.lerString("Senha: ");

        return controller.autenticar(email, senha);
    }
}
