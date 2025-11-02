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
        doPost(request, response);
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
        AdministradorDAO administradorDAO = new AdministradorDAO();
        Administrador administrador;
        boolean continuar = true;

//        Coletando o input do usuário:
        emailInput = request.getParameter("emailUsuario");
        senhaInput = request.getParameter("senhaUsuario");

//        Verificando se o formato do email é válido:
        if (emailInput == null) {
            request.setAttribute("erroEmail", "Email deve ser digitado");
            continuar = false;
        } else {
            emailInput = emailInput.trim().toLowerCase();
            if (!ValidacaoInput.validarEmail(emailInput)) {
                request.setAttribute("erroEmail", "Formato de email inválido");
                continuar = false;
            }
        }

        if (senhaInput == null){
            request.setAttribute("erroSenha", "Senha deve ser digitada");
            continuar = false;
        } else {
            senhaInput = senhaInput.trim();
            if (senhaInput.length() < 8) {
                request.setAttribute("erroSenha", "Senha deve maior que 8 caracteres");
                continuar = false;
            }
        }

        if (!continuar){
            request.getRequestDispatcher("/index.jsp")
                    .forward(request, response);
            return;
        }

        empresa = empresaDAO.buscarPorEmail(emailInput);
        administrador = administradorDAO.buscarPorEmail(emailInput);

//        Verificando se email está cadastrado:
        if (empresa != null || administrador != null) {

//            Verificando se está cadastrado como empresa
            if (empresa != null) {
//                Buscando
                assinatura = assinaturaDAO.buscarPorIdEmpresa(empresa.getId());
                if (assinatura.getStatus() == 'A') {
                    if (assinatura.getDtFim().isAfter(LocalDate.now())) {
                        if (empresaDAO.autenticar(emailInput, senhaInput) != null) {
                            session.setAttribute("empresa", empresa);
                            session.setAttribute("tipoUsuario", "empresa");
//                            Definindo tempo de expiração para login da empresa para 1 hora:
                            session.setMaxInactiveInterval(60 * 60);
                            response.sendRedirect(request.getContextPath() + "/HomeServlet");
                        } else {
                            request.setAttribute("erroSenha", "Senha incorreta");
                            request.getRequestDispatcher("/index.jsp")
                                    .forward(request, response);
                        }
                    } else {
                        assinatura.setStatus('I');
                        assinaturaDAO.alterar(assinatura);
                        response.sendRedirect(request.getContextPath()+"/pages/renovacao");
                    }
                } else {
                    request.setAttribute("erroEmail", "Empresa inativa");
                    request.getRequestDispatcher("/index.jsp")
                            .forward(request, response);
                }
            } else if (administrador != null) {
                if (administradorDAO.autenticar(emailInput, senhaInput) != null) {
                    session.setAttribute("administrador", administrador);
                    session.setAttribute("tipoUsuario", "administrador");
//                   Definindo tempo de expiração para login de administrador para 1 hora:
                    session.setMaxInactiveInterval(60 * 60);
                    response.sendRedirect(request.getContextPath() + "/HomeServlet");
                } else {
                    request.setAttribute("erroSenha", "Senha incorreta");
                    request.getRequestDispatcher("/index.jsp")
                            .forward(request, response);
                }
            }
        } else {
            request.setAttribute("erroEmail", "Email não cadastrado");
            request.getRequestDispatcher("/index.jsp")
                    .forward(request, response);
        }
    }
}