package com.example.servletfluxar.dao.interfaces;

import java.util.Map;

public interface ComLoginDAO<T> {
    T buscarPorNome (String nome);
    T buscarPorEmail (String email);
    boolean alterarSenha (String email, String senha);
    T autenticar (String email, String senha);
}