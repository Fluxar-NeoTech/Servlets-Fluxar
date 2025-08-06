package servlet;

import dao.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "TabelaAdminServlet", value = "/TabelaAdminServlet")
public class TabelaAdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        String tabela;
        String acao = (String) session.getAttribute("acaoAdmin");

//        Coletando dados do .jsp:
        tabela = request.getParameter("tabela");
        session.setAttribute("tabelaAdmin",tabela);


//        Redirecionando para a página correta:
        if(acao.equals("ver")){
            switch (tabela) {
                case "empresa":
                    session.setAttribute("dados", EmpresaDAO.buscarTodas());
                    break;
                case "administradores":
                    session.setAttribute("dados", AdministradorDAO.listarTodos());
                    break;
                case "unidade":
                    session.setAttribute("dados", UnidadeDAO.listarTodas());
                    break;
                case "usuario":
                    session.setAttribute("dados", UsuarioDAO.listarTodos());
                    break;
                case "setor":
                    session.setAttribute("dados", SetorDAO.listarTodos());
                    break;
                case "insumos":
                    session.setAttribute("dados", InsumosDAO.listarTodos());
                    break;
            }
            response.sendRedirect(request.getContextPath() + "/telasAdmin/mostrarDados/mostrarDados.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
