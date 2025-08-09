package model;
// Model para Setor
public class Setor {
    private int id;
    private String nome;
    private int id_unidade;

    public Setor(int id, String nome, int id_unidade) {
        this.id = id;
        this.nome = nome;
        this.id_unidade = id_unidade;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getId_unidade() {
        return id_unidade;
    }
}

