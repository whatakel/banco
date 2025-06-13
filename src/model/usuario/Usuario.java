package model.usuario;

import java.io.Serializable;

public abstract class Usuario implements Serializable{
    protected String nome;
    protected String cpf;
    protected String email;
    protected String senha;

    public Usuario(String nome, String cpf, String email, String senha){
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
    }

    public boolean login(String email, String senha){
        return this.email.equals(email) && this.senha.equals(senha);
    } // é preciso ser feito algum tratamento de erro aqui?

    public String getNome() { return nome;}
    public String getCpf() { return cpf;}
    public String getEmail() { return email;}
    public String getSenha() { return senha;}

    public void setNome(String nome){this.nome = nome;}
    public void setCpf(String cpf){this.cpf = cpf;}
    public void setEmail(String email){this.email = email;}
    public void setSenha(String senha){this.senha = senha;}
}
