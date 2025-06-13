package controller;

import java.util.List;
import java.util.Scanner;

import model.Conta;
import model.Transacao;
import model.usuario.Usuario;
import model.usuario.Cliente;
import model.usuario.Gerente;
import model.usuario.Adm;
import view.LoginView;
import view.ClienteView;
import view.GerenteView;
import view.AdmView;
import util.Persistencia;

public class SistemaController {
    private List<Usuario> usuarios;
    private List<Cliente> clientes;
    private List<Gerente> gerentes;
    private List<Conta> contas;

    public void iniciar() {
        carregarDados();
        Scanner sc = new Scanner(System.in);
        Usuario usuarioLogado;
        AutenticacaoController auth = new AutenticacaoController(usuarios);
        int maiorId = Persistencia.obterMaiorIdTransacao(clientes);
        Transacao.setContador(maiorId + 1);

        do {
            usuarioLogado = null;
            while (usuarioLogado == null) {
                usuarioLogado = LoginView.autenticar(auth);
            }

            if (usuarioLogado instanceof Adm) {
                AdmController admController = new AdmController(usuarios, gerentes, clientes, contas);
                AdmView.exibirMenu((Adm) usuarioLogado, admController);
            } else if (usuarioLogado instanceof Gerente) {
                GerenteController gerenteController = new GerenteController(usuarios, clientes);
                GerenteView.exibirMenu((Gerente) usuarioLogado, gerenteController);
            } else if (usuarioLogado instanceof Cliente) {
                ClienteController clienteController = new ClienteController((Cliente) usuarioLogado);
                ClienteView.exibirMenu((Cliente) usuarioLogado, clienteController);
            }

            salvarDados();

            System.out.println("\nDeseja fazer login com outro usuário? (s/n)");
        } while (sc.nextLine().trim().equalsIgnoreCase("s"));

    }


    private void carregarDados(){
        usuarios = Persistencia.carregarUsuarios();
        contas = Persistencia.carregarContas();
        clientes = Persistencia.filtrarClientes(usuarios);
        gerentes = Persistencia.filtrarGerentes(usuarios);
    }

    private void salvarDados(){
        Persistencia.salvarUsuarios(usuarios);
        Persistencia.salvarContas(clientes);
        Persistencia.salvarTransacoes(clientes);
    }
}
