package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Transacao implements Serializable{
    private static int contador = 1;

    private int id;
    private LocalDateTime dataHora;
    private double valor;
    private String tipo;

    public Transacao(double valor, String tipo){
        this.id = contador++;
        this.valor = valor;
        this.tipo = tipo;
        this.dataHora = LocalDateTime.now(); //talvez seja necessario formatar a da ta
    }

    public int getId(){return id;}
    public LocalDateTime getDataHora(){return dataHora;}
    public double getValor(){return valor;}
    public String getTipo(){return tipo;}


}
