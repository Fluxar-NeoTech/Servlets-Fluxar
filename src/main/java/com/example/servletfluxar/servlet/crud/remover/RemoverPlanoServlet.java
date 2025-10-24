package com.example.servletfluxar.servlet.crud.remover;

import com.example.servletfluxar.dao.PlanoDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Plano;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RemoverPlanoServlet", value = "/RemoverPlanoServlet")
public class RemoverPlanoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter("id"));
        PlanoDAO planoDAO = new PlanoDAO();
        Plano plano = planoDAO.buscarPorId(id);

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                request.setAttribute("administrador", (Administrador) session.getAttribute("administrador"));
            } else {
                request.setAttribute("empresa", (Empresa) session.getAttribute("empresa"));
            }
        } catch (NullPointerException npe){
            request.setAttribute("erroLogin", "É necessário fazer login novamente");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }

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
        int id = Integer.parseInt(request.getParameter("id"));
        PlanoDAO planoDAO = new PlanoDAO();
        Plano plano = planoDAO.buscarPorId(id);

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                request.setAttribute("administrador", (Administrador) session.getAttribute("administrador"));
            } else {
                request.setAttribute("empresa", (Empresa) session.getAttribute("empresa"));
            }
        } catch (NullPointerException npe){
            request.setAttribute("erroLogin", "É necessário fazer login novamente");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }

        planoDAO.deletarPorId(plano.getId());

        response.sendRedirect(request.getContextPath() + "/ListarPlanosServlet");
    }
}