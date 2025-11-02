package com.example.servletfluxar.servlet.cadastro.empresa;

import com.example.servletfluxar.util.ValidacaoInput;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.example.servletfluxar.util.EnvioEmail;
import java.io.IOException;

@WebServlet(name = "EnviarCodigoServlet", value = "/EnviarCodigoServlet")
public class EnviarCodigoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        String emailInput;
        String codigo;
        HttpSession session = request.getSession();

//        Recebendo o input do usuário:
        emailInput = request.getParameter("emailAdmin").trim();

//        Verificando se o email é válido:
        if (!ValidacaoInput.validarEmail(emailInput)){
            request.setAttribute("erroEmail", "Formato do email inválido");
            request.getRequestDispatcher("cadastro/Admin/escolherAdmin.jsp")
                    .forward(request, response);
        }

        session.setAttribute("emailAdmin", emailInput);
//        Gerando o código de verificação:
        codigo = String.valueOf((int) (Math.random() * 900000 + 100000));
        try {
//            Enviando um email com o código gerado:
            EnvioEmail.enviarEmail(emailInput, "Seu código de verificação", "Código: " + codigo + "\nNão responda a esse email");
            session.setAttribute("codigoVerificacaoAdmin", codigo);
            response.sendRedirect(request.getContextPath() + "/cadastro/Admin/inputCodigo.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erroEmail", e);
            request.getRequestDispatcher("cadastro/Admin/escolherAdmin.jsp")
                    .forward(request, response);
        }
    }
}