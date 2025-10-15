package com.example.servletfluxar.util;

import java.time.LocalDate;

/*
*   Classe feita para tratar os inputs para o formato requirido
*   pelas regras de negócio dentro do banco de dados.
 */
public class RegrasBanco {
    public static String cnpj (String cnpj) {
        return cnpj.replaceAll("[^\\d]", "");
    }

    public static String telefone (String telefone) {
        return telefone.replaceAll("[^\\d]", "");
    }

    public static String nomeEmpresa (String nome){
        char primeiraLetra = nome.charAt(0);
        char primeiraLetraMaiuscula = Character.toUpperCase(primeiraLetra);
        return nome.replace(primeiraLetra, primeiraLetraMaiuscula);
    }

    public static String[] separarNomeCompleto (String nomeCompleto){
        return nomeCompleto.split("^.+ ");
    }

    public static String cep(String input){
        return input.replaceAll("[^\\d]", "");
    }
}