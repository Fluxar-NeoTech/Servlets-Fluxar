package com.example.servletfluxar.dao.interfaces;

import java.util.List;
import java.util.Map;

/**
* Interface genérica que define os métodos que todos os DAOs terão em comum;
* Esses métodos serão usados para o CRUD do banco de dados;
* (Create, Read, Update e Delete) para todas as entidades do sistema.
* */
public interface DAO<T> {
    /**
     * Conta o número total de registros.
     *
     * @return          quantidade total de registros
     */
    int contar();

    /**
     * Transforma todos os registros em um map
     * @param pagina Número página atual
     * @param limit Quantidade de resgistros por página;
     * @return      Map onde a chave é o id do objeto e o valor o objeto.
     */
    List<T> listar(int pagina, int limit);

    /**
     * Busca um registro pelo id
     * @param id Id do registro
     * @return   Um objeto com os atributos sendo os campos do resgistro
     */
    T buscarPorId(int id);

    /**
     * Inseri um registro no banco de dados
     * @param objeto Objeto que representa o que será inserido no banco de dados
     * @return       true se a inserção ocorrer
     */
    boolean inserir(T objeto);
    boolean alterar(T objeto);
    boolean deletarPorId(int id);
}