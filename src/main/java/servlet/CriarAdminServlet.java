package servlet;

import dao.EmpresaDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Administrador;
import model.Empresa;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
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
        String senhaInput;
        String senhaConf;
        String senhaCriptografada;
        RequestDispatcher dispatcher = null;
        boolean temNumero = false;
        boolean temMaiuscula = false;
        boolean temMinuscula = false;


//        Pegando input do usuário:
        senhaInput = request.getParameter("senha");
        senhaConf = request.getParameter("senhaConfirmada");

//        Verificando se a senha possui os requisitos mínimos
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
                dispatcher = request.getRequestDispatcher("/cadastro/Admin/escolhaSenha.jsp");
                dispatcher.forward(request, response);
                return;
            }
        }

        if (!senhaInput.equals(senhaConf)) {
            request.setAttribute("erroConfSenha", "Senha incorreta");
            dispatcher = request.getRequestDispatcher("/cadastro/Admin/escolhaSenha.jsp");
            dispatcher.forward(request, response);
            return;
        }

//        Criptografando senha do admin:
        senhaCriptografada = BCrypt.hashpw(senhaInput, BCrypt.gensalt());

//        Salvando senha:
        session.setAttribute("senhaAdmin",senhaCriptografada);

//        Redirecionando usuário:
        response.sendRedirect(request.getContextPath() +"/cadastro/pagamento/formaPagamento/pagamento.html");
    }
}