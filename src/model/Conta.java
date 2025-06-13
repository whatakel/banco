package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import util.excecoes.SaldoInsuficienteException;

public class Conta implements Serializable{
    private String cpfDono;
    private double saldo;
    private List<Transacao> transacoes;

    public Conta(String cpfDono){
        this.cpfDono = cpfDono;
        this.saldo = 0.0;
        this.transacoes = new ArrayList<>();
    }

    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor > saldo) throw new SaldoInsuficienteException();
        saldo -= valor;
        adicionarTransacao(new Transacao(valor, "saque"));
    }

    public void depositar(double valor){
        saldo += valor;
        adicionarTransacao(new Transacao(valor, "deposito"));
    }

    public void adicionarTransacao(Transacao transacao){
        this.transacoes.add(transacao);
    }

    public String getCpfDono(){return cpfDono;}
    public double getSaldo(){return saldo;}
    public List<Transacao> getTransacoes(){return new ArrayList<>(transacoes);}
}
