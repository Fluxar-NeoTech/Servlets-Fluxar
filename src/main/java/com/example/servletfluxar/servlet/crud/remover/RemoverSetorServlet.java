package com.example.servletfluxar.servlet.crud.remover;

import com.example.servletfluxar.dao.SetorDAO;
import com.example.servletfluxar.dao.UnidadeDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Setor;
import com.example.servletfluxar.model.Unidade;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RemoverSetorServlet", value = "/RemoverSetorServlet")
public class RemoverSetorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        int id = 0;
        SetorDAO setorDAO = new SetorDAO();
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        Unidade unidade = new Unidade();
        Setor setor = new Setor();
        String tipoUsuario;
        Empresa empresaLogada = null;
        Administrador administradorLogado = null;

        try {
            tipoUsuario = (String) session.getAttribute("tipoUsuario");
            request.setAttribute("tipoUsuario", tipoUsuario);
            if (tipoUsuario.equals("administrador")) {
                response.sendRedirect(request.getContextPath()+"/ListarSetoresServlet");
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
            setor = setorDAO.buscarPorId(id);
            unidade = unidadeDAO.buscarPorId(setor.getIdUnidade());
            if (empresaLogada.getId() != unidade.getIdEmpresa()) {
                response.sendRedirect(request.getContextPath() + "/ListarSetoresServlet");
                return;
            }
        } else {
            setor = setorDAO.buscarPorId(id);
            unidade = unidadeDAO.buscarPorId(setor.getIdUnidade());
        }

        request.setAttribute("unidade", unidade);
        request.setAttribute("setor", setor);
        request.getRequestDispatcher("WEB-INF/pages/setores/confirmarDelecao.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        int id = 0;
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        SetorDAO setorDAO = new SetorDAO();
        Setor setor = new Setor();
        String tipoUsuario;
        Empresa empresaLogada = null;
        Administrador administradorLogado = null;

//        Verificando se usuário está logado e o tipo de usuário:
        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")) {
                response.sendRedirect(request.getContextPath() + "/ListarUnidadesServlet");
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

//        Verificando se o id foi passado corretamente:
        try{
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException nfe){
            request.setAttribute("erro", nfe.getMessage());
            request.setAttribute("mensagem", "Ocorreu um erro ao procurar esse setor");
            request.getRequestDispatcher("")
                    .forward(request, response);
            return;
        } catch (NullPointerException npe){
            request.setAttribute("erro", npe.getMessage());
            request.setAttribute("mensagem", "Ocorreu um erro ao procurar esse setor");
            request.getRequestDispatcher("")
                    .forward(request, response);
            return;
        }

        setor = setorDAO.buscarPorId(id);
        if (empresaLogada.getId() != unidadeDAO.buscarPorId(setor.getIdUnidade()).getIdEmpresa()) {
            response.sendRedirect(request.getContextPath() + "/ListarSetoresServlet");
            return;
        } else {
            setorDAO.deletarPorId(id);
        }

        response.sendRedirect(request.getContextPath() + "/ListarSetoresServlet");
    }
}
