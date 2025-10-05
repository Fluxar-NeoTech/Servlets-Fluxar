package com.example.servletfluxar.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "CadastroConfirmarPlanoServlet", value = "/CadastroConfirmarPlanoServlet")
public class CadastroConfirmarPlanoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        String planoEscolhido;
        int plano=0;
        HttpSession session = request.getSession();

//        Pegando dado do plano da página web:
        planoEscolhido = request.getParameter("plano");

//        Verificando valor do plano e alterando para o plano correspondende no banco de dados:
        if("essentialMensal".equals(planoEscolhido)){
            plano=1;
        } else if ("essentialAnual".equals(planoEscolhido)) {
            plano=2;
        }else if ("profissionalMensal".equals(planoEscolhido)) {
            plano=3;
        }else if ("profissionalAnual".equals(planoEscolhido)) {
            plano=4;
        }else if ("enterpriseMensal".equals(planoEscolhido)) {
            plano=5;
        }else if ("enterpriseAnual".equals(planoEscolhido)) {
            plano=6;
        }

//        Salvando o dado do plano temporariamente:
        session.setAttribute("plano",plano);
        response.sendRedirect(request.getContextPath() + "/cadastro/confirmarDados/confirmacao.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}