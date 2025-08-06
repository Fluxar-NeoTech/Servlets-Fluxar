package model;


public class Setor {
    private int codigo;
    private String nome;
    private int codUnidade;
    private int codUsuarioGestor;
    private int codUsuarioAnalista;

    public Setor() {}

    public Setor(int codigo, String nome, int codUnidade, int codUsuarioGestor, int codUsuarioAnalista) {
        this.codigo = codigo;
        this.nome = nome;
        this.codUnidade = codUnidade;
        this.codUsuarioGestor = codUsuarioGestor;
        this.codUsuarioAnalista = codUsuarioAnalista;
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getCodUnidade() { return codUnidade; }
    public void setCodUnidade(int codUnidade) { this.codUnidade = codUnidade; }
    public int getCodUsuarioGestor() { return codUsuarioGestor; }
    public void setCodUsuarioGestor(int codUsuarioGestor) { this.codUsuarioGestor = codUsuarioGestor; }
    public int getCodUsuarioAnalista() { return codUsuarioAnalista; }
    public void setCodUsuarioAnalista(int codUsuarioAnalista) { this.codUsuarioAnalista = codUsuarioAnalista; }
}