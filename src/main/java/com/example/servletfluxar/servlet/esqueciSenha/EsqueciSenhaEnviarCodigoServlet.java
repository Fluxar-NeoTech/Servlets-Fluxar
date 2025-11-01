package com.example.servletfluxar.servlet.esqueciSenha;

import com.example.servletfluxar.dao.AdministradorDAO;
import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.dao.FuncionarioDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Funcionario;
import com.example.servletfluxar.util.ValidacaoInput;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.example.servletfluxar.util.EnvioEmail;
import java.io.IOException;

@WebServlet(name = "EsqueciSenhaEnviarCodigoServlet", value = "/EsqueciSenhaEnviarCodigoServlet")
public class EsqueciSenhaEnviarCodigoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        String emailInput;
        EmpresaDAO empresaDAO = new EmpresaDAO();
        Empresa empresa;
        AdministradorDAO administradorDAO = new AdministradorDAO();
        Administrador administrador;
        String codigo;
        HttpSession session = request.getSession();

//        Recebendo o input do usuário:
        emailInput = request.getParameter("emailUsuario").trim().toLowerCase();

//        Verificando se o email possui formato válido:
        if (!ValidacaoInput.validarEmail(emailInput)){
            request.setAttribute("erroEmail", "Formato de email inválido");
            request.getRequestDispatcher("/pages/esqueciSenha/digitarEmail.jsp")
                    .forward(request, response);
            return;
        }

//        Verificando se o email está cadastrado:
        empresa = empresaDAO.buscarPorEmail(emailInput);
        administrador = administradorDAO.buscarPorEmail(emailInput);

        if (empresa != null || administrador != null){
            session.setAttribute("registroAlterar",emailInput);
            codigo = String.valueOf((int) (Math.random() * 900000 + 100000));

            try {
//                Enviando um email com o código de verificação para ele
                EnvioEmail.enviarEmail(emailInput, "Seu código de verificação", "<h2>Código:" + codigo+"</h2><br><p>Não responda a esse email</p>");

                session.setAttribute("codigoVerificacao", codigo);
                response.sendRedirect(request.getContextPath() +"/pages/esqueciSenha/codigo.jsp");

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("erroEmail", e);
                request.getRequestDispatcher("/pages/esqueciSenha/digitarEmail.jsp")
                        .forward(request, response);
            }

        }else{
//            Comunicando email inválido
            request.setAttribute("erroEmail", "Email não cadastrado");
            request.getRequestDispatcher("/pages/esqueciSenha/digitarEmail.jsp")
                    .forward(request, response);
        }
    }
}