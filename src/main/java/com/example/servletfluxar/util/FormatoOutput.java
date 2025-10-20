package com.example.servletfluxar.util;

public class FormatoOutput {
    /**
     * Método que formata o preço para mostrar ao usuário
     * @param preco Preço no banco de dados
     * @return      preço formatado para R$00,00
     */
    public static String preco (double preco){
        return String.format("R$%.2f", preco);
    }

    /**
     * Método que formata o cnpj para saída
     * @param cnpj CNPJ a ser formatado
     * @return     CNPJ formatado em 00.000.000/0000-00
     */
    public static String cnpj (String cnpj){
//      Usa regex para inserir os caracteres no lugar correto
        return cnpj.replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})",
                "$1.$2.$3/$4-$5");
    }
}
