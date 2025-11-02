package com.example.servletfluxar.servlet.cadastro.empresa;

import com.example.servletfluxar.util.ValidacaoInput;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;

@WebServlet(name = "CriarAdminEmpresaServlet", value = "/CriarAdminEmpresaServlet")
public class CriarAdminEmpresaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declarando variáveis:
        HttpSession session = request.getSession();
        String senhaInput;
        String senhaConf;
        int senhaValida;

//        Pegando input do usuário:
        senhaInput = request.getParameter("senha");
        senhaConf = request.getParameter("senhaConfirmada");

        senhaValida = ValidacaoInput.validarSenha(senhaInput);

//        Verificando se a senha possui os requisitos mínimos
        if (senhaValida > 0) {
            if (senhaValida == 1) {
                request.setAttribute("erroSenha", "Senha deve ter menos que 28 caracteres");
            } else if (senhaValida == 2){
                request.setAttribute("erroSenha", "Senha deve ser maior que 8 caracteres");
            } else if (senhaValida == 3){
                request.setAttribute("erroSenha", "Senha deve conter letras maiúsculas");
            } else if(senhaValida == 4) {
                request.setAttribute("erroSenha", "Senha deve conter letras minúsculas");
            } else if (senhaValida == 5) {
                request.setAttribute("erro senha", "Senha deve conter números");
            }

            request.getRequestDispatcher("/cadastro/Admin/escolhaSenha.jsp")
                    .forward(request, response);
        }

        if (!senhaInput.equals(senhaConf)) {
            request.setAttribute("erroConfSenha", "Senha incorreta");
            request.getRequestDispatcher("/cadastro/Admin/escolhaSenha.jsp")
                    .forward(request, response);
            return;
        }

//        Salvando senha:
        session.setAttribute("senhaAdmin",senhaInput);

//        Redirecionando usuário:
        response.sendRedirect(request.getContextPath() +"/cadastro/pagamento/formaPagamento/pagamento.html");
    }
}