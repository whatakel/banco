package view;

import java.util.List;

import controller.AdmController;
import model.usuario.Adm;
import model.usuario.Cliente;
import model.usuario.Gerente;
import util.InputHelper;


public class AdmView {
    public static void exibirMenu(Adm adm, AdmController controller) {
        int opcao;
    
        do {
            System.out.println("\n=== MENU ADMINISTRADOR ===");
            System.out.println("1. Listar gerentes");
            System.out.println("2. Listar todos os clientes");
            System.out.println("3. Cadastrar novo gerente");
            System.out.println("4. Cadastrar novo cliente");
            System.out.println("5. Excluir gerente");
            System.out.println("6. Excluir cliente");
            System.out.println("7. Ver total de dinheiro no banco");
            System.out.println("0. Sair");

            opcao = InputHelper.lerInt("Escolha: ");

            switch (opcao) {
                case 1:
                    controller.listarGerentes(); // já deve existir
                    break;
                case 2:
                    controller.listarTodosClientes();
                    break;
                case 3: {
                    String nome = InputHelper.lerString("Nome: ");
                    String cpf = InputHelper.lerString("CPF: ");
                    String email = InputHelper.lerString("Email: ");
                    String senha = InputHelper.lerString("Senha: ");
                    controller.cadastrarGerente(nome, cpf, email, senha);
                    break;
                }
                case 4: {
                    String nome = InputHelper.lerString("Nome: ");
                    String cpf = InputHelper.lerString("CPF: ");
                    String email = InputHelper.lerString("Email: ");
                    String senha = InputHelper.lerString("Senha: ");
                    controller.cadastrarCliente(nome, cpf, email, senha);
                    break;
                }
                case 5: {
                    String cpf = InputHelper.lerString("CPF do gerente a excluir: ");
                    controller.excluirGerentePorCPF(cpf);
                    break;
                }
                case 6: {
                    String cpf = InputHelper.lerString("CPF do cliente a excluir: ");
                    controller.excluirClientePorCPF(cpf);
                    break;
                }
                case 7: {
                    double total = controller.calcularTotalDinheiroBanco();
                    System.out.printf("Total em caixa: R$ %.2f\n", total);
                    break;
                }
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
//     public void exibirMenu(){
//         Scanner sc = new Scanner(System.in);
//         int opcao;

//         do {
//             System.out.println("\n--- MENU ADMINISTRADOR ---");
//             System.out.println("1. Gerenciar gerentes");
//             System.out.println("2. Gerenciar clientes");
//             System.out.println("3. Visualizar total no banco");
//             System.out.println("0. Sair");
//             System.out.print("Opção: ");
//             opcao = sc.nextInt(0);

//             switch (opcao){
//                 case 1:
//                     // cadastrar/excluir gerentes
//                     break;

//                 case 2: 
//                     //cadastrar/excluir clientes
//                     break;

//                 case 3:
//                     //somar todos os saldos
//                     break;
//             }
//         } while (opcao != 0);
//     }
