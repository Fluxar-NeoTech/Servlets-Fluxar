package com.example.servletfluxar.servlet.renovacao;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RenovacaoVerificarCodigoServlet", value = "/RenovacaoVerificarCodigoServlet")
public class RenovacaoVerificarCodigoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        // Declaração de variáveis:
        HttpSession session = null;
        String codigoGerado = null;
        String codigoDigitado = "";
        String digito = "";
        int i;

        try {
            // Obtém a sessão atual (sem criar nova)
            session = request.getSession(false);

            // Montando o código com os 6 campos (codigo1...codigo6)
            for (i = 1; i <= 6; i++) {
                digito = request.getParameter("codigo" + i);

                if (digito == null || digito.trim().isEmpty()) {
                    request.setAttribute("erroCodigo", "Preencha todos os campos do código.");
                    request.getRequestDispatcher("/pages/renovacao/codigo.jsp").forward(request, response);
                    return;
                }

                codigoDigitado += digito.trim();
            }

            //  Recupera código da sessão
            if (session != null) {
                codigoGerado = (String) session.getAttribute("codigoVerificacao");
            }

            // Verifica se o código digitado está correto
            if (codigoGerado != null && codigoGerado.equals(codigoDigitado)) {
                // Código correto → redireciona
                response.sendRedirect(request.getContextPath() + "/pages/renovacao/senha.jsp");
            } else {
                // Código incorreto
                request.setAttribute("erroCodigo", "Código incorreto. Tente novamente...");
                request.getRequestDispatcher("/pages/renovacao/codigo.jsp").forward(request, response);
            }

        } catch (Exception e) {
            //  Sessão expirada ou erro inesperado
            request.setAttribute("erro", "Tempo expirado, faça sua renovação novamente");
            request.getRequestDispatcher("/pages/renovacao/escolhaPlano.jsp").forward(request, response);
        }
    }
}
