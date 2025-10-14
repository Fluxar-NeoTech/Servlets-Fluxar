package com.example.servletfluxar.dao.interfaces;

public interface LoginDAO<T> {
    T buscarPorNome (String nome);
    T buscarPorEmail (String email);
    boolean alterarSenha (String email, String senha);
    T autenticar (String email, String senha);
}