package com.example.servletfluxar.model;

// Model para a tabela unidade do banco de dados:
public class Unidade {
    //    Declaração dos atributos:
    private int id;
    private String nome;
    private String cnpj;
    private String email;
    private int idEmpresa;
    private String cep;
    private int numero;
    private String complemento;

//    Métodos construtores:


    public Unidade(int id, String nome, String cnpj, String email, int idEmpresa, String cep, int numero, String complemento) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.idEmpresa = idEmpresa;
        this.cep = cep;
        this.numero = numero;
        this.complemento = complemento;
    }

    public Unidade() {
    }

    //    Getters e setters:

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

    public int getIdEmpresa() {
        return idEmpresa;
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
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