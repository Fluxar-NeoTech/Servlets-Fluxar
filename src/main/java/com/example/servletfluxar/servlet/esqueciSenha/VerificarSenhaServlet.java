package com.example.servletfluxar.servlet.esqueciSenha;

import com.example.servletfluxar.dao.AdministradorDAO;
import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Funcionario;
import com.example.servletfluxar.util.ValidacaoInput;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet(name = "VerificarSenhaServlet", value = "/VerificarSenhaServlet")
public class VerificarSenhaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        // Declaração de variáveis:
        EmpresaDAO empresaDAO = new EmpresaDAO();
        AdministradorDAO administradorDAO = new AdministradorDAO();
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("registroAlterar");
        String novaSenha = request.getParameter("novaSenha");
        String senhaConfirmada = request.getParameter("senhaConfirmada");
        int senhaValida;
        boolean continuar = true;

        // Verificando se senha está nos padrões:
        if (novaSenha == null){
            request.setAttribute("erroSenha", "Defina uma senha para o administrador");
            continuar = false;
        } else {
            novaSenha = novaSenha.trim();
            senhaValida = ValidacaoInput.validarSenha(novaSenha);
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
                if (!novaSenha.equals(senhaConfirmada.trim())){
                    request.setAttribute("erroConfirmarSenha", "Senha confirmada incorreta");
                    continuar = false;
                }
            }
        }

        // Verificando se a senha e a senha confirmada são iguais:
        if (!continuar) {
            request.getRequestDispatcher("/pages/esqueciSenha/novaSenha.jsp")
                    .forward(request, response);
            return;
        }

//        Atualizando banco de dados:
        if(empresaDAO.alterarSenha(email, novaSenha) || administradorDAO.alterarSenha(email, novaSenha)){
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }else{
            request.setAttribute("erroSenha","Não foi possível alterar a senha");
            request.getRequestDispatcher("/pages/esqueciSenha/novaSenha.jsp")
                    .forward(request,response);
        }
    }
}