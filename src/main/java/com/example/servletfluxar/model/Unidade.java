package com.example.servletfluxar.model;

// Model para a tabela unidade do banco de dados:
public class Unidade {
    //    Declaração dos atributos:
    private int id;
    private String nome;
    private String cnpj;
    private String email;
    private int idEndereco;
    private int idEmpresa;

//    Métodos construtores:
    public Unidade(int id, String nome, String cnpj, String email, int idEndereco, int idEmpresa) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.idEndereco = idEndereco;
        this.idEmpresa = idEmpresa;
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

    public int getIdEndereco() {
        return idEndereco;
    }

    public int getIdEmpresa() {
        return idEmpresa;
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

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
}
