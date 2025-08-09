package model;

import java.sql.Date;

public class Empresa {
    //    Declaração de variáveis:
    private int id;
    private String status;
    private Date dt_inicio;
    private String cnpj;
    private String senha;
    private String nome;
    private String email;
    private int id_plano;

    //    Construtores:
    public Empresa(int id, String status, Date dt_inicio, String cnpj, String senha, String nome, String email, int id_plano) {
        this.id = id;
        this.status = status;
        this.dt_inicio = dt_inicio;
        this.cnpj = cnpj;
        this.senha = senha;
        this.nome = nome;
        this.email = email;
        this.id_plano = id_plano;
    }

    public Empresa(Date dt_inicio, String cnpj, String senha, String nome, String email, int id_plano) {
        this.dt_inicio = dt_inicio;
        this.cnpj = cnpj;
        this.senha = senha;
        this.nome = nome;
        this.email = email;
        this.id_plano = id_plano;
    }

//        Getters e setters:


    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public Date getDt_inicio() {
        return dt_inicio;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getSenha() {
        return senha;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public int getId_plano() {
        return id_plano;
    }
}