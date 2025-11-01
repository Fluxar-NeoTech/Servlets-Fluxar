package com.example.servletfluxar.servlet.crud.listar;

import com.example.servletfluxar.dao.AdministradorDAO;
import com.example.servletfluxar.dao.AssinaturaDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Assinatura;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.util.RegrasBanco;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ListarAdminsServlet", value = "/ListarAdminsServlet")
public class ListarAdminsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Declaração de variáveis:
        String tipoFiltro = request.getParameter("tipoFiltro");
        String valorFiltro = request.getParameter("valorFiltro");
        int pagina = 1;
        int limite = 6;
        int id = 0;
        int totalRegitros = 0;
        int totalPaginas = 0;
        HttpSession session = request.getSession(false);
        AdministradorDAO administradorDAO = new AdministradorDAO();
        List<Administrador> administradores = new ArrayList<>();
        Administrador administrador;

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                request.setAttribute("administrador", (Administrador) session.getAttribute("administrador"));
            } else {
                response.sendRedirect(request.getContextPath()+"/HomeServlet");
                return;
            }
        } catch (NullPointerException npe){
            request.setAttribute("erroLogin", "É necessário fazer login novamente");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }

//                Verificando a página atual:
        if (request.getParameter("pagina") != null) {
            pagina = Integer.parseInt(request.getParameter("pagina"));
        }
        if (tipoFiltro != null) {
            tipoFiltro = tipoFiltro.trim().toLowerCase();
//                     Verificando se há algum valor definido para o filtro:
            if (valorFiltro != null) {
                valorFiltro = valorFiltro.trim().toLowerCase();
//                Filtro de id:
                if (tipoFiltro.equals("id")){
                    try {
                        id = Integer.parseInt(valorFiltro);
                        if (id>0) {
                            administrador = administradorDAO.buscarPorId(id);
                            if (administrador != null){
                                administradores.add(administrador);
                            }
                        } else {
                            request.setAttribute("erroValorFiltro", "Id do admin deve ser maior do que 0");
                        }
                    } catch (NumberFormatException | NullPointerException e){
                        request.setAttribute("erroValorFiltro", "Id do admin deve ser um número");
                    }
//                   Indo para o filtro de nome:
                } else if (tipoFiltro.equals("nome")) {
                    totalRegitros = administradorDAO.contarPorNomeCompleto(RegrasBanco.nomeCapitalize(valorFiltro));
                    totalPaginas = Math.max(1, (int) Math.ceil(totalRegitros / 6.0));
                    pagina = Math.max(1, Math.min(pagina, totalPaginas));

                    administradores = totalRegitros > 0 ? administradorDAO.listarPorNomeCompleto(pagina, limite, RegrasBanco.nomeCapitalize(valorFiltro)) : new ArrayList<>();
//                Filtro de email:
                } else if (tipoFiltro.equals("email")) {
                    totalRegitros = administradorDAO.contarPorEmail(valorFiltro);
                    totalPaginas = Math.max(1, (int) Math.ceil(totalRegitros / 6.0));
                    pagina = Math.max(1, Math.min(pagina, totalPaginas));

                    administradores = totalRegitros > 0 ? administradorDAO.listarPorEmail(pagina, limite, valorFiltro) : new ArrayList<>();
                } else {
                    request.setAttribute("erroValorFiltro", "Filtro não disponível");
                }
            } else {
                request.setAttribute("erroFiltro", "Defina um valor para o filtro");
                request.getRequestDispatcher("WEB-INF/pages/administradores/verAdministradores.jsp")
                        .forward(request, response);
                return;
            }

        } else {
            totalRegitros = administradorDAO.contar();
            totalPaginas = Math.max(1, (int) Math.ceil(totalRegitros / 6.0));

//              Garante que pagina está no intervalo válido [1, totalPaginas]
            pagina = Math.max(1, Math.min(pagina, totalPaginas));

            administradores = totalRegitros > 0 ? administradorDAO.listar(pagina, limite) : new ArrayList<>();
        }

        request.setAttribute("administradores", administradores);
        request.setAttribute("tipoUsuario", session.getAttribute("tipoUsuario"));

//        Setando atributo de página atual:
        request.setAttribute("pagina", pagina);

//        Enviando retorno:
        request.getRequestDispatcher("WEB-INF/pages/administradores/verAdministradores.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
