package com.example.servletfluxar.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import util.EmailService;
import java.io.IOException;

@WebServlet(name = "CadastroEnviarCodigoServlet", value = "/CadastroEnviarCodigoServlet")
public class CadastroEnviarCodigoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        String emailInput;
        RequestDispatcher dispatcher = null;
        String codigo;
        HttpSession session = request.getSession();

//        Recebendo o input do usuário:
        emailInput = request.getParameter("emailAdmin").trim();

//        Enviando email:
        session.setAttribute("emailAdmin", emailInput);
        codigo = String.valueOf((int) (Math.random() * 900000 + 100000));
        try {
            EmailService.enviarEmail(emailInput, "Seu código de verificação", "Código: " + codigo + "\nNão responda a esse email");
            session.setAttribute("codigoVerificacaoAdmin", codigo);
            response.sendRedirect(request.getContextPath() + "/cadastro/Admin/inputCodigo.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erroEmail", e);
            dispatcher = request.getRequestDispatcher("cadastro/Admin/escolherAdmin.jsp");
            dispatcher.forward(request, response);
        }
    }
}
