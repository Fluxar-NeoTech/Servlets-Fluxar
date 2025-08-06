package model;


public class Usuario {
    private int codigo;
    private String nome;
    private String email;
    private String cpf;
    private String turno;
    private String senha;
    private String tipo;

    public Usuario() {}

    public Usuario(int codigo, String nome, String email, String cpf, String turno, String senha, String tipo) {
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.turno = turno;
        this.senha = senha;
        this.tipo = tipo;
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}