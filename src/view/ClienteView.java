package view;

import java.time.format.DateTimeFormatter;
import java.util.List;

import controller.ClienteController;
import model.Transacao;
import model.usuario.Cliente;
import util.InputHelper;

public class ClienteView {
    public static void exibirMenu(Cliente cliente, ClienteController controller){
        int opcao;
        
        do {
            System.out.println("\n=== MENU CLIENTE ===");
            System.out.println("1. Ver saldo");
            System.out.println("2. Sacar");
            System.out.println("3. Depositar");
            System.out.println("4. Ver histórico");
            System.out.println("0. Logout");
            opcao = InputHelper.lerInt("Opção: ");

            switch (opcao){
                case 1: 
                    System.out.printf("Saldo Atual: R$ %.2f\n", controller.consultarSaldo());
                    break;
                
                case 2: 
                    double valor = InputHelper.lerDouble("Valor para saque: ");
                    String senha = "";
                    if(valor > 1000){
                        senha = InputHelper.lerString("Confirme sua senha");
                    }

                    try{
                        boolean sucesso = controller.sacar(valor, senha);
                        if(sucesso){
                            System.out.println("Saque realizado com sucesso.");
                        } else {
                            System.out.println("Senha incorreta.");
                        }
                    } catch (Exception e){
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 3:
                    double valorDeposito = InputHelper.lerDouble("Valor para depósito: ");
                    controller.depositar(valorDeposito);
                    System.out.println("Depósito realizado");
                    break;

                case 4: 
                    List<Transacao> lista = controller.listarTransacoes();
                    if (lista.isEmpty()){
                        System.out.println("Nenhuma transação encontrada.");
                    } else {
                        System.out.println("--- Histórico de Transações ---");
                        for(Transacao t : lista){
                            System.out.printf("ID: %d | %s | R$%.2f | %s\n",
                            t.getId(),
                            t.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                            t.getValor(),
                            t.getTipo().toUpperCase());
                        }
                    }
                    break;

                case 0: 
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida.");;
                    break;
            }
        } while (opcao != 0);
    }
}