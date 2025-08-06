package model;


public class Administrador {
    private int codigo;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private int codEmpresa;

    public Administrador() {}

    public Administrador(int codigo, String nome, String sobrenome, String email, String senha, int codEmpresa) {
        this.codigo = codigo;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senha = senha;
        this.codEmpresa = codEmpresa;
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getSobrenome() { return sobrenome; }
    public void setSobrenome(String sobrenome) { this.sobrenome = sobrenome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public int getCodEmpresa() { return codEmpresa; }
    public void setCodEmpresa(int codEmpresa) { this.codEmpresa = codEmpresa; }
}