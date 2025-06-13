package controller;

import java.util.List;

import model.Conta;
import model.Transacao;
import model.usuario.Cliente;
import model.usuario.Usuario;
import util.Log;
import util.Persistencia;
import util.excecoes.SaldoInsuficienteException;

public class GerenteController {
    private final List<Usuario> usuarios;
    private final List<Cliente> clientes;

    public GerenteController(List<Usuario> usuarios, List<Cliente> clientes) {
        this.usuarios = usuarios;
        this.clientes = clientes;
    }

    public void cadastrarCliente(String nome, String cpf, String email, String senha){
        Conta novaConta = new Conta(cpf); // cria conta vazia
        Cliente novo = new Cliente(nome, cpf, email, senha, novaConta); // passa 5 parâmetros
        clientes.add(novo);
        usuarios.add(novo);
        Persistencia.salvarUsuarios(usuarios);
        Persistencia.salvarContas(clientes);
        System.out.println("Cliente criado com sucesso.");
    }


    public List<Cliente> listarClientes(){
        return clientes;
    }

    public boolean reverterTransacao(Cliente cliente, int idTransacao){
        List<Transacao> transacoes = cliente.getConta().getTransacoes();

        for(int i = transacoes.size() - 1; i >= 0; i--){
            Transacao t = transacoes.get(i);
            if(t.getId() == idTransacao){
                String tipo = t.getTipo().toLowerCase();

                if (tipo.equals("saque")){
                    cliente.depositar(t.getValor());
                    Log.registrar("Gerente reverteu saque ID " + idTransacao + " do cliente " + cliente.getNome());
                } else if (tipo.equals("deposito")){
                    try {
                        cliente.sacar(t.getValor());
                        Log.registrar("Gerente reverteu depósito ID " + idTransacao + " do cliente " + cliente.getNome());
                    } catch (SaldoInsuficienteException e){
                        return false;
                    }
                }

                transacoes.remove(i);
                return true;
            }
        }

        return false;
    }
}
