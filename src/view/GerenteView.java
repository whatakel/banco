package view;

import java.util.List;

import controller.GerenteController;
import model.Transacao;
import model.usuario.Gerente;
import model.usuario.Cliente;
import util.InputHelper;

public class GerenteView {
    public static void exibirMenu(Gerente gerente, GerenteController controller) {
        int opcao;
    
        do {
            System.out.println("\n=== MENU GERENTE ===");
            System.out.println("1. Listar clientes");
            System.out.println("2. Cadastrar novo cliente");
            System.out.println("3. Reverter transação de cliente");
            System.out.println("0. Sair");
            opcao = InputHelper.lerInt("Escolha: ");
    
            switch (opcao) {
                case 1:
                    List<Cliente> lista = controller.listarClientes();
                    if (lista.isEmpty()) {
                        System.out.println("Nenhum cliente encontrado.");
                    } else {
                        System.out.println("--- Clientes ---");
                        for (Cliente c : lista) {
                            System.out.printf("Nome: %s | CPF: %s | Saldo: R$ %.2f\n",
                                    c.getNome(), c.getCpf(), c.getConta().getSaldo());
                        }
                    }
                    break;
    
                case 2:
                    String nome = InputHelper.lerString("Nome: ");
                    String cpf = InputHelper.lerString("CPF: ");
                    String email = InputHelper.lerString("Email: ");
                    String senha = InputHelper.lerString("Senha: ");
                    controller.cadastrarCliente(nome, cpf, email, senha);
                    System.out.println("Cliente cadastrado com sucesso.");
                    break;
    
                case 3:
                    String cpfCliente = InputHelper.lerString("CPF do cliente: ");
                    int id = InputHelper.lerInt("ID da transação a reverter: ");
                    Cliente alvo = controller.listarClientes().stream()
                            .filter(c -> c.getCpf().equals(cpfCliente))
                            .findFirst().orElse(null);
    
                    if (alvo == null) {
                        System.out.println("Cliente não encontrado.");
                    } else {
                        boolean ok = controller.reverterTransacao(alvo, id);
                        if (ok) {
                            System.out.println("Transação revertida com sucesso.");
                        } else {
                            System.out.println("Não foi possível reverter a transação.");
                        }
                    }
                    break;
    
                case 0:
                    System.out.println("Saindo...");
                    break;
    
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
    
        } while (opcao != 0);
    }
}    

// @Override
// public void exibirMenu(){
// Scanner sc = new Scanner(System.in);
// int opcao;

// do{
// System.out.println("\n--- MENU GERENTE ---");
// System.out.println("1. Listar clientes");
// System.out.println("2. Reverter transacao");
// System.out.println("3. Cadastrar novo cliente");;
// System.out.println("0. Sair");
// System.out.println("Opção: ");
// opcao = sc.nextInt();

// switch(opcao){
// case 1:
// // chamda à controller/listagem
// break;

// case 2:
// // lógica de reversão (a implementar)
// break;

// case 3:
// // chamada à controller para cadastro
// break;
// }
// } while (opcao != 0);
// }