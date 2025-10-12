package com.example.servletfluxar.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

class ConexaoAPILGPD {
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
}

class EmpresaService {
    private static final String BASE_URL = "http://127.0.0.1:5000/empresa";

    public String cadastrar(String email, String senha) {
        String json = "{ \"email\": \"" + email + "\", \"senha\": \"" + senha + "\" }";
        return ConexaoAPILGPD.post(BASE_URL + "/cadastro", json);
    }

    public String login(String email, String senha) {
        String json = "{ \"email\": \"" + email + "\", \"senha\": \"" + senha + "\" }";
        return ConexaoAPILGPD.post(BASE_URL + "/login", json);
    }

    public String alterarCnpj(String cnpjVelho, String cnpjNovo) {
        String json = "{ \"cnpjVelho\": \"" + cnpjVelho + "\", \"cnpjNovo\": \"" + cnpjNovo + "\" }";
        return ConexaoAPILGPD.post(BASE_URL + "/alterar_cnpj", json);
    }
}

class FuncionarioService {
    private static final String BASE_URL = "http://127.0.0.1:5000/funcionario";

    public String cadastrar(String nome, String email, String senha) {
        String json = "{ \"nome\": \"" + nome + "\", \"email\": \"" + email + "\", \"senha\": \"" + senha + "\" }";
        return ConexaoAPILGPD.post(BASE_URL + "/cadastro", json);
    }

    public String login(String email, String senha) {
        String json = "{ \"email\": \"" + email + "\", \"senha\": \"" + senha + "\" }";
        return ConexaoAPILGPD.post(BASE_URL + "/login", json);
    }

    public String alterarCargo(String email, String cargoNovo) {
        String json = "{ \"email\": \"" + email + "\", \"cargoNovo\": \"" + cargoNovo + "\" }";
        return ConexaoAPILGPD.post(BASE_URL + "/alterar_cargo", json);
    }
}

class AdminService {
    private static final String BASE_URL = "http://127.0.0.1:5000/admin";

    public String cadastrar(String usuario, String senha) {
        String json = "{ \"usuario\": \"" + usuario + "\", \"senha\": \"" + senha + "\" }";
        return ConexaoAPILGPD.post(BASE_URL + "/cadastro", json);
    }

    public String login(String usuario, String senha) {
        String json = "{ \"usuario\": \"" + usuario + "\", \"senha\": \"" + senha + "\" }";
        return ConexaoAPILGPD.post(BASE_URL + "/login", json);
    }

    public String alterarPermissao(String usuario, String permissao) {
        String json = "{ \"usuario\": \"" + usuario + "\", \"permissao\": \"" + permissao + "\" }";
        return ConexaoAPILGPD.post(BASE_URL + "/alterar_permissao", json);
    }
}