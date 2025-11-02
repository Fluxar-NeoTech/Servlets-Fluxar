package com.example.servletfluxar.util;
// ------------------------- Administrador -------------------------
public class AdminService {

    public static String cadastrar(String email, String senha) {
        String json = "{ \"email\": \"" + email + "\", \"senha\": \"" + senha + "\" }";
        return ConexaoAPILGPD.post(ConexaoAPILGPD.getBaseUrl() + "/cadastro", json);
    }

    public static boolean login(String email, String senha) {
        String json = "{ \"email\": \"" + email + "\", \"senha\": \"" + senha + "\" }";
        String resposta = ConexaoAPILGPD.post(ConexaoAPILGPD.getBaseUrl() + "/login", json);
        return ConexaoAPILGPD.toBoolean(resposta);
    }

    public static boolean alterarEmail(String emailVelho, String emailNovo) {
        String json = "{ \"emailVelho\": \"" + emailVelho + "\", \"emailNovo\": \"" + emailNovo + "\" }";
        String resposta = ConexaoAPILGPD.post(ConexaoAPILGPD.getBaseUrl() + "/alterar-email", json);
        return ConexaoAPILGPD.toBoolean(resposta);
    }

    public static String alterarSenha(String email, String senhaNova) {
        String json = "{ \"email\": \"" + email + "\", \"senhaNova\": \"" + senhaNova + "\" }";
        return ConexaoAPILGPD.post(ConexaoAPILGPD.getBaseUrl() + "/alterar-senha", json);
    }
}