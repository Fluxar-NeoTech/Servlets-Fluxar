package model;


public class Insumo {
    private int codigo;
    private String nome;
    private int qtdOcupada;
    private int qtdMax;
    private String unidadeDeMedida;

    public Insumo() {}

    public Insumo(int codigo, String nome, int qtdOcupada, int qtdMax, String unidadeDeMedida) {
        this.codigo = codigo;
        this.nome = nome;
        this.qtdOcupada = qtdOcupada;
        this.qtdMax = qtdMax;
        this.unidadeDeMedida = unidadeDeMedida;
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getQtdOcupada() { return qtdOcupada; }
    public void setQtdOcupada(int qtdOcupada) { this.qtdOcupada = qtdOcupada; }
    public int getQtdMax() { return qtdMax; }
    public void setQtdMax(int qtdMax) { this.qtdMax = qtdMax; }
    public String getUnidadeDeMedida() { return unidadeDeMedida; }
    public void setUnidadeDeMedida(String unidadeDeMedida) { this.unidadeDeMedida = unidadeDeMedida; }
}