package com.example.servletfluxar.servlet.crud.listar;

import com.example.servletfluxar.dao.PlanoDAO;
import com.example.servletfluxar.model.Plano;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
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
        PlanoDAO planoDAO = new PlanoDAO();
        List<Plano> planos = null;

//        Informando o tipo de usuário logado a fim de saber se pode ou não editar/adicionar dados;
        try {
            if (session.getAttribute("tipoUsuario")!=null) {
                request.setAttribute("tipoUsuario", session.getAttribute("tipoUsuario"));
            }
        }catch (NullPointerException npe){
            request.setAttribute("mensagem", "Você passou tempo demais logado, faça seu login novamente");
            request.setAttribute("erro", npe);
            request.getRequestDispatcher("")
                    .forward(request, response);
        }

//        Verificando se no jsp é declarado :
        if (request.getParameter("pagina") != null){
            try {
                pagina = Integer.parseInt(request.getParameter("pagina"));
                if (pagina<1){
                    pagina=1;
                }
            } catch (NullPointerException npe){
                pagina = 1;
            }
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
            planos = planoDAO.listar(pagina, limite);
            if (planos.isEmpty()){
                pagina--;
                planos = planoDAO.listar(pagina, limite);
            }
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