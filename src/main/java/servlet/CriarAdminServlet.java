package servlet;

import dao.AdministradorDAO;
import dao.EmpresaDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Empresa;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CriarAdminServlet", value = "/CriarAdminServlet")
public class CriarAdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declarando variáveis:
        HttpSession session = request.getSession();
        String emailInput;
        String senhaInput;
        String senhaConf;
        String senhaCriptografada;
        RequestDispatcher dispatcher = null;
        String status = (String) session.getAttribute("status");
        Administrador admin;
        Empresa emp;
        int codigoEmpresa;
        boolean temNumero = false;
        boolean temMaiuscula = false;
        boolean temMinuscula = false;
        Administrador admin2 = new Administrador();
        List<Administrador> admins = AdministradorDAO.listarTodos();


//        Pegando input do usuário:
        emailInput = request.getParameter("emailAdmin");
        senhaInput = request.getParameter("senhaAdmin");
        senhaConf = request.getParameter("confirmarSenha");

//        Verificando se o email já está cadastrado, caso esteja verificar qual empresa ele é:
        admin = AdministradorDAO.buscarPorEmail(emailInput);
        if (admin != null) {
            emp = EmpresaDAO.buscarPorCodigo(admin.getCodEmpresa());
            if (emp.getStatus().equals("A")) {
                request.setAttribute("erroEmail", "Esse email está sendo usado");
                dispatcher = request.getRequestDispatcher("/cadastro/Admin/escolherAdmin.jsp");
                dispatcher.forward(request, response);
                return;
            }
        }
        if (senhaInput.length() < 8) {
            request.setAttribute("erroSenha", "Senha possui menos de 8 caracteres");
        } else {
            for (char c : senhaInput.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    temMaiuscula = true;
                } else if (Character.isLowerCase(c)) {
                    temMinuscula = true;
                } else if (Character.isDigit(c)) {
                    temNumero = true;
                }
            }
            if (temMaiuscula == false) {
                request.setAttribute("erroSenha", "Senha deve ter 1 letra maiúscula");
            } else if (temMinuscula == false) {
                request.setAttribute("erroSenha", "Senha deve ter 1 letra minúscula");
            } else if (temNumero == false) {
                request.setAttribute("erroSenha", "Senha deve ter um número");
            }
            if (request.getAttribute("erroSenha") != null) {
                dispatcher = request.getRequestDispatcher("/cadastro/Admin/escolherAdmin.jsp");
                dispatcher.forward(request, response);
                return;
            }
        }

        if (!senhaInput.equals(senhaConf)) {
            request.setAttribute("erroConfSenha", "Senha incorreta");
            dispatcher = request.getRequestDispatcher("/cadastro/Admin/escolherAdmin.jsp");
            dispatcher.forward(request, response);
            return;
        }

//        Buscando código da empresa:
        codigoEmpresa = (int) session.getAttribute("codigoEmpresa");

        senhaCriptografada = BCrypt.hashpw(senhaInput, BCrypt.gensalt());

//        Cadastrando Admin:
        admin2.setCodEmpresa(codigoEmpresa);
        admin2.setEmail(emailInput);
        admin2.setSenha(senhaCriptografada);

        session.setAttribute("emailAdmin", emailInput);
        session.setAttribute("senhaAdmin", senhaCriptografada);

        if (AdministradorDAO.cadastrar(admin2)) {
            response.sendRedirect(request.getContextPath() +"/cadastro/fimCadastro/agradecimentos.html");
        } else {
            request.setAttribute("erroEmail", "Não foi possível realizar o cadastro");
            dispatcher = request.getRequestDispatcher("/cadastro/Admin/escolherAdmin.jsp");
            dispatcher.forward(request, response);
        }
    }
}