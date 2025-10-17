package com.example.servletfluxar.servlet.crud.AdminNeoTech.listar;

import com.example.servletfluxar.dao.PlanoDAO;
import com.example.servletfluxar.model.Plano;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "ListarPlanosServlet", value = "/ListarPlanosServlet")
public class ListarPlanosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declarando variáveis:
        String tipoFiltro = request.getParameter("tipoFiltro");
        String valorFiltro = request.getParameter("valorFiltro");
        int pagina = 1;
        int limite = 6;

        PlanoDAO planoDAO = new PlanoDAO();
        Map<Integer, Plano> planos = null;

//        Coletando informações do jsp:
        if (request.getParameter("pagina") != null){
            pagina = Integer.parseInt(request.getParameter("pagina"));
        }

        if (tipoFiltro !=  null) {
            if (valorFiltro != null) {

            } else {
                request.setAttribute("erroFiltro", "Defina um valor para o filtro");
                request.getRequestDispatcher("")
                        .forward(request, response);
                return;
            }
        } else {
            planos = planoDAO.listar(pagina, limite);
            if (planos == null){
                pagina--;
                planos = planoDAO.listar(pagina, limite);
            }
        }

        request.getRequestDispatcher("")
                .forward(request, response);
    }
}
