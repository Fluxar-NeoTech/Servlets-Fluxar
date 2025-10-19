package com.example.servletfluxar.servlet.login;

import com.example.servletfluxar.dao.AdministradorDAO;
import com.example.servletfluxar.dao.AssinaturaDAO;
import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.dao.FuncionarioDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Assinatura;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Funcionario;
import com.example.servletfluxar.util.ValidacaoInput;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declarando variáveis:
        String emailInput;
        String senhaInput;
        HttpSession session = request.getSession();
        EmpresaDAO empresaDAO = new EmpresaDAO();
        Empresa empresa;
        AssinaturaDAO assinaturaDAO = new AssinaturaDAO();
        Assinatura assinatura;
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        Funcionario funcionario;
        AdministradorDAO administradorDAO = new AdministradorDAO();
        Administrador administrador;

//        Coletando o input do usuário:
        emailInput = request.getParameter("emailUsuario").trim();
        senhaInput = request.getParameter("senhaUsuario").trim();

//        Verificando se o formato do email é válido:
        if (!ValidacaoInput.validarEmail(emailInput)){
            request.setAttribute("erroEmail", "Formato de email inválido");
            request.getRequestDispatcher("/fazerLogin/paginaLogin/login.jsp")
                    .forward(request, response);
            return;
        }

        if (senhaInput.length() < 8) {
            request.setAttribute("erroSenha", "Senha possui menos de 8 caracteres");
            request.getRequestDispatcher( "/fazerLogin/paginaLogin/login.jsp")
                    .forward(request, response);
            return;
        }

        empresa = empresaDAO.buscarPorEmail(emailInput);
        funcionario = funcionarioDAO.buscarPorEmail(emailInput);
        administrador = administradorDAO.buscarPorEmail(emailInput);

//        Verificando se email está cadastrado:
        if (funcionario != null || empresa != null || administrador != null) {

//            Verificando se está cadastrado como empresa
            if (empresa != null) {
//                Buscando
                assinatura = assinaturaDAO.buscarPorIdEmpresa(empresa.getId());
                if (assinatura.getStatus() == 'A') {
                    if (assinatura.getDtFim().isAfter(LocalDate.now())) {
                        if (empresaDAO.autenticar(emailInput, senhaInput) != null) {
                            session.setAttribute("tipoUsuario", "empresa");
//                            Definindo tempo de expiração para login da empresa para 1 hora:
                            session.setMaxInactiveInterval(60 * 60);
                            request.getRequestDispatcher("/WEB-INF/paginasIniciais/PIAdminEmpresa/PIAdminEmpresa.jsp")
                                    .forward(request, response);
                        } else {
                            request.setAttribute("erroSenha", "Senha incorreta");
                            request.getRequestDispatcher("/fazerLogin/paginaLogin/login.jsp")
                                    .forward(request, response);
                        }
                    } else {
                        assinatura.setStatus('1');
                        assinaturaDAO.alterar(assinatura);
                        request.setAttribute("erroEmail", "Empresa inativa");
                        request.getRequestDispatcher("/fazerLogin/paginaLogin/login.jsp")
                                .forward(request, response);
                    }
                } else {
                    request.setAttribute("erroEmail", "Empresa inativa");
                    request.getRequestDispatcher("/fazerLogin/paginaLogin/login.jsp")
                            .forward(request, response);
                }
            } else if (administrador != null) {
                if (administradorDAO.autenticar(emailInput, senhaInput) != null) {
                    session.setAttribute("tipoUsuario", "administrador");
//                   Definindo tempo de expiração para login de administrador para 1 hora:
                    session.setMaxInactiveInterval(60 * 60);
                    response.sendRedirect(request.getContextPath() + "/ListarPlanosServlet");
                } else {
                    request.setAttribute("erroSenha", "Senha incorreta");
                    request.getRequestDispatcher("/fazerLogin/paginaLogin/login.jsp")
                            .forward(request, response);
                }
            } else {
                request.setAttribute("erroEmail", "Acesso no mobile");
                request.getRequestDispatcher("/fazerLogin/paginaLogin/login.jsp")
                        .forward(request, response);
            }
        } else {
            request.setAttribute("erroEmail", "Email não cadastrado");
            request.getRequestDispatcher("/fazerLogin/paginaLogin/login.jsp")
                    .forward(request, response);
        }
    }
}