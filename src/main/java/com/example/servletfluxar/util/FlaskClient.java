package com.example.servletfluxar.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

public class FlaskClient {

    private final String baseUrl;

    public FlaskClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    // Método genérico para enviar POST com JSON e retornar JSONObject
    private JSONObject postJsonObject(String endpoint, String jsonInput) throws IOException {
        URL url = new URL(baseUrl + endpoint);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int code = con.getResponseCode();
        InputStream is = (code >= 200 && code < 300) ? con.getInputStream() : con.getErrorStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line.trim());
        }

        return new JSONObject().put("response", response.toString());
    }

    // ------------------------- Métodos processados -------------------------

    // Retorna hash de senha como String
    public String empresaCadastro(String email, String senha) throws IOException {
        String json = String.format("{\"email\":\"%s\", \"senha\":\"%s\"}", email, senha);
        JSONObject obj = postJsonObject("/empresa/cadastro", json);
        return obj.getString("response");
    }

    // Retorna true ou false
    public boolean empresaLogin(String email, String senha) throws IOException {
        String json = String.format("{\"email\":\"%s\", \"senha\":\"%s\"}", email, senha);
        JSONObject obj = postJsonObject("/empresa/login", json);
        return Boolean.parseBoolean(obj.getString("response"));
    }

    public boolean empresaTelefone(String telefone) throws IOException {
        String json = String.format("{\"telefone\":\"%s\"}", telefone);
        JSONObject obj = postJsonObject("/empresa/telefone", json);
        return Boolean.parseBoolean(obj.getString("response"));
    }

    public boolean empresaAlterarEmail(String emailVelho, String emailNovo) throws IOException {
        String json = String.format("{\"emailVelho\":\"%s\", \"emailNovo\":\"%s\"}", emailVelho, emailNovo);
        JSONObject obj = postJsonObject("/empresa/alterar-email", json);
        return Boolean.parseBoolean(obj.getString("response"));
    }

    public String empresaAlterarSenha(String senhaVelha, String senhaNova) throws IOException {
        String json = String.format("{\"senhaVelha\":\"%s\", \"senhaNova\":\"%s\"}", senhaVelha, senhaNova);
        JSONObject obj = postJsonObject("/empresa/alterar-senha", json);
        return obj.getString("response");
    }

    public boolean registrarCnpj(String cnpj) throws IOException {
        String json = String.format("{\"cnpj\":\"%s\"}", cnpj);
        JSONObject obj = postJsonObject("/cnpj", json);
        return Boolean.parseBoolean(obj.getString("response"));
    }

    public boolean alterarCnpj(String cnpjVelho, String cnpjNovo) throws IOException {
        String json = String.format("{\"cnpjVelho\":\"%s\", \"cnpjNovo\":\"%s\"}", cnpjVelho, cnpjNovo);
        JSONObject obj = postJsonObject("/empresa/alterar-cnpj", json);
        return Boolean.parseBoolean(obj.getString("response"));
    }

    // ------------------------- Funcionário -------------------------

    public String funcionarioCadastro(String email, String senha) throws IOException {
        String json = String.format("{\"email\":\"%s\", \"senha\":\"%s\"}", email, senha);
        JSONObject obj = postJsonObject("/funcionario/cadastro", json);
        return obj.getString("response");
    }

    public boolean funcionarioLogin(String email, String senha) throws IOException {
        String json = String.format("{\"email\":\"%s\", \"senha\":\"%s\"}", email, senha);
        JSONObject obj = postJsonObject("/funcionario/login", json);
        return Boolean.parseBoolean(obj.getString("response"));
    }

    // ------------------------- Administrador -------------------------

    public String adminCadastro(String email, String senha) throws IOException {
        String json = String.format("{\"email\":\"%s\", \"senha\":\"%s\"}", email, senha);
        JSONObject obj = postJsonObject("/admin/cadastro", json);
        return obj.getString("response");
    }

    public boolean adminLogin(String email, String senha) throws IOException {
        String json = String.format("{\"email\":\"%s\", \"senha\":\"%s\"}", email, senha);
        JSONObject obj = postJsonObject("/admin/login", json);
        return Boolean.parseBoolean(obj.getString("response"));
    }
}