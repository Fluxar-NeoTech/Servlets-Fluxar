package servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "DuracaoPlanoServlet", value = "/DuracaoPlanoServlet")
public class DuracaoPlanoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declarando variáveis:
        String duracaoInput;
        int duracao = 0;
        HttpSession session = request.getSession();

//        Pegando input do usuário:
        duracaoInput = request.getParameter("duracao");

//        Verificando e convertendo para int:
        if (duracaoInput.equals("mensal")){
            duracao = 1;
        }else if(duracaoInput.equals("anual")){
            duracao = 12;
        }

//        Salvando:
        session.setAttribute("duracao",duracao);

//        Enviando usuário para próxima tela:
        response.sendRedirect(request.getContextPath() + "/cadastro/confirmarDados/confirmacao.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
