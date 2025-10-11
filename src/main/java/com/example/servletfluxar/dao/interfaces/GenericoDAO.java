package com.example.servletfluxar.dao.interfaces;

import java.util.Map;

/*
* Interface genérica que define os métodos que todos os DAOs terão em comum;
* Esses métodos serão usados para o CRUD do banco de dados;
* (Create, Read, Update e Delete) para todas as entidades do sistema.
* */
public interface GenericoDAO<T> {
    Map<Integer, T> listar(int pagina, int limit);
    T buscarPorId(int id);
    boolean inserir(T objeto);
    boolean alterar(T objeto);
    boolean deletarPorId(int id);
}