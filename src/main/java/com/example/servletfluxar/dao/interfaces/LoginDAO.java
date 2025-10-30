package com.example.servletfluxar.dao.interfaces;

public interface LoginDAO<T> {
    /**
     * Método que busca um nome dentro do banco de dados
     * @param nome É o nome que deseja ser procurado na tabela
     * @return     Um objeto com todos os campos do registro
     */
    T buscarPorNome (String nome);

    /**
     * Método que procura um registro com um email
     * @param email Email que será procurado
     * @return      Um objeto com o registro que possui esse email
     */
    T buscarPorEmail (String email);

    /**
     * Método que altera a senha do usuário
     * @param email Email do usuário que terá senha alterada
     * @param senha Nova senha
     * @return      true se houver alteração
     */
    boolean alterarSenha (String email, String senha);

    /**
     * Método que valida se o usuário tem acesso, ou seja, autentica se o usuário está correto
     * @param email Email do usuário
     * @param senha Senha digitada pelo usuário
     * @return      Um objeto com o usuário cadastado
     */
    T autenticar (String email, String senha);
}