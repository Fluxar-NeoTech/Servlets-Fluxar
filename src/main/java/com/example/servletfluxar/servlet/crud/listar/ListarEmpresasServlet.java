package com.example.servletfluxar.servlet.crud.listar;

import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.dao.TelefoneDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Telefone;
import com.example.servletfluxar.util.RegrasBanco;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ListarEmpresasServlet", value = "/ListarEmpresasServlet")
public class ListarEmpresasServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Declaração de variáveis:
        String tipoFiltro = request.getParameter("tipoFiltro");
        String valorFiltro = request.getParameter("valorFiltro");
        int pagina = 1;
        int limite = 6;
        int totalRegitros = 0;
        int totalPaginas = 0;
        int id = 0;
        HttpSession session = request.getSession(false);
        EmpresaDAO empresaDAO = new EmpresaDAO();
        List<Empresa> empresas = new ArrayList<>();
        Empresa empresa;

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")) {
                request.setAttribute("administrador", (Administrador) session.getAttribute("administrador"));
            } else {
                request.setAttribute("empresa", (Empresa) session.getAttribute("empresa"));
            }
        } catch (NullPointerException npe) {
            request.setAttribute("erro", "É necessário fazer login novamente");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

//                Verificando a página atual:
        if (request.getParameter("pagina") != null) {
            pagina = Integer.parseInt(request.getParameter("pagina"));
        }

        if (session.getAttribute("tipoUsuario").equals("empresa")) {
            empresas.add(empresaDAO.buscarPorId(((Empresa) session.getAttribute("empresa")).getId()));
        } else {
            //              Vendo se há algum filtro definido:
            if (tipoFiltro != null) {
                tipoFiltro = tipoFiltro.trim().toLowerCase();
//                     Verificando se há algum valor definido para o filtro:
                if (valorFiltro != null) {
                    valorFiltro = valorFiltro.trim().toLowerCase();
                    if (tipoFiltro.equals("id")) {
                        try {
                            id = Integer.parseInt(valorFiltro);
                            if (id > 0) {
                                empresa = empresaDAO.buscarPorId(id);
                                if (empresa != null) {
                                    empresas.add(empresa);
                                } else {
                                    request.setAttribute("empresas", new ArrayList<>());
                                    request.setAttribute("erro", "Nenhuma empresa foi encontrada nessa filtragem");
                                    request.getRequestDispatcher("WEB-INF/pages/empresas/verEmpresas.jsp")
                                            .forward(request, response);
                                    return;
                                }
                            } else {
                                request.setAttribute("erroValorFiltro", "Id do plano deve ser maior do que 0");
                            }
                        } catch (NumberFormatException | NullPointerException e) {
                            request.setAttribute("erroValorFiltro", "Id do plano deve ser um número");
                        }
                        //                   Indo para o filtro de nome:
                    } else if (tipoFiltro.equals("nome")) {
                        totalRegitros = empresaDAO.contarPorNome(RegrasBanco.nomeCapitalize(valorFiltro));
                        totalPaginas = Math.max(1, (int) Math.ceil(totalRegitros / 6.0));
                        pagina = Math.max(1, Math.min(pagina, totalPaginas));

                        empresas = totalRegitros > 0 ? empresaDAO.listarPorNome(pagina, limite, RegrasBanco.nomeCapitalize(valorFiltro)) : new ArrayList<>();

                        if (empresas.isEmpty()){
                            request.setAttribute("empresas", new ArrayList<>());
                            request.setAttribute("erro", "Nenhuma empresa foi encontrada nessa filtragem");
                            request.getRequestDispatcher("WEB-INF/pages/empresas/verEmpresas.jsp")
                                    .forward(request, response);
                            return;
                        }
                    } else if (tipoFiltro.equals("email")) {
                        //                Filtro de email:
                        totalRegitros = empresaDAO.contarPorEmail(valorFiltro);
                        totalPaginas = Math.max(1, (int) Math.ceil(totalRegitros / 6.0));
                        pagina = Math.max(1, Math.min(pagina, totalPaginas));

                        empresas = totalRegitros > 0 ? empresaDAO.listarPorEmail(pagina, limite, valorFiltro) : new ArrayList<>();

                        if (empresas.isEmpty()){
                            request.setAttribute("empresas", new ArrayList<>());
                            request.setAttribute("erro", "Nenhuma empresa foi encontrada nessa filtragem");
                            request.getRequestDispatcher("WEB-INF/pages/empresas/verEmpresas.jsp")
                                    .forward(request, response);
                            return;
                        }
                    } else if (tipoFiltro.equals("cnpj")) {
//                    Filtro de cnpj:
                        totalRegitros = empresaDAO.contarPorCNPJ(valorFiltro);
                        totalPaginas = Math.max(1, (int) Math.ceil(totalRegitros / 6.0));
                        pagina = Math.max(1, Math.min(pagina, totalPaginas));

                        empresas = totalRegitros > 0 ? empresaDAO.listarPorCNPJ(pagina, limite, valorFiltro) : new ArrayList<>();

                        if (empresas.isEmpty()){
                            request.setAttribute("empresas", new ArrayList<>());
                            request.setAttribute("erro", "Nenhuma empresa foi encontrada nessa filtragem");
                            request.getRequestDispatcher("WEB-INF/pages/empresas/verEmpresas.jsp")
                                    .forward(request, response);
                            return;
                        }
                    } else if (tipoFiltro.equals("mindtcadastro")) {
                        try {
                            // Converte o valor recebido (ex: "2025-11-01") para LocalDate
                            LocalDate dataFiltro = LocalDate.parse(valorFiltro);

                            // Conta quantos registros têm data >= a informada
                            totalRegitros = empresaDAO.contarPorMinDataCadastro(dataFiltro);
                            totalPaginas = Math.max(1, (int) Math.ceil(totalRegitros / 6.0));
                            pagina = Math.max(1, Math.min(pagina, totalPaginas));

                            // Lista as empresas dentro do intervalo
                            empresas = totalRegitros > 0 ? empresaDAO.listarPorMinDataCadastro(pagina, limite, dataFiltro) : new ArrayList<>();

                            if (empresas.isEmpty()){
                                request.setAttribute("empresas", new ArrayList<>());
                                request.setAttribute("erro", "Nenhuma empresa foi encontrada nessa filtragem");
                                request.getRequestDispatcher("WEB-INF/pages/empresas/verEmpresas.jsp")
                                        .forward(request, response);
                                return;
                            }
                        } catch (DateTimeParseException e) {
                            e.printStackTrace();
                            request.setAttribute("erroValorFiltro", "Use o formato YYYY-MM-DD.");
                            empresas = new ArrayList<>();
                        }
                    } else if (tipoFiltro.equals("maxdtcadastro")) {
                        try {
                            // Converte o valor recebido (ex: "2025-11-01") para LocalDate
                            LocalDate dataFiltro = LocalDate.parse(valorFiltro);

                            // Conta quantos registros têm data >= a informada
                            totalRegitros = empresaDAO.contarPorMaxDataCadastro(dataFiltro);
                            totalPaginas = Math.max(1, (int) Math.ceil(totalRegitros / 6.0));
                            pagina = Math.max(1, Math.min(pagina, totalPaginas));

                            // Lista as empresas dentro do intervalo
                            empresas = totalRegitros > 0 ? empresaDAO.listarPorMaxDataCadastro(pagina, limite, dataFiltro) : new ArrayList<>();

                            if (empresas.isEmpty()){
                                request.setAttribute("empresas", new ArrayList<>());
                                request.setAttribute("erro", "Nenhuma empresa foi encontrada nessa filtragem");
                                request.getRequestDispatcher("WEB-INF/pages/empresas/verEmpresas.jsp")
                                        .forward(request, response);
                                return;
                            }
                        } catch (DateTimeParseException e) {
                            e.printStackTrace();
                            request.setAttribute("erroValorFiltro", "Use o formato YYYY-MM-DD.");
                            empresas = new ArrayList<>();
                        }
                    } else {
                        request.setAttribute("erroValorFiltro", "Filtro não disponível");
                    }
                } else {
                    request.setAttribute("empresas", new ArrayList<>());
                    request.setAttribute("erroFiltro", "Defina um valor para o filtro");
                    request.getRequestDispatcher("WEB-INF/pages/empresas/verEmpresas.jsp")
                            .forward(request, response);
                    return;
                }

            } else {
                totalRegitros = empresaDAO.contar();
                totalPaginas = Math.max(1, (int) Math.ceil(totalRegitros / 6.0));

//              Garante que pagina está no intervalo válido [1, totalPaginas]
                pagina = Math.max(1, Math.min(pagina, totalPaginas));

                empresas = totalRegitros > 0 ? empresaDAO.listar(pagina, limite) : new ArrayList<>();
            }
        }

        request.setAttribute("empresas", empresas);
        request.setAttribute("tipoUsuario", session.getAttribute("tipoUsuario"));

//        Setando atributo de página atual:
        request.setAttribute("pagina", pagina);

//        Enviando retorno:
        request.getRequestDispatcher("WEB-INF/pages/empresas/verEmpresas.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
