package com.example.servletfluxar.servlet;

import com.example.servletfluxar.dao.AdministradorDAO;
import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Funcionario;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import util.EmailService;
import java.io.IOException;

@WebServlet(name = "EsqueciSenhaEnviarCodigoServlet", value = "/EsqueciSenhaEnviarCodigoServlet")
public class EsqueciSenhaEnviarCodigoServlet extends HttpServlet {
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
        empresa = EmpresaDAO.buscarPorEmail(emailInput);
        administrador = AdministradorDAO.buscarPorEmail(emailInput);

        if (empresa != null || administrador != null){
            session.setAttribute("registroAlterar",emailInput);
            codigo = String.valueOf((int) (Math.random() * 900000 + 100000));

            try {
                EmailService.enviarEmail(emailInput, "Seu código de verificação", "<h2>Código:" + codigo+"</h2><br><p>Não responda a esse email</p>");

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