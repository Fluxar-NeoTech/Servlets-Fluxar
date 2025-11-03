package com.example.servletfluxar.util;
// ------------------------- Administrador -------------------------
public class AdminService {

    public static String cadastrar(String email, String senha) {
        String json = "{ \"email\": \"" + email + "\", \"senha\": \"" + senha + "\" }";
        String resposta = ConexaoAPILGPD.post(ConexaoAPILGPD.getBaseUrl() + "/admin/cadastro", json);
        resposta = resposta.replace("\"","");
        return resposta;
    }

    public static boolean login(String email, String senha) {
        String json = "{ \"email\": \"" + email + "\", \"senha\": \"" + senha + "\" }";
        String resposta = ConexaoAPILGPD.post(ConexaoAPILGPD.getBaseUrl() + "/admin/login", json);
        return ConexaoAPILGPD.toBoolean(resposta);
    }

    public static boolean alterarEmail(String emailVelho, String emailNovo) {
        String json = "{ \"emailVelho\": \"" + emailVelho + "\", \"emailNovo\": \"" + emailNovo + "\" }";
        String resposta = ConexaoAPILGPD.post(ConexaoAPILGPD.getBaseUrl() + "/admin/alterar-email", json);
        return ConexaoAPILGPD.toBoolean(resposta);
    }
}