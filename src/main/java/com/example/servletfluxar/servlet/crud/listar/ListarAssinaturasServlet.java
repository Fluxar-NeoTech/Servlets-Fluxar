package com.example.servletfluxar.servlet.crud.listar;

import com.example.servletfluxar.dao.AssinaturaDAO;
import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.dao.SetorDAO;
import com.example.servletfluxar.model.Assinatura;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Setor;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        int totalRegitros = 0;
        int totalPaginas = 0;
        HttpSession session = request.getSession(false);
        AssinaturaDAO assinaturaDAO = new AssinaturaDAO();
        List<Assinatura> assinaturas = new ArrayList<>();

        if (session == null || session.getAttribute("tipoUsuario") == null) {
            System.out.println("erro");
            request.setAttribute("mensagem", "Faça login novamente");
            request.getRequestDispatcher("/fazerLogin/paginaLogin/login.jsp")
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
            totalRegitros = 1;
            totalPaginas = Math.max(1, (int) Math.ceil(totalRegitros / 6.0));

//              Garante que pagina está no intervalo válido [1, totalPaginas]
            pagina = Math.max(1, Math.min(pagina, totalPaginas));

            assinaturas.add(assinaturaDAO.buscarPorIdEmpresa(((Empresa) session.getAttribute("empresa")).getId()));
        } else {
            //              Vendo se há algum filtro definido:
            if (tipoFiltro != null) {
//                     Verificando se há algum valor definido para o filtro:
                if (valorFiltro != null) {

                } else {
                    request.setAttribute("erroFiltro", "Defina um valor para o filtro");
                    request.getRequestDispatcher("WEB-INF/pages/assinaturas/verAssinaturas.jsp")
                            .forward(request, response);
                    return;
                }

            } else {
                totalRegitros = assinaturaDAO.contar();
                totalPaginas = Math.max(1, (int) Math.ceil(totalRegitros / 6.0));

//              Garante que pagina está no intervalo válido [1, totalPaginas]
                pagina = Math.max(1, Math.min(pagina, totalPaginas));

                assinaturas = totalRegitros > 0 ? assinaturaDAO.listar(pagina, limite) : new ArrayList<>();
            }
        }

        request.setAttribute("assinaturas", assinaturas);
        request.setAttribute("tipoUsuario", session.getAttribute("tipoUsuario"));

//        Setando atributo de página atual:
        request.setAttribute("pagina", pagina);

//        Enviando retorno:
        request.getRequestDispatcher("WEB-INF/pages/assinaturas/verAssinaturas.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
