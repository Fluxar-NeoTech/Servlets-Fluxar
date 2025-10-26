package com.example.servletfluxar.util;

/**
 * Classe feita para transformar o input do usuário para o padrão definido pela equipe
 * de dados para o próprio banco de dados
 */
public class RegrasBanco {
    /**
     * Método para deixar apenas os números do cnpj:
     * @param cnpj É o cnpj do input do usuário
     * @return   O cnpj sem . nem - nem / apenas os números
     */
    public static String cnpj (String cnpj) {

        return cnpj.replaceAll("[^\\d]", "");
    }

    /**
     * Método para transformar o telefone em apenas números
     * @param telefone É o telefone do input do usuário
     * @return         O telefone apenas com os números
     */
    public static String telefone (String telefone) {

        return telefone.replaceAll("[^\\d]", "");
    }

    /**
     * Método para colocar em Capitalize o nome da empresa posto pelo usuário
     * @param nome É o nome da empresa
     * @return      O nome da empresa com primeria letra maíuscula
     */
    public static String nomeCapitalize (String nome){
        char primeiraLetra = nome.charAt(0);
        char primeiraLetraMaiuscula = Character.toUpperCase(primeiraLetra);
        return nome.replace(primeiraLetra, primeiraLetraMaiuscula);
    }

    /**
     * Método para separar o nome completo para uma nome + sobrenomes
     * @param nomeCompleto É o nome inteiro inputado pelo usuário
     * @return             Uma lista com os elementos sendo [nome, sobrenomes]
     */
    public static String[] separarNomeCompleto(String nomeCompleto) {
//        Declaração de variáveis:
        String nome;
        String sobrenomes;
        int primeiroEspaco;

        nomeCompleto = nomeCompleto.trim();

        // Encontra o índice do primeiro espaço
        primeiroEspaco = nomeCompleto.indexOf(" ");

        if (primeiroEspaco == -1) {
//      Se não houver espaço, o nome inteiro é o primeiro nome
            nome = nomeCapitalize(nomeCompleto);
            sobrenomes = "";
        } else {
//          Se houver espaço, separa o primeiro nome do restante
            nome = nomeCapitalize(nomeCompleto.substring(0, primeiroEspaco));
            sobrenomes = nomeCapitalize(nomeCompleto.substring(primeiroEspaco + 1));
        }

        return new String[]{nome, sobrenomes};
    }

    /**
     * Método para transformar o cep inputado para apenas números:
     * @param cep   É o cep digitado pelo usuário
     * @return      O cep sem - apenas os números
     */
    public static String cep(String cep){
        return cep.replaceAll("[^\\d]", "");
    }
}