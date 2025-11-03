package com.example.servletfluxar.util;

// ------------------------- Empresa -------------------------
public class EmpresaService {

    public static String cadastrar(String email, String senha) {
        String json = "{ \"email\": \"" + email + "\", \"senha\": \"" + senha + "\" }";
        String resposta = ConexaoAPILGPD.post(ConexaoAPILGPD.getBaseUrl() + "/empresa/cadastro", json);
        resposta = resposta.replace("\"","");
        return resposta;
    }

    public static boolean login(String email, String senha) {
        String json = "{ \"email\": \"" + email + "\", \"senha\": \"" + senha + "\" }";
        String resposta = ConexaoAPILGPD.post(ConexaoAPILGPD.getBaseUrl() + "/empresa/login", json);
        return ConexaoAPILGPD.toBoolean(resposta);
    }

    public static boolean registrarTelefone(String telefone) {
        String json = "{ \"telefone\": \"" + telefone + "\" }";
        String resposta = ConexaoAPILGPD.post(ConexaoAPILGPD.getBaseUrl() + "/empresa/telefone", json);
        return ConexaoAPILGPD.toBoolean(resposta);
    }

    public static boolean alterarCnpj(String cnpjVelho, String cnpjNovo) {
        String json = "{ \"cnpjVelho\": \"" + cnpjVelho + "\", \"cnpjNovo\": \"" + cnpjNovo + "\" }";
        String resposta = ConexaoAPILGPD.post(ConexaoAPILGPD.getBaseUrl() + "/empresa/alterar-cnpj", json);
        return ConexaoAPILGPD.toBoolean(resposta);
    }

    public static boolean alterarEmail(String emailVelho, String emailNovo) {
        String json = "{ \"emailVelho\": \"" + emailVelho + "\", \"emailNovo\": \"" + emailNovo + "\" }";
        String resposta = ConexaoAPILGPD.post(ConexaoAPILGPD.getBaseUrl()+ "/empresa/alterar-email", json);
        return ConexaoAPILGPD.toBoolean(resposta);
    }
}