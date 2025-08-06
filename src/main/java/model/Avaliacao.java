package model;

import java.sql.Timestamp;

public class Avaliacao {
    private int id;
    private int codEmpresa;
    private int codUsuario;
    private int nota;
    private String comentario;
    private Timestamp dataAvaliacao;

    public Avaliacao() {}

    public Avaliacao(int id, int codEmpresa, int codUsuario, int nota, String comentario, Timestamp dataAvaliacao) {
        this.id = id;
        this.codEmpresa = codEmpresa;
        this.codUsuario = codUsuario;
        this.nota = nota;
        this.comentario = comentario;
        this.dataAvaliacao = dataAvaliacao;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCodEmpresa() { return codEmpresa; }
    public void setCodEmpresa(int codEmpresa) { this.codEmpresa = codEmpresa; }
    public int getCodUsuario() { return codUsuario; }
    public void setCodUsuario(int codUsuario) { this.codUsuario = codUsuario; }
    public int getNota() { return nota; }
    public void setNota(int nota) { this.nota = nota; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    public Timestamp getDataAvaliacao() { return dataAvaliacao; }
    public void setDataAvaliacao(Timestamp dataAvaliacao) { this.dataAvaliacao = dataAvaliacao; }
}