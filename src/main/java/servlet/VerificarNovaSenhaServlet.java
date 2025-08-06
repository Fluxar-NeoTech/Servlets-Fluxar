package servlet;

import dao.AdministradorDAO;
import dao.UsuarioDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Administrador;
import model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet(name = "VerificarNovaSenhaServlet", value = "/VerificarNovaSenhaServlet")
public class VerificarNovaSenhaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        // Declaração de variáveis:
        Administrador admin;
        Usuario user;
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("emailAlterarSenha");
        String novaSenha = request.getParameter("novaSenha").trim();
        String senhaConfirmada = request.getParameter("senhaConfirmada").trim();
        String senhaCriptografada;
        boolean temNumero = false;
        boolean temMaiuscula = false;
        boolean temMinuscula = false;
        RequestDispatcher dispatcher = null;

        // Verificando se senha está nos padrões:
        if (novaSenha.length() < 8) {
            request.setAttribute("erroSenha", "Senha possui menos de 8 caracteres");
            dispatcher = request.getRequestDispatcher(
                    request.getContextPath() + "/fazerLogin/esqueciSenha/novaSenha/novaSenha.jsp");
            dispatcher.forward(request, response);
            return;
        } else {
            for (char c : novaSenha.toCharArray()) {
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
                dispatcher = request.getRequestDispatcher("/fazerLogin/esqueciSenha/novaSenha/novaSenha.jsp");
                dispatcher.forward(request, response);
                return;
            }
        }

        // Verificando se a senha e a senha confirmada são iguais:
        if (!senhaConfirmada.equals(novaSenha)) {
            request.setAttribute("erroConfSenha", "Senha incorreta");
            dispatcher = request.getRequestDispatcher("/fazerLogin/esqueciSenha/novaSenha/novaSenha.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Criptografando a senha:
        senhaCriptografada = BCrypt.hashpw(novaSenha, BCrypt.gensalt());

        // Verificando se o usuário é gestor/analista ou administrador ou se é ambos:
        if (!UsuarioDAO.buscarUsuarioPeloEmail(email).isEmpty() && AdministradorDAO.existeEmail(email)) {
            if (AdministradorDAO.alterarSenha(email, senhaCriptografada)) {
                if (UsuarioDAO.alterarSenha(email, senhaCriptografada)) {
                    response.sendRedirect(request.getContextPath() + "/fazerLogin/paginaLogin/login.jsp");
                } else {
                    request.setAttribute("erroSenha", "Erro ao alterar a senha");
                    dispatcher = request.getRequestDispatcher("/fazerLogin/esqueciSenha/novaSenha/novaSenha.jsp");
                    dispatcher.forward(request, response);
                }
            } else {
                request.setAttribute("erroSenha", "Erro ao alterar a senha");
                dispatcher = request.getRequestDispatcher("/fazerLogin/esqueciSenha/novaSenha/novaSenha.jsp");
                dispatcher.forward(request, response);
            }
        } else if (!UsuarioDAO.buscarUsuarioPeloEmail(email).isEmpty()) {
            if (UsuarioDAO.alterarSenha(email, senhaCriptografada)) {
                response.sendRedirect(request.getContextPath() + "/fazerLogin/paginaLogin/login.jsp");
            } else {
                request.setAttribute("erroSenha", "Erro ao alterar a senha");
                dispatcher = request.getRequestDispatcher("/fazerLogin/esqueciSenha/novaSenha/novaSenha.jsp");
                dispatcher.forward(request, response);
            }
        } else if(AdministradorDAO.existeEmail(email)){
            if (AdministradorDAO.alterarSenha(email, senhaCriptografada)) {
                response.sendRedirect(request.getContextPath() + "/fazerLogin/paginaLogin/login.jsp");
            } else {
                request.setAttribute("erroSenha", "Erro ao alterar a senha");
                dispatcher = request.getRequestDispatcher("/fazerLogin/esqueciSenha/novaSenha/novaSenha.jsp");
                dispatcher.forward(request, response);
            }
        }
    }
}
