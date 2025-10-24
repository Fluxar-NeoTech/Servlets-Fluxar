package com.example.servletfluxar.model;

import java.time.LocalDate;

public class Assinatura {
//    Desclaração de variáveis:
    private int id;
    private LocalDate dtInicio;
    private LocalDate dtFim;
    private char status;
    private int idEmpresa;
    private int idPlano;
    private String formaPagamento;

//    Métodos construtores:

    public Assinatura(int id, LocalDate dtInicio, LocalDate dtFim, char status, int idEmpresa, int idPlano, String formaPagamento) {
        this.id = id;
        this.dtInicio = dtInicio;
        this.dtFim = dtFim;
        this.status = status;
        this.idEmpresa = idEmpresa;
        this.idPlano = idPlano;
        this.formaPagamento = formaPagamento;
    }

    public Assinatura() {
    }

    //    Getters e setters:
    public int getId() {
        return id;
    }

    public LocalDate getDtInicio() {
        return dtInicio;
    }

    public LocalDate getDtFim() {
        return dtFim;
    }

    public char getStatus() {
        return status;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public int getIdPlano() {
        return idPlano;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDtInicio(LocalDate dtInicio) {
        this.dtInicio = dtInicio;
    }

    public void setDtFim(LocalDate dtFim) {
        this.dtFim = dtFim;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public void setIdPlano(int idPlano) {
        this.idPlano = idPlano;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}