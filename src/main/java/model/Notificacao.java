package model;

import java.sql.Timestamp;

public class Notificacao {
    private int id;
    private String titulo;
    private String mensagem;
    private Timestamp dataEnvio;
    private boolean lida;
    private int codUsuario;

    public Notificacao() {}

    public Notificacao(int id, String titulo, String mensagem, Timestamp dataEnvio, boolean lida, int codUsuario) {
        this.id = id;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.dataEnvio = dataEnvio;
        this.lida = lida;
        this.codUsuario = codUsuario;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    public Timestamp getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(Timestamp dataEnvio) { this.dataEnvio = dataEnvio; }
    public boolean getLida() { return lida; }
    public void setLida(boolean lida) { this.lida = lida; }
    public int getCodUsuario() { return codUsuario; }
    public void setCodUsuario(int codUsuario) { this.codUsuario = codUsuario; }
}