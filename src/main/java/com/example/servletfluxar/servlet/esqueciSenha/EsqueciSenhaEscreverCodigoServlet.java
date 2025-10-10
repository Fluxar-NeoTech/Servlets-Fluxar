package com.example.servletfluxar.servlet.esqueciSenha;

import com.example.servletfluxar.dao.AdministradorDAO;
import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Funcionario;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet(name = "EsqueciSenhaEscreverCodigoServlet", value = "/EsqueciSenhaEscreverCodigoServlet")
public class EsqueciSenhaEscreverCodigoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        String codigoInput;
        String codigo;
        HttpSession session = request.getSession();
        String codigoGerado;

//        Recebendo input do usuário:
        codigoInput = request.getParameter("codigo").trim();

//        Recebendo código salvo:
        codigoGerado = (String) session.getAttribute("codigoVerificacao");

        if (session != null) {
            if (codigoGerado != null && codigoGerado.equals(codigoInput)) {
                // Código correto
                response.sendRedirect(request.getContextPath() +"/fazerLogin/esqueciSenha/novaSenha/novaSenha.jsp");
            } else {
                // Código incorreto
                request.setAttribute("erroCodigo", "Código inválido");
                request.getRequestDispatcher("/fazerLogin/esqueciSenha/codigo/codigo.jsp")
                        .forward(request,response);
            }
        } else {
            // Sessão expirada ou inexistente
            request.setAttribute("erroCodigo", "Tempo de uso expirado, volte à página anterior");
            request.getRequestDispatcher("/fazerLogin/esqueciSenha/codigo/codigo.jsp")
                    .forward(request,response);
        }
    }
}