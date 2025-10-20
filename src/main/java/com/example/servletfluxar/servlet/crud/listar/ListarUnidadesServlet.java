package com.example.servletfluxar.servlet.crud.listar;

import com.example.servletfluxar.dao.AssinaturaDAO;
import com.example.servletfluxar.dao.UnidadeDAO;
import com.example.servletfluxar.model.Assinatura;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Unidade;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ListarUnidadesServlet", value = "/ListarUnidadesServlet")
public class ListarUnidadesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Declaração de variáveis:
        String tipoFiltro = request.getParameter("tipoFiltro");
        String valorFiltro = request.getParameter("valorFiltro");
        int pagina = 1;
        int limite = 6;
        HttpSession session = request.getSession(false);
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        List<Unidade> unidades = new ArrayList<>();

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
            unidades = unidadeDAO.listarPorIdEmpresa(pagina, limite, ((Empresa) session.getAttribute("empresa")).getId());

            request.setAttribute("unidades", unidades);
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
//                Listando unidades:
                unidades = unidadeDAO.listar(pagina, limite);
//                Verificando se a lista de unidades não está vazia:
                if (unidades.isEmpty()) {
//                    Caso esteja, reduzo uma página:
                    pagina--;
                    unidades = unidadeDAO.listar(pagina, limite);
                }
            }

//            Setando atributo assinatuas
            request.setAttribute("unidades", unidades);
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
