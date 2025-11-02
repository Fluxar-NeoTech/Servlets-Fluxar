package com.example.servletfluxar.servlet.renovacao;

import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.util.EnvioEmail;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RenovarConfirmarDadosServlet", value = "/RenovarConfirmarDadosServlet")
public class RenovarConfirmarDadosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//      Declarando variáveis:
        int tempo = 0;
        int idPlano = 0;
        String codigo;
        HttpSession session = request.getSession();
        Empresa empresa = new Empresa();
        String plano;
        double preco = 0;

        try {
            empresa = (Empresa) session.getAttribute("empresa");
            idPlano = (Integer) session.getAttribute("idPlano");
            plano = (String) session.getAttribute("plano");
        } catch (NullPointerException npe){
            System.out.println(npe);
            request.setAttribute("erro", "Tempo expirado, tente cadastrar novamente...");
            request.getRequestDispatcher("/pages/cadastro/inicioCadastro.jsp")
                    .forward(request, response);
            return;
        }

        try{
            tempo = Integer.parseInt(request.getParameter("tempo"));
        } catch (NullPointerException | NumberFormatException e){
            System.out.println(e);
            request.setAttribute("erro", "Tempo expirado, tente logar para renovar novamente...");
            request.getRequestDispatcher("/index.jsp")
                    .forward(request, response);
            return;
        }

        if (tempo == 1){
            if (idPlano == 1){
                idPlano = 1;
                preco = 599.99;
            } else if (idPlano == 2){
                idPlano = 3;
                preco = 899.99;
            } else if (idPlano == 3){
                idPlano = 5;
                preco = 1599.99;
            }
        } else if (tempo == 12) {
            if (idPlano == 1){
                idPlano = 2;
                preco = 499.99;
            }else if (idPlano == 2){
                idPlano = 4;
                preco = 799.99;
            } else if (idPlano == 3){
                idPlano = 6;
                preco = 1499.99;
            }
        } else {
            request.setAttribute("erro", "Tempo deve ser mensal ou anual");
            request.getRequestDispatcher("/pages/renovacao/confirmarDados.jsp")
                    .forward(request, response);
            return;
        }

        session.setAttribute("idPlano", idPlano);
        session.setAttribute("preco", preco);

        request.setAttribute("preco", preco);
        request.setAttribute("plano", plano);

        codigo = String.valueOf((int) (Math.random() * 900000 + 100000));

        try {
//                Enviando um email com o código de verificação para ele
            EnvioEmail.enviarEmail(empresa.getEmail(), "Seu código de verificação", "<h2>Código:</h2><h3>"+codigo+"</h3><p>Não responda a esse email</p>");

            session.setAttribute("codigoVerificacao", codigo);

            response.sendRedirect(request.getContextPath() + "/pages/renovacao/codigo.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Não foi possível enviar um código de verificação");
            request.getRequestDispatcher("/pages/renovacao/confirmarDados.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
