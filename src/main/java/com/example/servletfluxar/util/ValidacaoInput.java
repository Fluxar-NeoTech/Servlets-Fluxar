package com.example.servletfluxar.util;

public class ValidacaoInput {

/*
*     Método estático para validar se o input
*     corresponde ao formato do CNPJ,
*/
    public static boolean validarCNPJ(String input){
//        Verifica se o formato do input equivale ao REGEX para CNPJ:
        return input.matches("^\\d{2}\\.?\\d{3}\\.?\\d{3}\\/\\d{4}-?\\d{2}$");
    }

/*
*    Método estático para verificar se o formato do
*    telefone está correto
 */
    public static boolean validarTelefone(String input){
//        Verifica se o formato do input tem o formato do telefone:
        return input.matches("^\\(?\\d{2}\\)? ?\\d{4,5}[ -]?\\d{4}$");
    }

/*
*   Método estático para validar se o input corresponde
*   ao formato necessário para o email, o formato esperado é:
*   - Qualquer caracter que não seja @ ou espaço em branco
*   - Seguido por um @ com novamente qualquer caracter sem @ ou espaço em branco
*   - Por fim, um . seguido por qualuer caracter exceto @ ou espaço em branco
 */
    public static boolean validarEmail(String input){
//        Verificando e retornando se o input corresponde ao REGEX do formato para email:
        return input.matches("^[^@\\s]+@[^@\\s]+\\.[A-Za-z]+$");
    }

/*
*   Método estático para verificar se o input
*   corresponde aos requisitos do nosso projeto para
*   uma senha ser definida, ou seja, deve conter:
*   - Letras maiúsculas e minúsculas;
*   - Números;
*   - Mínimo 8 caracteres;
*   - Máximo 28 caracteres.
 */
    public static int validarSenha(String input){
        if (input.length()>28){
            return 1;
        }
        if (input.length()<8){
            return 2;
        }
        if (!input.matches(".*[A-Z].*")){
            return 3;
        }
        if (!input.matches(".*[a-z].*")){
            return 4;
        }
        if (!input.matches(".*\\d.*")){
            return 5;
        }
        return 0;
    }

    /**
     * Método para verificar se valor do plano é válido:
     * @param input é o valor do plano colocado no input
     * @return     true se o valor for maior que 0
     */
    public static boolean validarPreco(double input){
        return input>0;
    }
}