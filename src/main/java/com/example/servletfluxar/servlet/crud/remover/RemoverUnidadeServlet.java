package com.example.servletfluxar.servlet.crud.remover;

import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.dao.UnidadeDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Unidade;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "RemoverUnidadeServlet", value = "/RemoverUnidadeServlet")
public class RemoverUnidadeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        int id = 0;
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        EmpresaDAO empresaDAO = new EmpresaDAO();
        Unidade unidade = null;
        Empresa empresa = null;
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
            request.setAttribute("erro", "É necessário fazer login novamente");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
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
            empresa = empresaDAO.buscarPorId(unidade.getIdEmpresa());
            if (empresaLogada.getId() != empresa.getId()) {
                response.sendRedirect(request.getContextPath() + "/ListarUnidadesServlet");
                return;
            }
        } else {
            unidade = unidadeDAO.buscarPorId(id);
        }

        if (unidade!=null) {
            request.setAttribute("unidade", unidade);
            request.setAttribute("empresa", empresa);
            request.getRequestDispatcher("WEB-INF/pages/unidades/confirmarDelecao.jsp")
                    .forward(request, response);
        } else {
            request.setAttribute("unidades", new ArrayList<>());
            request.setAttribute("erro", "Unidade escolhida não existe");
            request.getRequestDispatcher("WEB-INF/pages/unidades/verUnidades.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        int id = 0;
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        EmpresaDAO empresaDAO = new EmpresaDAO();
        Unidade unidade = null;
        Empresa empresa = null;
        Empresa empresaLogada = null;
        Administrador administradorLogado = null;

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")) {
                response.sendRedirect(request.getContextPath() + "/ListarUnidadesServlet");
                return;
            } else {
                empresaLogada = (Empresa) session.getAttribute("empresa");
                request.setAttribute("empresa", (Empresa) session.getAttribute("empresa"));
            }
        } catch (NullPointerException npe) {
            request.setAttribute("erro", "É necessário fazer login novamente");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
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
        if(unidade!=null) {
            empresa = empresaDAO.buscarPorId(unidade.getIdEmpresa());
            if (empresaLogada.getId() == empresa.getId()) {
                unidadeDAO.deletarPorId(id);
            }
            response.sendRedirect(request.getContextPath() + "/ListarUnidadesServlet");
        } else {
            request.setAttribute("unidades", new ArrayList<>());
            request.setAttribute("erro", "Unidade escolhida não existe");
            request.getRequestDispatcher("WEB-INF/pages/unidades/verUnidades.jsp")
                    .forward(request, response);
        }
    }
}
