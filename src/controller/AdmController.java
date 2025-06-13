package controller;

import model.Conta;
import model.usuario.Cliente;
import model.usuario.Gerente;
import model.usuario.Usuario;
import util.Persistencia;

import java.util.List;

public class AdmController {
    private final List<Usuario> usuarios;
    private final List<Gerente> gerentes;
    private final List<Cliente> clientes;
    private final List<Conta> contas;


    public AdmController(List<Usuario> usuarios, List<Gerente> gerentes, List<Cliente> clientes, List<Conta> contas) {
        this.usuarios = usuarios;
        this.gerentes = gerentes;
        this.clientes = clientes;
        this.contas = contas;
    }

    public void cadastrarGerente(String nome, String cpf, String email, String senha) {
        Gerente g = new Gerente(nome, cpf, email, senha);
        usuarios.add(g);
        gerentes.add(g);
        Persistencia.salvarUsuarios(usuarios);  // Salva no JSON
        System.out.println("Gerente salvo com sucesso.");
    }

    public void cadastrarCliente(String nome, String cpf, String email, String senha) {
        Conta conta = new Conta(cpf);
        Cliente cliente = new Cliente(nome, cpf, email, senha, conta); // Instancia o cliente com a conta

        usuarios.add(cliente);
        clientes.add(cliente);

        contas.add(conta);

        Persistencia.salvarUsuarios(usuarios);
        Persistencia.salvarContas(clientes);

        System.out.println("Cliente cadastrado com sucesso.");
    }


    public boolean excluirGerentePorCPF(String cpf) {
        boolean removidoGerente = gerentes.removeIf(g -> g.getCpf().equals(cpf));
        boolean removidoUsuario = usuarios.removeIf(u -> u instanceof Gerente && u.getCpf().equals(cpf));

        if (removidoUsuario) {
            Persistencia.salvarUsuarios(usuarios);
            System.out.println("Gerente excluído com sucesso.");
        } else {
            System.out.println("Nenhum gerente encontrado com esse CPF.");
        }

        return removidoGerente && removidoUsuario;
    }


    public void listarGerentes() {
        if (gerentes.isEmpty()) {
            System.out.println("Nenhum gerente cadastrado.");
        } else {
            System.out.println("=== Lista de Gerentes ===");
            for (Gerente g : gerentes) {
                System.out.println("- " + g.getNome() + " | CPF: " + g.getCpf() + " | Email: " + g.getEmail());
            }
        }
    }


    public boolean excluirClientePorCPF(String cpf) {
        boolean removidoCliente = clientes.removeIf(c -> c.getCpf().equals(cpf));
        boolean removidoUsuario = usuarios.removeIf(u -> u instanceof Cliente && u.getCpf().equals(cpf));
        boolean removidoConta = contas.removeIf(ct -> ct.getCpfDono().equals(cpf));

        if (removidoCliente || removidoUsuario || removidoConta) {
            Persistencia.salvarUsuarios(usuarios);
            Persistencia.salvarContas(clientes);  // salvar apenas contas de clientes ativos
            Persistencia.salvarTransacoes(clientes);  // caso queira remover transações também
            System.out.println("Cliente excluído com sucesso.");
            return true;
        } else {
            System.out.println("Nenhum cliente encontrado com esse CPF.");
            return false;
        }
    }


    public List<Cliente> listarClientes() {
        return clientes;
    }

    public void listarTodosClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            System.out.println("=== Lista de Clientes ===");
            for (Cliente c : clientes) {
                System.out.println("- " + c.getNome() + " | CPF: " + c.getCpf() + " | Email: " + c.getEmail());
            }
        }
    }

    public double calcularTotalDinheiroBanco() {
        double total = 0;
        for (Cliente c : clientes) {
            total += c.getConta().getSaldo();
        }
        return total;
    }
}
