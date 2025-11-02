package com.example.servletfluxar.servlet.crud.listar;

import com.example.servletfluxar.dao.SetorDAO;
import com.example.servletfluxar.model.*;
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
        int totalRegistros = 0;
        int totalPaginas = 0;
        HttpSession session = request.getSession(false);
        SetorDAO setorDAO = new SetorDAO();
        List<Setor> setores = new ArrayList<>();

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                request.setAttribute("administrador", (Administrador) session.getAttribute("administrador"));
            } else {
                request.setAttribute("empresa", (Empresa) session.getAttribute("empresa"));
            }
        } catch (NullPointerException npe){
            request.setAttribute("erro", "É necessário fazer login novamente");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

//                Verificando a página atual:
        if (request.getParameter("pagina") != null) {
            pagina = Integer.parseInt(request.getParameter("pagina"));
        }

        if (session.getAttribute("tipoUsuario").equals("empresa")) {
            if (tipoFiltro != null){

            } else {
                totalRegistros = setorDAO.contarPorIdEmpresa(((Empresa) session.getAttribute("empresa")).getId());
                totalPaginas = Math.max(1, (int) Math.ceil(totalRegistros / 6.0));

//              Garante que pagina está no intervalo válido [1, totalPaginas]
                pagina = Math.max(1, Math.min(pagina, totalPaginas));

                setores = totalRegistros > 0 ? setorDAO.listarPorIdEmpresa(pagina, limite, ((Empresa) session.getAttribute("empresa")).getId()) : new ArrayList<>();
            }
        } else {
            //              Vendo se há algum filtro definido:
            if (tipoFiltro != null) {
//                     Verificando se há algum valor definido para o filtro:
                if (valorFiltro != null) {

                } else {
                    request.setAttribute("erroFiltro", "Defina um valor para o filtro");
                    request.getRequestDispatcher("WEB-INF/pages/setores/verSetores.jsp")
                            .forward(request, response);
                    return;
                }

            } else {
                totalRegistros = setorDAO.contar();
                totalPaginas = Math.max(1, (int) Math.ceil(totalRegistros / 6.0));

//              Garante que pagina está no intervalo válido [1, totalPaginas]
                pagina = Math.max(1, Math.min(pagina, totalPaginas));

                setores = totalRegistros > 0 ? setorDAO.listar(pagina, limite) : new ArrayList<>();
            }
        }

        request.setAttribute("setores", setores);
        request.setAttribute("tipoUsuario", session.getAttribute("tipoUsuario"));

//        Setando atributo de página atual:
        request.setAttribute("pagina", pagina);

//        Enviando retorno:
        request.getRequestDispatcher("WEB-INF/pages/setores/verSetores.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
