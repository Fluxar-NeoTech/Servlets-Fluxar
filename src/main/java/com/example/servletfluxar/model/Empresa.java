package com.example.servletfluxar.model;

import java.sql.Date;

public class Empresa {
//    Declaração de variáveis:
    private int id;
    private String nome;
    private String cnpj;
    private String email;
    private String senha;
    private Date dtCadastro;

//    Métodos construtores:
    public Empresa(int id, String nome, String cnpj, String email, String senha, Date dtCadastro) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.senha = senha;
        this.dtCadastro = dtCadastro;
    }
    public Empresa() {}

//        Getters e setters:

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public Date getDtCadastro() {
        return dtCadastro;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setDataCadastro(Date dtCadastro) {
        this.dtCadastro = dtCadastro;
    }
}