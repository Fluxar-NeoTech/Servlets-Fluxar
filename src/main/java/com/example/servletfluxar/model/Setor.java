package com.example.servletfluxar.model;

// Model para Setor
public class Setor {
//    Declarando os atributos:
    private int id;
    private String nome;
    private int idUnidade;

//    Construtores:
    public Setor(int id, String nome, int idUnidade) {
        this.id = id;
        this.nome = nome;
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

    public int getIdUnidade() {
        return idUnidade;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdUnidade(int idUnidade) {
        this.idUnidade = idUnidade;
    }
}

