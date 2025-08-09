package model;

import java.util.Date;

public class Avaliacao {
    private int id;
    private String comentarios;
    private int nota;
    private String email;
    private Date data;

    public Avaliacao(int id, String comentarios, int nota, String email, Date data) {
        this.id = id;
        this.comentarios = comentarios;
        this.nota = nota;
        this.email = email;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public String getComentarios() {
        return comentarios;
    }

    public int getNota() {
        return nota;
    }

    public String getEmail() {
        return email;
    }

    public Date getData() {
        return data;
    }
}
