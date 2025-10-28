package com.example.servletfluxar.servlet.crud.alterar;

import com.example.servletfluxar.dao.AdministradorDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.util.RegrasBanco;
import com.example.servletfluxar.util.ValidacaoInput;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet(name = "AlterarAdminServlet", value = "/AlterarAdminServlet")
public class AlterarAdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        int id = 0;
        Administrador administrador;
        AdministradorDAO administradorDAO = new AdministradorDAO();

//        Setando atributo da request com o tipo do usuário
        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")) {
                request.setAttribute("administrador", (Administrador) session.getAttribute("administrador"));
            } else {
                response.sendRedirect(request.getContextPath());
                return;
            }
//            Tratando exceção para caso não seja encontrado os dados na session:
        } catch (NullPointerException npe) {
            request.setAttribute("erroLogin", "Ops, é necessário fazer login novamente...");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }

        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException nfe) {
            request.setAttribute("erro", "Id deve ser um número");
            request.getRequestDispatcher("")
                    .forward(request, response);
            return;
        } catch (NullPointerException npe) {
            request.setAttribute("erro", npe.getMessage());
            request.setAttribute("mensagem", "Ocorreu um erro ao procurar esse admin");
            request.getRequestDispatcher("")
                    .forward(request, response);
            return;
        }

        administrador = administradorDAO.buscarPorId(id);
        request.setAttribute("administrador", administrador);

//        Redireciona para a página de adicionar administrador:
        request.getRequestDispatcher("/WEB-INF/pages/administradores/alterarAdministrador.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        String nomeInput = request.getParameter("nomeCompleto");
        String[] nomeCompleto = new String[2];
        String email = request.getParameter("email");
        int senhaValida;
        HttpSession session = request.getSession();
        Administrador administrador = new Administrador();
        AdministradorDAO administradorDAO = new AdministradorDAO();
        boolean continuar = true;

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")) {
                request.setAttribute("administrador", (Administrador) session.getAttribute("administrador"));
            } else {
                response.sendRedirect(request.getContextPath());
                return;
            }
        } catch (NullPointerException npe) {
            request.setAttribute("erroLogin", "É necessário fazer login novamente");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }

//        Verificações do input:
        if (nomeCompleto == null) {
            request.setAttribute("erroNome", "Insira um nome para o administrador");
            continuar = false;
        } else {
            nomeCompleto = RegrasBanco.separarNomeCompleto(nomeInput);
            administrador.setNome(nomeCompleto[0]);
            administrador.setSobrenome(nomeCompleto[1]);
        }

        if (email == null) {
            request.setAttribute("erroEmail", "Insira um email para o administrador");
            continuar = false;
        } else {
            if (!ValidacaoInput.validarEmail(email)) {
                request.setAttribute("erroEmail", "Formato de email inválido");
                continuar = false;
            } else {
                administrador.setEmail(email);
            }
        }


        if (!continuar) {
            request.getRequestDispatcher("/WEB-INF/pages/administradores/alterarAdministrador.jsp")
                    .forward(request, response);
            return;
        }

//        Enviando e vendo se há um retorno:
        if (administradorDAO.alterar(administrador)) {
            response.sendRedirect(request.getContextPath() + "/ListarAdminsServlet");
        } else {
            request.setAttribute("mensagem", "Não foi possível inserir um plano no momento. Tente novamente mais tarde...");
            request.getRequestDispatcher("")
                    .forward(request, response);
        }
    }
}