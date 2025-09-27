package com.example.servletfluxar.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "AcaoAdminServlet", value = "/AcaoAdminServlet")
public class AcaoAdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declarando variáveis:
        String acao;
        HttpSession session = request.getSession();

//        Buscando dado do jsp:
        acao = request.getParameter("acao");

//        Salvando:
        session.setAttribute("acaoAdmin",acao);

//        Redirecionando o admin
        response.sendRedirect(request.getContextPath() + "/telasAdmin/escolhaTabela/escolhaTabela.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
