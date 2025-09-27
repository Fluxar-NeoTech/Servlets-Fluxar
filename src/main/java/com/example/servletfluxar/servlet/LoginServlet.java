package com.example.servletfluxar.servlet;

import com.example.servletfluxar.dao.AdministradorDAO;
import com.example.servletfluxar.dao.AssinaturaDAO;
import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.dao.FuncionarioDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Funcionario;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    private HttpServletResponse response;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declarando variáveis:
        String emailInput;
        String senhaInput;
        String tipo;
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher = null;
        Empresa empresa;
        Funcionario funcionario;
        Administrador administrador;

//        Coletando o input do usuário:
        emailInput = request.getParameter("emailUsuario").trim();
        senhaInput = request.getParameter("senhaUsuario").trim();

        if (senhaInput.length() < 8) {
            request.setAttribute("erroSenha", "Senha possui menos de 8 caracteres");
            dispatcher = request.getRequestDispatcher( "/fazerLogin/paginaLogin/login.jsp");
            dispatcher.forward(request, response);
            return;
        }

        empresa = EmpresaDAO.buscarPorEmail(emailInput);
        funcionario = FuncionarioDAO.buscarPorEmail(emailInput);
        administrador = AdministradorDAO.buscarPorEmail(emailInput);

//        Verificando se email está cadastrado:
        if (funcionario != null || empresa != null || administrador != null) {
            if (empresa != null) {
                if (AssinaturaDAO.buscarPorIdEmpresa(empresa.getId()).stream().filter(assinatura -> assinatura.getStatus() == 'A') != null) {
                    if (BCrypt.checkpw(senhaInput, empresa.getSenha())) {
                        response.sendRedirect(request.getContextPath() + "/paginasIniciais/PIAdminEmpresa/PIAdminEmpresa.jsp");
                    } else {
                        request.setAttribute("erroSenha", "Senha incorreta");
                        dispatcher = request.getRequestDispatcher("/fazerLogin/paginaLogin/login.jsp");
                        dispatcher.forward(request, response);
                    }
                } else {
                    request.setAttribute("erroEmail", "Email inválido");
                    dispatcher = request.getRequestDispatcher("/fazerLogin/paginaLogin/login.jsp");
                    dispatcher.forward(request, response);
                }
            } else if (administrador != null) {
                if (BCrypt.checkpw(senhaInput, administrador.getSenha())) {
                    response.sendRedirect(request.getContextPath() + "/paginasIniciais/PIAdmin/PIadmin.jsp");
                } else {
                    request.setAttribute("erroSenha", "Senha incorreta");
                    dispatcher = request.getRequestDispatcher("/fazerLogin/paginaLogin/login.jsp");
                    dispatcher.forward(request, response);
                }
            } else {
                request.setAttribute("erroEmail", "Acesso no mobile");
                dispatcher = request.getRequestDispatcher("/fazerLogin/paginaLogin/login.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            request.setAttribute("erroEmail", "Email não cadastrado");
            dispatcher = request.getRequestDispatcher("/fazerLogin/paginaLogin/login.jsp");
            dispatcher.forward(request, response);
        }
    }
}