package com.example.servletfluxar.servlet.crud;

import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "HomeServlet", value = "/HomeServlet")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Declarando variáveis:
        HttpSession session = request.getSession();
        Empresa empresaLogada = (Empresa) session.getAttribute("empresa");
        Administrador administradorLogado = (Administrador) session.getAttribute("administrador");

        if (empresaLogada != null){
            request.setAttribute("tipoUsuario", "empresa");
            request.setAttribute("empresa", empresaLogada);
        } else if (administradorLogado != null){
            request.setAttribute("tipoUsuario", "administrador");
            request.setAttribute("administrador", administradorLogado);
        }

        request.getRequestDispatcher("WEB-INF/pages/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
