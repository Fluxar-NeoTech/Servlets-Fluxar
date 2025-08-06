package model;

import java.sql.Date;

public class Empresa {
    private int codigo;
    private String nome;
    private String cnpj;
    private String telefone;
    private String site;
    private String email;
    private String status;
    private int idPlano;
    private Date dtInicio;
    private int duracao;
    private String formaPag;

    public Empresa() {}

    public Empresa(int codigo, String nome, String cnpj, String telefone, String site, String email, String status, int idPlano, Date dtInicio, int duracao, String formaPag) {
        this.codigo = codigo;
        this.nome = nome;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.site = site;
        this.email = email;
        this.status = status;
        this.idPlano = idPlano;
        this.dtInicio = dtInicio;
        this.duracao = duracao;
        this.formaPag = formaPag;
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getSite() { return site; }
    public void setSite(String site) { this.site = site; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getIdPlano() { return idPlano; }
    public void setIdPlano(int idPlano) { this.idPlano = idPlano; }
    public Date getDtInicio() { return dtInicio; }
    public void setDtInicio(Date dtInicio) { this.dtInicio = dtInicio; }
    public int getDuracao() { return duracao; }
    public void setDuracao(int duracao) { this.duracao = duracao; }
    public String getFormaPag() { return formaPag; }
    public void setFormaPag(String formaPag) { this.formaPag = formaPag; }
}