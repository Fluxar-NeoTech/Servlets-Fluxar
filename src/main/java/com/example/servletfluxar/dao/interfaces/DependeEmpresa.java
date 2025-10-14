package com.example.servletfluxar.dao.interfaces;

import java.util.Map;

/**
 * Interface que determina os métodos que todas as tabelas que tenham
 * algum relacionamento com uma empresa devem implementar.
 */
public interface DependeEmpresa<T> {

    /**
     * Lista os registros pertencentes a uma empresa com paginação.
     *
     * @param pagina    número da página atual
     * @param limite    quantidade de itens por página
     * @param idEmpresa ID da empresa cujos registros devem ser listados
     * @return          um Map onde a chave é o ID do objeto e o valor é o próprio objeto
     */
    Map<Integer, T> listarPorIdEmpresa(int pagina, int limite, int idEmpresa);

    /**
     * Conta o número total de registros vinculados a uma empresa.
     *
     * @param idEmpresa ID da empresa
     * @return          quantidade total de registros
     */
    int contarPorIdEmpresa(int idEmpresa);
}