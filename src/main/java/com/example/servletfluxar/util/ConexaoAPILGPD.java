package com.example.servletfluxar.util;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

// Classe genérica para requisições POST JSON
public class ConexaoAPILGPD {

    private static Dotenv dotenv = null;

    static {
        try {
            dotenv = Dotenv.configure()
                    .ignoreIfMissing() // se não existir .env, não dá erro
                    .load();
        } catch (Exception e) {
            System.err.println("Erro ao carregar o .env: " + e.getMessage());
            dotenv = null;
        }
    }

    /**
     * Busca variável de ambiente:
     * Primeiro do sistema (produção), depois do .env (desenvolvimento)
     */
    public static String get(String key) {
        String value = System.getenv(key);
        if (value == null && dotenv != null) {
            value = dotenv.get(key);
        }
        return value;
    }

//    Pega a URL base da API:
    public static String getBaseUrl() {
        return get("BASE_URL");
    }
    public static String post(String endpoint, String json) {
        try {
            URL url = new URL(endpoint);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("POST");
            conexao.setRequestProperty("Content-Type", "application/json; utf-8");
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setDoOutput(true);

            try (OutputStream os = conexao.getOutputStream()) {
                os.write(json.getBytes("utf-8"));
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    conexao.getResponseCode() < 400 ? conexao.getInputStream() : conexao.getErrorStream(),
                    "utf-8"));
            StringBuilder resposta = new StringBuilder();
            String linha;
            while ((linha = br.readLine()) != null) resposta.append(linha);

            return resposta.toString();

        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }

    // Converte resposta "true"/"false" em boolean
    public static boolean toBoolean(String resposta) {
        return resposta != null && resposta.equalsIgnoreCase("true");
    }
}