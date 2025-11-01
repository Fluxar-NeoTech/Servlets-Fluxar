package com.example.servletfluxar.servlet.esqueciSenha;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EsqueciSenhaEscreverCodigoServlet", value = "/EsqueciSenhaEscreverCodigoServlet")
public class EsqueciSenhaEscreverCodigoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

            // +Montando o código com os 6 campos (codigo1...codigo6)
            for (i = 1; i <= 6; i++) {
                digito = request.getParameter("codigo" + i);

                if (digito == null || digito.trim().isEmpty()) {
                    request.setAttribute("erroCodigo", "Preencha todos os campos do código.");
                    request.getRequestDispatcher("/pages/esqueciSenha/codigo.jsp").forward(request, response);
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
                response.sendRedirect(request.getContextPath() + "/pages/esqueciSenha/novaSenha.jsp");
            } else {
                // Código incorreto
                request.setAttribute("erroCodigo", "Código incorreto. Tente novamente...");
                request.getRequestDispatcher("/pages/esqueciSenha/codigo.jsp").forward(request, response);
            }

        } catch (Exception e) {
            //  Sessão expirada ou erro inesperado
            request.setAttribute("erroCodigo", "Tempo expirado, volte à página anterior.");
            request.getRequestDispatcher("/pages/esqueciSenha/codigo.jsp").forward(request, response);
        }
    }
}