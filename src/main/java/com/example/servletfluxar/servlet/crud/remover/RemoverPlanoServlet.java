package com.example.servletfluxar.servlet.crud.remover;

import com.example.servletfluxar.dao.PlanoDAO;
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
        int id = Integer.parseInt(request.getParameter("id"));
        PlanoDAO planoDAO = new PlanoDAO();
        Plano plano = planoDAO.buscarPorId(id);

        planoDAO.deletarPorId(plano.getId());

        request.setAttribute("plano", plano);
        request.getRequestDispatcher("/WEB-INF/pages/planos/verPlanos.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
