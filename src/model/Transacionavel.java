package model;

import util.excecoes.SaldoInsuficienteException;

public interface Transacionavel {
    void sacar(double valor) throws SaldoInsuficienteException;
    void depositar(double valor);
}
