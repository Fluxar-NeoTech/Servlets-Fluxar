package com.example.servletfluxar.servlet.crud.listar;

import com.example.servletfluxar.dao.AssinaturaDAO;
import com.example.servletfluxar.model.Assinatura;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ListarAssinaturasServlet", value = "/ListarAssinaturasServlet")
public class ListarAssinaturasServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //        Declaração de variáveis:
        String tipoFiltro = request.getParameter("tipoFiltro");
        String valorFiltro = request.getParameter("valorFiltro");
        int pagina = 1;
        int limite = 6;
        AssinaturaDAO assinaturaDAO = new AssinaturaDAO();
        Map<Integer, Assinatura> assinaturas = new HashMap<>();

//        Verificando a página atual:
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
        if (tipoFiltro != null){
//            Verificando se há algum valor definido para o filtro:
            if (valorFiltro != null) {

            } else {
                request.setAttribute("erroFiltro", "Defina um valor para o filtro");
                request.getRequestDispatcher("")
                        .forward(request, response);
                return;
            }

        } else {
            assinaturas = assinaturaDAO.listar(pagina, limite);
            if (assinaturas.isEmpty()){
                pagina--;
                assinaturas = assinaturaDAO.listar(pagina, limite);
            }
        }

//        Setando atributos:
        request.setAttribute("pagina", pagina);
        request.setAttribute("assinatura", assinaturas);
//        Enviando retorno:
        request.getRequestDispatcher("")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
