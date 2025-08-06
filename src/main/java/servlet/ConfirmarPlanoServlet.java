package servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ConfirmarPlanoServlet", value = "/ConfirmarPlanoServlet")
public class ConfirmarPlanoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        String planoEscolhido;
        int plano;
        HttpSession session = request.getSession();

//        Pegando dado do plano da página web:
        planoEscolhido = request.getParameter("plano");

//        Verificando qual plano é? (1, 2 ou 3)
        if (planoEscolhido.equals("Essential")){
            plano=1;
        }else if(planoEscolhido.equals("Profissional")){
            plano=2;
        }else{
            plano=3;
        }

//        Salvando o dado do plano temporariamente:
        session.setAttribute("plano",plano);
        response.sendRedirect(request.getContextPath() + "/cadastro/duracao/duracao.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}