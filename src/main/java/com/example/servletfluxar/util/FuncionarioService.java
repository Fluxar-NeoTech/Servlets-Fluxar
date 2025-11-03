package com.example.servletfluxar.util;

// ------------------------- Funcionário -------------------------
public class FuncionarioService {
    public static String cadastrar(String email, String senha) {
        String json = "{ \"email\": \"" + email + "\", \"senha\": \"" + senha + "\" }";
        String resposta = ConexaoAPILGPD.post(ConexaoAPILGPD.getBaseUrl() + "/funcionario/cadastro", json);
        resposta = resposta.replace("\"","");
        return resposta;
    }

    public static boolean login(String email, String senha) {
        String json = "{ \"email\": \"" + email + "\", \"senha\": \"" + senha + "\" }";
        String resposta = ConexaoAPILGPD.post(ConexaoAPILGPD.getBaseUrl() + "/funcionario/login", json);
        return ConexaoAPILGPD.toBoolean(resposta);
    }

    public static boolean alterarEmail(String emailVelho, String emailNovo) {
        String json = "{ \"emailVelho\": \"" + emailVelho + "\", \"emailNovo\": \"" + emailNovo + "\" }";
        String resposta = ConexaoAPILGPD.post(ConexaoAPILGPD.getBaseUrl() + "/funcionario/alterar-email", json);
        return ConexaoAPILGPD.toBoolean(resposta);
    }
}