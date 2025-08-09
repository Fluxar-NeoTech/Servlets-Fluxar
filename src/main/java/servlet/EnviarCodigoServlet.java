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
import util.EmailService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "EnviarCodigoServlet", value = "/EnviarCodigoServlet")
public class EnviarCodigoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        String emailInput;
        RequestDispatcher dispatcher = null;
        Funcionario funcionario;
        Empresa empresa;
        Administrador administrador;
        String codigo;
        HttpSession session = request.getSession();

//        Recebendo o input do usuário:
        emailInput = request.getParameter("emailUsuario").trim();

//        Verificando se o email é válido:
        empresa = EmpresaDAO.buscarEmpresa("email",emailInput);
        administrador = AdministradorDAO.buscarAdministrador("email",emailInput);
        if (empresa.getEmail()!=null || administrador.getEmail()!=null){
            session.setAttribute("registroAlterar",emailInput);
            codigo = String.valueOf((int) (Math.random() * 900000 + 100000));
            try {
                EmailService.enviarEmail(emailInput, "Seu código de verificação", "Código: " + codigo+"\nNão responda a esse email");
                session.setAttribute("codigoVerificacao", codigo);
                response.sendRedirect(request.getContextPath() +"/fazerLogin/esqueciSenha/codigo/codigo.jsp");
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("erroEmail", e);
                dispatcher = request.getRequestDispatcher("/fazerLogin/esqueciSenha/inputEmail/recuperarSenha.jsp");
                dispatcher.forward(request, response);
            }

        }else{
//            Comunicando email inválido
            request.setAttribute("erroEmail", "Email não cadastrado");
            dispatcher = request.getRequestDispatcher("/fazerLogin/esqueciSenha/inputEmail/recuperarSenha.jsp");
            dispatcher.forward(request, response);
        }
    }
}