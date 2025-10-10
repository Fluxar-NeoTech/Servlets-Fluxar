package com.example.servletfluxar.servlet.cadastro.empresa;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "EscreverCodigoServlet", value = "/EscreverCodigoServlet")
public class EscreverCodigoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        RequestDispatcher dispatcher = null;
        String codigoInput;
        HttpSession session = request.getSession();
        String codigoGerado;

//        Recebendo input do usuário:
        codigoInput = request.getParameter("codigo").trim();

//        Recebendo código salvo:
        codigoGerado = (String) session.getAttribute("codigoVerificacaoAdmin");

        if (session != null) {
            if (codigoGerado != null && codigoGerado.equals(codigoInput)) {
                // Código correto
                response.sendRedirect(request.getContextPath() +"/cadastro/Admin/escolhaSenha.jsp");
            } else {
                // Código incorreto
                request.setAttribute("erroCodigo", "Código inválido");
                dispatcher = request.getRequestDispatcher("/cadastro/Admin/inputCodigo.jsp");
                dispatcher.forward(request,response);
            }
        } else {
            // Sessão expirada ou inexistente
            request.setAttribute("erroCodigo", "Tempo de uso expirado, volte à página anterior");
            dispatcher = request.getRequestDispatcher("/cadastro/Admin/inputCodigo.jsp");
            dispatcher.forward(request,response);
        }
    }
}