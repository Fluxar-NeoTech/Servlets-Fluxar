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
    public static String nomeCapitalize(String nome) {
        if (nome == null || nome.isEmpty()) {
            return nome;
        }
        return nome.substring(0, 1).toUpperCase() + nome.substring(1);
    }

    /**
     * Método para separar o nome completo para uma nome + sobrenomes
     * @param nomeCompleto É o nome inteiro inputado pelo usuário
     * @return             Uma lista com os elementos sendo [nome, sobrenomes]
     */
    public static String[] separarNomeCompleto(String nomeCompleto) {
        // Declaração de variáveis:
        int primeiroEspaco;
        String nome;
        String sobrenomes;
        char[] chars;
        boolean maiuscula;
        int i;

        // Remove espaços extras no início e fim
        nomeCompleto = nomeCompleto.trim();

        // Encontra o primeiro espaço
        primeiroEspaco = nomeCompleto.indexOf(" ");

        // Caso só exista um nome
        if (primeiroEspaco == -1) {
            nome = nomeCapitalize(nomeCompleto);
            sobrenomes = "";
            return new String[]{nome, sobrenomes};
        }

        // Primeiro nome capitalizado
        nome = nomeCapitalize(nomeCompleto.substring(0, primeiroEspaco));

        // Resto dos sobrenomes em minúsculo
        sobrenomes = nomeCompleto.substring(primeiroEspaco + 1).toLowerCase();

        // Transforma em array de caracteres
        chars = sobrenomes.toCharArray();
        maiuscula = true;

        // Percorre e capitaliza após cada espaço
        for (i = 0; i < chars.length; i++) {
            if (maiuscula && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                maiuscula = false;
            } else if (chars[i] == ' ') {
                maiuscula = true;
            }
        }

        // Converte de volta para String
        sobrenomes = new String(chars);

        // Retorna nome e sobrenomes
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