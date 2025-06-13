package model.usuario;

import model.Conta;
import model.Transacionavel;
import util.excecoes.SaldoInsuficienteException;

public class Cliente extends Usuario implements Transacionavel{
    private Conta conta;

    public Cliente(String nome, String cpf, String email, String senha, Conta conta){
        super(nome, cpf, email, senha);
        this.conta = conta;
    }

    @Override
    public void sacar(double valor) throws SaldoInsuficienteException{
        conta.sacar(valor);
    }
    
    @Override
    public void depositar(double valor){
        conta.depositar(valor);
    }

    public Conta getConta(){return conta;}
    public void setConta(Conta conta){this.conta = conta;}
}
