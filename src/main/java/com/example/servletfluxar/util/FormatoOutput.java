package com.example.servletfluxar.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    /**
     * Método que transforma o valor do tempo para uma string de duração
     * @param tempo Tempo registrado no banco de dados
     * @return      retorna Anual se o tempo for 12 meses e Mensal se for 1 mês
     */
    public static String duracao (int tempo){
        return tempo==1 ? "Mensal" : "Anual";
    }

    /**
     * Método que une o valor do nome ao sobrenome:
     * @param nome Nome do usuário
     * @param sobrenome Sobrenome do usuário
     * @return         nome + espaço + sobrenome
     */
    public static String nome(String nome, String sobrenome) {
        if (nome == null) nome = "";
        if (sobrenome == null) sobrenome = "";

        String nomeCompleto = (nome + " " + sobrenome).trim();
        return nomeCompleto.isEmpty() ? "—" : nomeCompleto;
    }

    /**
     * Método para formatar a data para mostrar ao usuário
     * @param data É a data que vem dos DAOs
     * @return     A data no formato ano / mês / dia
     */
    public static String data(LocalDate data){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return formatter.format(data);
    }

    /**
     * Método para formatar o telefone para output e visualização do usuário
     * @param telefone É o telefone que veio do banco de dados, apenas com números
     * @return         telefone com (__) _____-____ ou (__) ____-____
     */
    public static String telefone(String telefone){
        if (telefone.length() == 11) {
            // Celular com 9 dígitos: 11987654321 → (11) 98765-4321
            return String.format("(%s) %s-%s",
                    telefone.substring(0, 2),
                    telefone.substring(2, 7),
                    telefone.substring(7));
        } else if (telefone.length() == 10) {
            // Telefone fixo: 1134567890 → (11) 3456-7890
            return String.format("(%s) %s-%s",
                    telefone.substring(0, 2),
                    telefone.substring(2, 6),
                    telefone.substring(6));
        } else {
            return null;
        }
    }

    /**
     * Método para formatar cep que está salvo no banco para output:
     * @param cep É o cep salvo no banco de dados
     * @return    Retorna o cep no formato _____-___
     */
    public static String cep(String cep){
        return cep.replaceAll("(\\d{5})(\\d{3})", "$1-$2");
    }

    /**
     * Método estático para formatar status para a saída para o usuário
     * @param status É o status A ou I salvo no banco de dados
     * @return      Retorna Ativo ou Inativo a depender
     */
    public static String status(char status){
        return status == 'A' ? "Ativo": "Inativo";
    }
}