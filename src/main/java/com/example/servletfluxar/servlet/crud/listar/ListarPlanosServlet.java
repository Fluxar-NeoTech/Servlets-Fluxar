package com.example.servletfluxar.servlet.crud.listar;

import com.example.servletfluxar.dao.PlanoDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Plano;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ListarPlanosServlet", value = "/ListarPlanosServlet")
public class ListarPlanosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declarando variáveis:
        String tipoFiltro = request.getParameter("tipoFiltro");
        String valorFiltro = request.getParameter("valorFiltro");
        HttpSession session = request.getSession();
        int pagina = 1;
        int limite = 6;
        int totalRegitros = 0;
        int totalPaginas = 0;
        PlanoDAO planoDAO = new PlanoDAO();
        List<Plano> planos = null;

//        Informando o tipo de usuário logado a fim de saber se pode ou não editar/adicionar dados;
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

//        Verificando se no jsp é declarado :
        if (request.getParameter("pagina") != null){
            pagina = Integer.parseInt(request.getParameter("pagina"));
        }

//        Vendo se há algum filtro definido:
        if (tipoFiltro !=  null) {
//            Verificando se há algum valor definido para o filtro:
            if (valorFiltro != null) {

            } else {
                request.setAttribute("erroFiltro", "Defina um valor para o filtro");
                request.getRequestDispatcher("WEB-INF/pages/planos/verPlanos.jsp")
                        .forward(request, response);
                return;
            }
        } else {
//            Caso não haja um filtro, ele retorna todos os elementos:
            totalRegitros = planoDAO.contar();
            totalPaginas = Math.max(1, (int) Math.ceil(totalRegitros / 6.0));

//              Garante que pagina está no intervalo válido [1, totalPaginas]
            pagina = Math.max(1, Math.min(pagina, totalPaginas));

            planos = totalRegitros > 0 ? planoDAO.listar(pagina, limite) : new ArrayList<>();
        }

        request.setAttribute("pagina", pagina);
        request.setAttribute("planos", planos);

        request.getRequestDispatcher("WEB-INF/pages/planos/verPlanos.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}