package com.example.servletfluxar.servlet.crud.listar;

import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.dao.UnidadeDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Unidade;
import com.example.servletfluxar.util.RegrasBanco;
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
        response.setContentType("text/html");
        //        Declaração de variáveis:
        String tipoFiltro = request.getParameter("tipoFiltro");
        String valorFiltro = request.getParameter("valorFiltro");
        int pagina = 1;
        int limite = 6;
        int id = 0;
        int totalRegitros = 0;
        int totalPaginas = 0;
        HttpSession session = request.getSession(false);
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        EmpresaDAO empresaDAO = new EmpresaDAO();
        Unidade unidade;
        Empresa empresa;
        List<Unidade> unidades = new ArrayList<>();

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
            totalRegitros = unidadeDAO.contarPorIdEmpresa(((Empresa) session.getAttribute("empresa")).getId());
            totalPaginas = Math.max(1, (int) Math.ceil(totalRegitros / 6.0));

//              Garante que pagina está no intervalo válido [1, totalPaginas]
            pagina = Math.max(1, Math.min(pagina, totalPaginas));

            unidades = totalRegitros > 0 ? unidadeDAO.listarPorIdEmpresa(pagina, limite, ((Empresa) session.getAttribute("empresa")).getId()) : new ArrayList<>();
        } else {
//          Vendo se há algum filtro definido:
            if (tipoFiltro != null) {
                tipoFiltro = tipoFiltro.trim().toLowerCase();
//              Verificando se há algum valor definido para o filtro:
                if (valorFiltro != null) {
                    valorFiltro = valorFiltro.trim().toLowerCase();
                    if (tipoFiltro.equals("id")){
                        try {
                            id = Integer.parseInt(valorFiltro);
                            if (id > 0) {
                                unidade = unidadeDAO.buscarPorId(id);
                                if (unidade != null) {
                                    unidades.add(unidade);
                                } else {
                                    request.setAttribute("unidades", new ArrayList<>());
                                    request.setAttribute("erro", "Nenhuma unidade foi encontrado nessa filtragem");
                                    request.getRequestDispatcher("WEB-INF/pages/unidades/verUnidades.jsp")
                                            .forward(request, response);
                                    return;
                                }
                            } else {
                                request.setAttribute("erroValorFiltro", "Id da unidade deve ser maior do que 0");
                            }
                        } catch (NumberFormatException | NullPointerException e){
                            request.setAttribute("erroValorFiltro", "Id da unidade deve ser um número");
                        }
                    } else if (tipoFiltro.equals("empresa")){
                        valorFiltro = RegrasBanco.nomeCapitalize(valorFiltro);
                        empresa = empresaDAO.buscarPorNome(valorFiltro);
                        if (empresa != null){
                            totalRegitros = unidadeDAO.contarPorIdEmpresa(empresa.getId());
                            totalPaginas = Math.max(1, (int) Math.ceil(totalRegitros / 6.0));
                            pagina = Math.max(1, Math.min(pagina, totalPaginas));

                            unidades = totalRegitros>0 ? unidadeDAO.listarPorIdEmpresa(pagina, limite, empresa.getId()) : new ArrayList<>();

                            if (unidades.isEmpty()){
                                request.setAttribute("unidades", new ArrayList<>());
                                request.setAttribute("erro", "Essa empresa não possui unidades cadastradas");
                                request.getRequestDispatcher("WEB-INF/pages/unidades/verUnidades.jsp")
                                        .forward(request, response);
                                return;
                            }
                        } else {
                            request.setAttribute("unidades", new ArrayList<>());
                            request.setAttribute("erro", "Empresa não encontrada, passe o nome completo da empresa");
                            request.getRequestDispatcher("WEB-INF/pages/unidades/verUnidades.jsp")
                                    .forward(request, response);
                            return;
                        }
                    } else if (tipoFiltro.equals("nome")){
                        valorFiltro = RegrasBanco.nomeCapitalize(valorFiltro);
                    } else {
                        request.setAttribute("erroValorFiltro", "Filtro não disponível");
                    }
                } else {
                    request.setAttribute("unidades", new ArrayList<>());
                    request.setAttribute("erroFiltro", "Defina um valor para o filtro");
                    request.getRequestDispatcher("WEB-INF/pages/unidades/verUnidades.jsp")
                            .forward(request, response);
                    return;
                }

            } else {
                totalRegitros = unidadeDAO.contar();
                totalPaginas = Math.max(1, (int) Math.ceil(totalRegitros / 6.0));

//              Garante que pagina está no intervalo válido [1, totalPaginas]
                pagina = Math.max(1, Math.min(pagina, totalPaginas));

                unidades = totalRegitros > 0 ? unidadeDAO.listar(pagina, limite) : new ArrayList<>();
            }
        }

        request.setAttribute("unidades", unidades);
        request.setAttribute("tipoUsuario", session.getAttribute("tipoUsuario"));

//        Setando atributo de página atual:
        request.setAttribute("pagina", pagina);

//        Enviando retorno:
        request.getRequestDispatcher("WEB-INF/pages/unidades/verUnidades.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}