package view;

import controller.AdmController;
import model.usuario.Adm;
import util.InputHelper;

public class AdmView {

    public static void exibirMenu(Adm adm, AdmController controller) {
        int opcao;

        do {
            System.out.println("\n=== MENU ADMINISTRADOR ===");
            System.out.println("1. Gerenciar Gerentes");
            System.out.println("2. Gerenciar Clientes");
            System.out.println("3. Financeiro");
            System.out.println("0. Logout");

            opcao = InputHelper.lerInt("Escolha: ");

            switch (opcao) {
                case 1 -> exibirSubmenuGerentes(controller);
                case 2 -> exibirSubmenuClientes(controller);
                case 3 -> exibirSubmenuFinanceiro(controller);
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    private static void exibirSubmenuGerentes(AdmController controller) {
        int opcao;
        do {
            System.out.println("\n--- GERENTES ---");
            System.out.println("1. Listar todos os gerentes");
            System.out.println("2. Cadastrar novo gerente");
            System.out.println("3. Excluir gerente");
            System.out.println("0. Voltar");

            opcao = InputHelper.lerInt("Escolha: ");

            switch (opcao) {
                case 1 -> controller.listarGerentes();
                case 2 -> {
                    String nome = InputHelper.lerString("Nome: ");
                    String cpf = InputHelper.lerString("CPF: ");
                    String email = InputHelper.lerString("Email: ");
                    String senha = InputHelper.lerString("Senha: ");
                    controller.cadastrarGerente(nome, cpf, email, senha);
                }
                case 3 -> {
                    String cpf = InputHelper.lerString("CPF do gerente a excluir: ");
                    controller.excluirGerentePorCPF(cpf);
                }
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void exibirSubmenuClientes(AdmController controller) {
        int opcao;
        do {
            System.out.println("\n--- CLIENTES ---");
            System.out.println("1. Listar todos os clientes");
            System.out.println("2. Cadastrar novo cliente");
            System.out.println("3. Excluir cliente");
            System.out.println("0. Voltar");

            opcao = InputHelper.lerInt("Escolha: ");

            switch (opcao) {
                case 1 -> controller.listarTodosClientes();
                case 2 -> {
                    String nome = InputHelper.lerString("Nome: ");
                    String cpf = InputHelper.lerString("CPF: ");
                    String email = InputHelper.lerString("Email: ");
                    String senha = InputHelper.lerString("Senha: ");
                    controller.cadastrarCliente(nome, cpf, email, senha);
                }
                case 3 -> {
                    String cpf = InputHelper.lerString("CPF do cliente a excluir: ");
                    controller.excluirClientePorCPF(cpf);
                }
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void exibirSubmenuFinanceiro(AdmController controller) {
        int opcao;
        do {
            System.out.println("\n--- FINANCEIRO ---");
            System.out.println("1. Ver total de dinheiro no banco");
            System.out.println("0. Voltar");

            opcao = InputHelper.lerInt("Escolha: ");

            switch (opcao) {
                case 1 -> {
                    double total = controller.calcularTotalDinheiroBanco();
                    System.out.printf("Total em caixa: R$ %.2f\n", total);
                }
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
}
