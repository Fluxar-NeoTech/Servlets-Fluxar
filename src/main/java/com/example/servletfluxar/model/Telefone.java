package com.example.servletfluxar.model;

public class Telefone {
//    Declaração de atributos:
    private int id;
    private String numero;
    private int idEmpresa;

//    Métodos construtores:
    public Telefone(int id, String numero, int idEmpresa) {
        this.id = id;
        this.numero = numero;
        idEmpresa = idEmpresa;
    }

    public Telefone() {
    }

//    Getters e setters:

    public int getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setIdEmpresa(int idEmpresa) {
        idEmpresa = idEmpresa;
    }
}