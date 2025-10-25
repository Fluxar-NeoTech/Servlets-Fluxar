package com.example.servletfluxar.servlet.crud.remover;

import com.example.servletfluxar.dao.UnidadeDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Unidade;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RemoverUnidadeServlet", value = "/RemoverUnidadeServlet")
public class RemoverUnidadeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        int id = 0;
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        Unidade unidade = null;
        String tipoUsuario;
        Empresa empresaLogada = null;
        Administrador administradorLogado = null;

        try {
            tipoUsuario = (String) session.getAttribute("tipoUsuario");
            request.setAttribute("tipoUsuario", tipoUsuario);
            if (tipoUsuario.equals("administrador")) {
                response.sendRedirect(request.getContextPath()+"/ListarUnidadesServlet");
                return;
            } else {
                empresaLogada = (Empresa) session.getAttribute("empresa");
                request.setAttribute("empresa", empresaLogada);
            }
        } catch (NullPointerException npe) {
            request.setAttribute("erroLogin", "É necessário fazer login novamente");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }

        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException nfe) {
            request.setAttribute("erro", nfe.getMessage());
            request.setAttribute("mensagem", "Ocorreu um erro ao procurar essa empresa");
            request.getRequestDispatcher("")
                    .forward(request, response);
            return;
        } catch (NullPointerException npe) {
            response.sendRedirect(request.getContextPath() + "/ListarUnidadesServlet");
            return;
        }

        if (tipoUsuario.equals("empresa")) {
            unidade = unidadeDAO.buscarPorId(id);
            if (empresaLogada.getId() != unidade.getIdEmpresa()) {
                response.sendRedirect(request.getContextPath() + "/ListarUnidadesServlet");
                return;
            }
        } else {
            unidade = unidadeDAO.buscarPorId(id);
        }

        request.setAttribute("unidade", unidade);
        request.getRequestDispatcher("WEB-INF/pages/unidades/confirmarDelecao.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        int id = 0;
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        Unidade unidade = null;
        String tipoUsuario;
        Empresa empresaLogada = null;
        Administrador administradorLogado = null;

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")) {
                response.sendRedirect(request.getContextPath() + "/ListarUnidadesServlet");
                return;
            } else {
                request.setAttribute("empresa", (Empresa) session.getAttribute("empresa"));
            }
        } catch (NullPointerException npe) {
            request.setAttribute("erroLogin", "É necessário fazer login novamente");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }

        try{
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException nfe){
            request.setAttribute("erro", nfe.getMessage());
            request.setAttribute("mensagem", "Ocorreu um erro ao procurar essa unidade");
            request.getRequestDispatcher("")
                    .forward(request, response);
            return;
        } catch (NullPointerException npe){
            request.setAttribute("erro", npe.getMessage());
            request.setAttribute("mensagem", "Ocorreu um erro ao procurar essa unidade");
            request.getRequestDispatcher("")
                    .forward(request, response);
            return;
        }

        unidade = unidadeDAO.buscarPorId(id);
        if (empresaLogada.getId() != unidade.getIdEmpresa()) {
            response.sendRedirect(request.getContextPath() + "/ListarUnidadesServlet");
            return;
        } else {
            unidadeDAO.deletarPorId(id);
        }

        response.sendRedirect(request.getContextPath() + "/ListarUnidadesServlet");
    }
}
