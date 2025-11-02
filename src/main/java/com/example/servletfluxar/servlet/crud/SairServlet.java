package com.example.servletfluxar.servlet.crud;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "SairServlet", value = "/SairServlet")
public class SairServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Declaração de variáveis:
        HttpSession session = request.getSession(false);

//        Verificando se a sessão existe:
        if (session != null) {
            // Encerrando a sessão atual:
            session.invalidate();
        }

//        Redirecionando para a home, ou seja index.html:
        response.sendRedirect(request.getContextPath()+"/index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
