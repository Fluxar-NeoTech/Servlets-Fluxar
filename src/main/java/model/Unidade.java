package model;

public class Unidade {
    private int id;
    private String nome;
    private int numero;
    private String cep;
    private String referencia;
    private String descricao;
    private int id_empresa;

    public Unidade(int id, String nome, int numero, String cep, String referencia, String descricao, int id_empresa) {
        this.id = id;
        this.nome = nome;
        this.numero = numero;
        this.cep = cep;
        this.referencia = referencia;
        this.descricao = descricao;
        this.id_empresa = id_empresa;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getNumero() {
        return numero;
    }

    public String getCep() {
        return cep;
    }

    public String getReferencia() {
        return referencia;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getId_empresa() {
        return id_empresa;
    }
}
