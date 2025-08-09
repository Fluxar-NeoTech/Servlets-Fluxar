package servlet;

import dao.AdministradorDAO;
import dao.EmpresaDAO;
import dao.FuncionarioDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Administrador;
import model.Empresa;
import model.Funcionario;
import org.mindrot.jbcrypt.BCrypt;

import javax.lang.model.util.ElementScanner7;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

//        Verificando se email está cadastrado:
        if (FuncionarioDAO.verificarCampo("email", emailInput) || EmpresaDAO.verificarCampo("email", emailInput) || AdministradorDAO.verificarCampo("email", emailInput)) {
            if (EmpresaDAO.verificarCampo("email", emailInput)) {
                empresa = EmpresaDAO.buscarEmpresa("email",emailInput);
                if ("A".equals(empresa.getStatus())) {
                    if (EmpresaDAO.autenticar(emailInput, senhaInput)) {
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
            } else if (AdministradorDAO.verificarCampo("email", emailInput)) {
                administrador = AdministradorDAO.buscarAdministrador("email", emailInput);
                if (AdministradorDAO.autenticar(emailInput, senhaInput)) {
                    response.sendRedirect(request.getContextPath() + "/paginasIniciais/PIAdmin/PIadmin.jsp");
                } else {

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