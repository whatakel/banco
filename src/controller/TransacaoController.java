package controller;

import java.util.List;
import model.Transacao;
import model.usuario.Cliente;
import util.excecoes.SaldoInsuficienteException;

public class TransacaoController {
    private final List<Cliente> clientes;

    public TransacaoController(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Transacao buscarTransacaoPorId(int id) {
        for (Cliente c : clientes) {
            for (Transacao t : c.getConta().getTransacoes()) {
                if (t.getId() == id) {
                    return t;
                }
            }
        }
        return null;
    }

    public boolean reverterTransacaoGlobal(int id) {
        for (Cliente c : clientes) {
            List<Transacao> transacoes = c.getConta().getTransacoes();

            for (int i = transacoes.size() - 1; i >= 0; i--) {
                Transacao t = transacoes.get(i);
                if (t.getId() == id) {
                    try {
                        if (t.getTipo().equalsIgnoreCase("saque")) {
                            c.depositar(t.getValor());
                        } else if (t.getTipo().equalsIgnoreCase("deposito")) {
                            c.sacar(t.getValor());
                        }
                        transacoes.remove(i);
                        return true;
                    } catch (SaldoInsuficienteException e) {
                        return false;
                    }
                }
            }
        }
        return false;
    }
}