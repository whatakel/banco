package controller;

import model.Transacao;
import model.usuario.Cliente;
import util.Log;
import util.excecoes.SaldoInsuficienteException;
import java.util.List;

public class ClienteController {
    private final Cliente cliente;

    public ClienteController(Cliente cliente){
        this.cliente = cliente;
    }

    public void depositar(double valor){
        cliente.depositar(valor);
        Log.registrar(cliente.getNome() + " depositou R$" + String.format("%.2f", valor));
    }

    public boolean sacar(double valor, String senhaDigitada) throws SaldoInsuficienteException{
        if(valor > 1000){
            if(!cliente.getSenha().equals(senhaDigitada)){
                return false;
            }
        }
        cliente.sacar(valor);
        Log.registrar(cliente.getNome() + " sacou R$" + String.format("%.2f", valor));
        return true;
    }

    public double consultarSaldo(){
        return cliente.getConta().getSaldo();
    }

    public List<Transacao> listarTransacoes(){
        return cliente.getConta().getTransacoes();
    }

}
