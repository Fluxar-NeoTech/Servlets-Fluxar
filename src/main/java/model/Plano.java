package model;

import java.math.BigDecimal;

public class Plano {
    private int id;
    private String nome;
    private boolean alertas;
    private boolean saidaEntradas;
    private int qtdRedirecionar;
    private boolean relatorioPdf;
    private boolean relatorioExcel;
    private int tempoSuporteH;
    private BigDecimal valorMensal;
    private BigDecimal valorAnual;
    private int qtdUsuarios;

    public Plano() {}

    public Plano(int id, String nome, boolean alertas, boolean saidaEntradas, int qtdRedirecionar, boolean relatorioPdf, boolean relatorioExcel, int tempoSuporteH, BigDecimal valorMensal, BigDecimal valorAnual, int qtdUsuarios) {
        this.id = id;
        this.nome = nome;
        this.alertas = alertas;
        this.saidaEntradas = saidaEntradas;
        this.qtdRedirecionar = qtdRedirecionar;
        this.relatorioPdf = relatorioPdf;
        this.relatorioExcel = relatorioExcel;
        this.tempoSuporteH = tempoSuporteH;
        this.valorMensal = valorMensal;
        this.valorAnual = valorAnual;
        this.qtdUsuarios = qtdUsuarios;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public boolean getAlertas() { return alertas; }
    public void setAlertas(boolean alertas) { this.alertas = alertas; }
    public boolean getSaidaEntradas() { return saidaEntradas; }
    public void setSaidaEntradas(boolean saidaEntradas) { this.saidaEntradas = saidaEntradas; }
    public int getQtdRedirecionar() { return qtdRedirecionar; }
    public void setQtdRedirecionar(int qtdRedirecionar) { this.qtdRedirecionar = qtdRedirecionar; }
    public boolean getRelatorioPdf() { return relatorioPdf; }
    public void setRelatorioPdf(boolean relatorioPdf) { this.relatorioPdf = relatorioPdf; }
    public boolean getRelatorioExcel() { return relatorioExcel; }
    public void setRelatorioExcel(boolean relatorioExcel) { this.relatorioExcel = relatorioExcel; }
    public int getTempoSuporteH() { return tempoSuporteH; }
    public void setTempoSuporteH(int tempoSuporteH) { this.tempoSuporteH = tempoSuporteH; }
    public BigDecimal getValorMensal() { return valorMensal; }
    public void setValorMensal(BigDecimal valorMensal) { this.valorMensal = valorMensal; }
    public BigDecimal getValorAnual() { return valorAnual; }
    public void setValorAnual(BigDecimal valorAnual) { this.valorAnual = valorAnual; }
    public int getQtdUsuarios() { return qtdUsuarios; }
    public void setQtdUsuarios(int qtdUsuarios) { this.qtdUsuarios = qtdUsuarios; }
}