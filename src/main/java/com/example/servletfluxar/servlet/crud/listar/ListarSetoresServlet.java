package com.example.servletfluxar.servlet.crud.listar;

import com.example.servletfluxar.dao.SetorDAO;
import com.example.servletfluxar.dao.UnidadeDAO;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Setor;
import com.example.servletfluxar.model.Unidade;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ListarSetoresServlet", value = "/ListarSetoresServlet")
public class ListarSetoresServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Declaração de variáveis:
        String tipoFiltro = request.getParameter("tipoFiltro");
        String valorFiltro = request.getParameter("valorFiltro");
        int pagina = 1;
        int limite = 6;
        HttpSession session = request.getSession(false);
        SetorDAO setorDAO = new SetorDAO();
        List<Setor> setores = new ArrayList<>();

        if (session == null || session.getAttribute("tipoUsuario") == null) {
            System.out.println("erro");
            request.setAttribute("mensagem", "Faça login novamente");
            request.getRequestDispatcher("")
                    .forward(request, response);
            return;
        }

//                Verificando a página atual:
        if (request.getParameter("pagina") != null) {
            pagina = Integer.parseInt(request.getParameter("pagina"));
            if (pagina < 1) {
                pagina = 1;
            }
        }

        if (session.getAttribute("tipoUsuario").equals("empresa")) {
//              Vendo se há algum filtro definido:
            if (tipoFiltro != null) {
//                     Verificando se há algum valor definido para o filtro:
                if (valorFiltro != null) {

                } else {
                    request.setAttribute("erroFiltro", "Defina um valor para o filtro");
                    request.getRequestDispatcher("")
                            .forward(request, response);
                }
            } else {
//                Listando setores:
                setores = setorDAO.listarPorIdEmpresa(pagina, limite, ((Empresa) session.getAttribute("empresa")).getId());
//                Verificando se a lista de setores não está vazia:
                if (setores.isEmpty()) {
//                    Caso esteja, reduzo uma página:
                    pagina--;
                    setores = setorDAO.listarPorIdEmpresa(pagina, limite, ((Empresa) session.getAttribute("empresa")).getId());
                }
            }
            request.setAttribute("setores", setores);
        } else {
//              Vendo se há algum filtro definido:
            if (tipoFiltro != null) {
//                     Verificando se há algum valor definido para o filtro:
                if (valorFiltro != null) {

                } else {
                    request.setAttribute("erroFiltro", "Defina um valor para o filtro");
                    request.getRequestDispatcher("")
                            .forward(request, response);
                    return;
                }

            } else {
//                Listando setores:
                setores = setorDAO.listar(pagina, limite);
//                Verificando se a lista de setores não está vazia:
                if (setores.isEmpty()) {
//                    Caso esteja, reduzo uma página:
                    pagina--;
                    setores = setorDAO.listar(pagina, limite);
                }
            }

//            Setando atributo assinatuas
            request.setAttribute("setores", setores);
        }

        request.setAttribute("tipoUsuario", session.getAttribute("tipoUsuario"));

//        Setando atributo de página atual:
        request.setAttribute("pagina", pagina);

//        Enviando retorno:
        request.getRequestDispatcher("")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
