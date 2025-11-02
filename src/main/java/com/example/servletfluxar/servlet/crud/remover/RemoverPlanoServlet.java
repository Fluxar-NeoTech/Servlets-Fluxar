package com.example.servletfluxar.servlet.crud.remover;

import com.example.servletfluxar.dao.PlanoDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Plano;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RemoverPlanoServlet", value = "/RemoverPlanoServlet")
public class RemoverPlanoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        int id = 0;
        PlanoDAO planoDAO = new PlanoDAO();
        Plano plano = null;

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                request.setAttribute("administrador", (Administrador) session.getAttribute("administrador"));
            } else {
                response.sendRedirect(request.getContextPath()+"/ListarPlanosServlet");
                return;
            }
        } catch (NullPointerException npe){
            request.setAttribute("erro", "É necessário fazer login novamente");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        try{
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException | NullPointerException e){
            request.setAttribute("erro", e.getMessage());
            request.setAttribute("mensagem", "Ocorreu um erro ao procurar esse plano");
            request.getRequestDispatcher("")
                    .forward(request, response);
            return;
        }

        plano = planoDAO.buscarPorId(id);

        if (planoDAO.contar() > 1){
            request.setAttribute("plano", plano);
            request.getRequestDispatcher("WEB-INF/pages/planos/confirmarDelecao.jsp")
                    .forward(request, response);
        } else {
            request.getRequestDispatcher("WEB-INF/pages/planos/confirmarDelecao.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        int id = 0;
        PlanoDAO planoDAO = new PlanoDAO();

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                request.setAttribute("administrador", (Administrador) session.getAttribute("administrador"));
            } else {
                response.sendRedirect(request.getContextPath() + "/ListarPlanosServlet");
                return;
            }
        } catch (NullPointerException npe){
            request.setAttribute("erro", "É necessário fazer login novamente");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        try{
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException | NullPointerException e){
            request.setAttribute("erro", e.getMessage());
            request.setAttribute("mensagem", "Ocorreu um erro ao procurar esse plano");
            request.getRequestDispatcher("")
                    .forward(request, response);
            return;
        }

        planoDAO.deletarPorId(id);

        response.sendRedirect(request.getContextPath() + "/ListarPlanosServlet");
    }
}