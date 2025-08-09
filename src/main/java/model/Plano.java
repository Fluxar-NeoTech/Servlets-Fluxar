package model;
public class Plano {
    private int id;
    private String nome;
    private double preco;
    private int duracao;

    public Plano() {}

    public Plano(int id, String nome, double preco, int duracao) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.duracao = duracao;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public int getDuracao() { return duracao; }
    public void setDuracao(int duracao) { this.duracao = duracao; }
}