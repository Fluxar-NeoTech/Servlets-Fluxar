package com.example.servletfluxar.model;

// Model da tabela endereço:
public class Endereco {
//    Declaração de atributos:
    private int id;
    private String cep;
    private int numero;
    private  String complemento;

//    Métodos construtores:

    public Endereco(int id, String cep, int numero, String complemento) {
        this.id = id;
        this.cep = cep;
        this.numero = numero;
        this.complemento = complemento;
    }

    public Endereco() {
    }

    //    Getters e setters:
    public int getId() {
        return id;
    }

    public String getCep() {
        return cep;
    }

    public int getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
}
