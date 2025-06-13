package util.excecoes;

public class SaldoInsuficienteException extends Exception {
    public SaldoInsuficienteException(){
        super("Saldo insuficiente para realizar esta operação.");
    }
}

//pode ser adicionados excessões para try catch para metodo reverter transação gerenteController