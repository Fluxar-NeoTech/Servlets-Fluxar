package com.example.servletfluxar.model;
public class Plano {
//    Declaração de atributos:
    private int id;
    private String nome;
    private int tempo;
    private double preco;


//    Métodos construtores:
    public Plano(int id, String nome, int tempo,double preco) {
        this.id = id;
        this.nome = nome;
        this.tempo = tempo;
        this.preco = preco;
    }

    public Plano() {
    }

    //    Getters e setters:

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getTempo() {
        return tempo;
    }

    public double getPreco() {
        return preco;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}