package com.example.servletfluxar.servlet.crud.remover;

import com.example.servletfluxar.dao.PlanoDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Plano;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

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
            e.printStackTrace();
            request.setAttribute("erro", "Id do plano deve ser um número inteiro");
            request.getRequestDispatcher("WEB-INF/pages/planos/verPlanos.jsp")
                    .forward(request, response);
            return;
        }

        plano = planoDAO.buscarPorId(id);

        if (plano!=null) {
            if (planoDAO.contar() > 1) {
                request.setAttribute("plano", plano);
                request.getRequestDispatcher("WEB-INF/pages/planos/confirmarDelecao.jsp")
                        .forward(request, response);
            } else {
                request.getRequestDispatcher("WEB-INF/pages/planos/confirmarDelecao.jsp")
                        .forward(request, response);
            }
        } else {
            request.setAttribute("planos", new ArrayList<>());
            request.setAttribute("erro", "Não existe um plano com esse id");
            request.getRequestDispatcher("WEB-INF/pages/planos/verPlanos.jsp")
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
            e.printStackTrace();
            request.setAttribute("erro", "Ocorreu um erro ao procurar esse plano");
            request.getRequestDispatcher("WEB-INF/pages/planos/confirmarDelecao.jsp")
                    .forward(request, response);
            return;
        }

        if (planoDAO.buscarPorId(id) != null) {
            if (planoDAO.deletarPorId(id)) {
                response.sendRedirect(request.getContextPath() + "/ListarPlanosServlet");
            } else {
                request.setAttribute("planos", new ArrayList<>());
                request.setAttribute("erro", "Não foi possível deletar esse plano no momento, tente novamente mais tarde...");
                request.getRequestDispatcher("WEB-INF/pages/planos/verPlanos.jsp")
                        .forward(request, response);
            }
        } else {
            request.setAttribute("planos", new ArrayList<>());
            request.setAttribute("erro", "Não existe um plano com esse id");
            request.getRequestDispatcher("WEB-INF/pages/planos/verPlanos.jsp")
                    .forward(request, response);
        }
    }
}