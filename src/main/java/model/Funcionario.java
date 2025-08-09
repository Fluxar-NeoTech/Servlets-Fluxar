package model;

// Model para Funcionario
public class Funcionario {
    private int id;
    private String nome;
    private String sobrenome;
    private String dataNasc;
    private String telefone;
    private String senha;
    private String email;
    private String cargo;
    private int id_setor;

    public Funcionario(int id, String nome, String sobrenome, String dataNasc, String telefone, String senha, String email, String cargo, int id_setor) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.dataNasc = dataNasc;
        this.telefone = telefone;
        this.senha = senha;
        this.email = email;
        this.cargo = cargo;
        this.id_setor = id_setor;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getSenha() {
        return senha;
    }

    public String getEmail() {
        return email;
    }

    public String getCargo() {
        return cargo;
    }

    public int getId_setor() {
        return id_setor;
    }
}