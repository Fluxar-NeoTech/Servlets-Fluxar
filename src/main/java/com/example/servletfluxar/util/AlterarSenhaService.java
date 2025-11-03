package com.example.servletfluxar.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class AlterarSenhaService {

    private static final String BASE_URL = ConexaoAPILGPD.getBaseUrl();

    // Envia requisição POST com JSON e retorna o corpo da resposta
    private static String enviarRequisicao(String endpoint, String json) {
        HttpURLConnection conexao = null;
        try {
            URL url = new URL(BASE_URL + endpoint);
            conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("POST");
            conexao.setRequestProperty("Content-Type", "application/json; utf-8");
            conexao.setDoOutput(true);

            // Envia o JSON
            try (OutputStream os = conexao.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }

            // Seleciona stream correto (sucesso ou erro)
            InputStream is = (conexao.getResponseCode() >= 200 && conexao.getResponseCode() < 400)
                    ? conexao.getInputStream()
                    : conexao.getErrorStream();

            // Lê e retorna o JSON puro da resposta
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                return br.lines().collect(Collectors.joining("\n"));
            }

        } catch (Exception e) {
            // Retorna apenas a mensagem de erro, sem aspas extras
            return "{erro: " + e.getMessage() + "}";
        } finally {
            if (conexao != null) {
                conexao.disconnect();
            }
        }
    }

    public static String alterarSenhaEmpresa(String email, String novaSenha) {
        String json = String.format("{email: %s, senhaNova: %s}", email, novaSenha);
        return enviarRequisicao("/empresa/alterar-senha", json);
    }

    public static String alterarSenhaFuncionario(String email, String novaSenha) {
        String json = String.format("{email: %s, senhaNova: %s}", email, novaSenha);
        return enviarRequisicao("/funcionario/alterar-senha", json);
    }

    public static String alterarSenhaAdmin(String email, String novaSenha) {
        String json = String.format("{email: %s, senhaNova: %s}", email, novaSenha);
        return enviarRequisicao("/admin/alterar-senha", json);
    }
}