package controller;

import model.usuario.Usuario;
import java.util.List;

public class AutenticacaoController {
    private final List<Usuario> usuarios;

    public AutenticacaoController(List<Usuario> usuarios){
        this.usuarios = usuarios;
    }

    public Usuario autenticar(String email, String senha){
        for(Usuario u : usuarios){
            if(u.login(email, senha)){
                System.out.println("Usuario autenticado com sucesso!");
                return u;
            }
        }
        System.out.print("Usuario não encontrado.");
        return null;
    }
}
