package com.example.servletfluxar.model;

// Model para Funcionario
public class Funcionario {
//    Declaração de atributos:
    private int id;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private String cargo;
    private int idSetor;

//    Métodos contrutores:
    public Funcionario(int id, String nome, String sobrenome, String email, String senha, String cargo, int idSetor) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senha = senha;
        this.cargo = cargo;
        this.idSetor = idSetor;
    }

    public Funcionario() {
    }

    //    Getters e setters:
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getCargo() {
        return cargo;
    }

    public int getIdSetor() {
        return idSetor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setIdSetor(int idSetor) {
        this.idSetor = idSetor;
    }
}