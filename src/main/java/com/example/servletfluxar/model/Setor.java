package com.example.servletfluxar.model;

// Model para Setor
public class Setor {
//    Declarando os atributos:
    private int id;
    private String nome;
    private String descricao;
    private int idUnidade;

//    Métodos construtores:
    public Setor(int id, String nome, String descricao, int idUnidade) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.idUnidade = idUnidade;
    }

    public Setor() {
    }

    //    Getters e setters:

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getIdUnidade() {
        return idUnidade;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setIdUnidade(int idUnidade) {
        this.idUnidade = idUnidade;
    }
}

