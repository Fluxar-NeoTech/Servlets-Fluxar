package com.example.servletfluxar.servlet.crud.adicionar;

import com.example.servletfluxar.dao.AdministradorDAO;
import com.example.servletfluxar.dao.PlanoDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Plano;
import com.example.servletfluxar.util.RegrasBanco;
import com.example.servletfluxar.util.ValidacaoInput;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet(name = "AdicionarAdminServlet", value = "/AdicionarAdminServlet")
public class AdicionarAdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        HttpSession session = request.getSession();

//        Setando atributo da request com o tipo do usuário
        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                request.setAttribute("administrador", (Administrador) session.getAttribute("administrador"));
            } else {
                response.sendRedirect(request.getContextPath());
                return;
            }

//            Tratando exceção para caso não seja encontrado os dados na session:
        } catch (NullPointerException npe){
            request.setAttribute("erroLogin", "Ops, é necessário fazer login novamente...");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }

//        Redireciona para a página de adicionar administrador:
        request.getRequestDispatcher("/WEB-INF/pages/administradores/adicionarAdministrador.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        String[] nomeCompleto = new String[2];
        String nomeInput = request.getParameter("nomeCompleto");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String confirmarSenha = request.getParameter("confirmarSenha");
        int senhaValida;
        HttpSession session = request.getSession();
        Administrador administrador = new Administrador();
        AdministradorDAO administradorDAO = new AdministradorDAO();
        boolean continuar = true;

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                request.setAttribute("administrador", (Administrador) session.getAttribute("administrador"));
            } else {
                response.sendRedirect(request.getContextPath());
                return;
            }
        } catch (NullPointerException npe){
            request.setAttribute("erroLogin", "É necessário fazer login novamente");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }

//        Verificações do input:
        if (nomeCompleto == null){
            request.setAttribute("erroNome", "Insira um nome para o administrador");
            continuar = false;
        } else {
            nomeInput = nomeInput.trim();
            nomeCompleto = RegrasBanco.separarNomeCompleto(nomeInput.trim());
            administrador.setNome(nomeCompleto[0]);
            administrador.setSobrenome(nomeCompleto[1]);
        }

        if (email == null){
            request.setAttribute("erroEmail", "Insira um email para o administrador");
            continuar = false;
        } else {
            email = email.trim().toLowerCase();
            if (!ValidacaoInput.validarEmail(email)) {
                request.setAttribute("erroEmail", "Formato de email inválido");
                continuar = false;
            }
        }

        if (senha == null){
            request.setAttribute("erroSenha", "Defina uma senha para o administrador");
            continuar = false;
        } else {
            senha = senha.trim();
            senhaValida = ValidacaoInput.validarSenha(senha);
            if (senhaValida != 0){
                if (senhaValida == 1){
                    request.setAttribute("erroSenha", "Senha deve ser menor do que 28 caracteres");
                }
                if (senhaValida == 2){
                    request.setAttribute("erroSenha", "Senha deve ser maior do que 8 caracteres");
                }
                if (senhaValida == 3){
                    request.setAttribute("erroSenha", "Senha deve ter letras maiúsculas");
                }
                if (senhaValida == 4){
                    request.setAttribute("erroSenha", "Senha deve ter letras minúsculas");
                }
                if (senhaValida == 5){
                    request.setAttribute("erroSenha", "Senha deve ter números");
                }
                continuar = false;
            } else {
                if (!senha.equals(confirmarSenha.trim())){
                    request.setAttribute("erroConfirmarSenha", "Senha confirmada incorreta");
                    continuar = false;
                }
            }
        }

        if (!continuar){
            request.getRequestDispatcher("/WEB-INF/pages/administradores/adicionarAdministrador.jsp")
                    .forward(request, response);
            return;
        }

        administrador.setEmail(email);
        administrador.setSenha(senha);

//        Enviando e vendo se há um retorno:
        if (administradorDAO.inserir(administrador)){
            response.sendRedirect(request.getContextPath() + "/ListarAdminsServlet");
        }else {
            request.setAttribute("mensagem", "Não foi possível inserir um plano no momento. Tente novamente mais tarde...");
            request.getRequestDispatcher("")
                    .forward(request, response);
        }
    }
}