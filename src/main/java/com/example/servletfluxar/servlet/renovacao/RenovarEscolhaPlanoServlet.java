package com.example.servletfluxar.servlet.renovacao;

import com.example.servletfluxar.model.Empresa;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RenovarEscolhaPlanoServlet", value = "/RenovarEscolhaPlanoServlet")
public class RenovarEscolhaPlanoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//      Declarando variáveis:
        String plano = request.getParameter("plano");
        int idPlano = 0;
        double precoMensal = 0;
        double precoAnual = 0;
        HttpSession session = request.getSession();
        Empresa empresa = new Empresa();

        try {
            empresa = (Empresa) session.getAttribute("empresa");
        } catch (NullPointerException npe){
            System.out.println(npe);
            request.setAttribute("erro", "Tempo expirado, tente logar para renovar novamente...");
            request.getRequestDispatcher("/index.jsp")
                    .forward(request, response);
            return;
        }

        if (plano == null){
            request.setAttribute("erro", "Um plano deve ser selecionado");
            request.getRequestDispatcher("/pages/cadastro/escolhaPlano.jsp")
                    .forward(request, response);
            return;
        } else {
            plano = plano.trim().toLowerCase();
            if (plano.equals("essential")){
                plano = "Essential";
                precoMensal = 599.99;
                precoAnual = 499.99;
                idPlano = 1;
            } else if (plano.equals("profissional")){
                plano = "Profissional";
                precoMensal = 899.99;
                precoAnual = 799.99;
                idPlano = 2;
            } else if (plano.equals("enterprise")){
                plano = "Enterprise";
                precoMensal = 1599.99;
                precoAnual = 1499.99;
                idPlano = 3;
            }
        }

        session.setAttribute("idPlano", idPlano);
        session.setAttribute("plano", plano);

        request.setAttribute("plano", plano);
        request.setAttribute("precoMensal", precoMensal);
        request.setAttribute("precoAnual", precoAnual);
        request.setAttribute("empresa", empresa);

        request.getRequestDispatcher("/pages/renovacao/confirmarDados.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
