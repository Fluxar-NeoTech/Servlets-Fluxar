package com.example.servletfluxar.servlet.crud.remover;

import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
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
        EmpresaDAO empresaDAO = new EmpresaDAO();
        Empresa empresa = null;
        String tipoUsuario;
        Empresa empresaLogada = null;
        Administrador administradorLogado = null;

        try {
            tipoUsuario = (String) session.getAttribute("tipoUsuario");
            request.setAttribute("tipoUsuario", tipoUsuario);
            if (tipoUsuario.equals("administrador")){
                administradorLogado = (Administrador) session.getAttribute("administrador");
                request.setAttribute("administrador", administradorLogado);
            } else {
                empresaLogada = (Empresa) session.getAttribute("empresa");
                request.setAttribute("empresa", empresaLogada);
            }
        } catch (NullPointerException npe){
            request.setAttribute("erroLogin", "É necessário fazer login novamente");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }

        try{
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException nfe){
            request.setAttribute("erro", nfe.getMessage());
            request.setAttribute("mensagem", "Ocorreu um erro ao procurar essa empresa");
            request.getRequestDispatcher("")
                    .forward(request, response);
            return;
        } catch (NullPointerException npe){
            response.sendRedirect(request.getContextPath()+"/ListarEmpresasServlet");
            return;
        }

        if (tipoUsuario.equals("empresa")) {
            if (empresaLogada.getId() == id) {
                empresa = empresaDAO.buscarPorId(id);
            } else {
                response.sendRedirect(request.getContextPath() + "/ListarEmpresasServlet");
                return;
            }
        } else {
            empresa = empresaDAO.buscarPorId(id);
        }

        request.setAttribute("empresa", empresa);
        request.getRequestDispatcher("WEB-INF/pages/empresas/confirmarDelecao.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        int id = 0;
        EmpresaDAO empresaDAO = new EmpresaDAO();
        String tipoUsuario;
        Empresa empresaLogada = null;
        Administrador administradorLogado = null;

        try {
            tipoUsuario = (String) session.getAttribute("tipoUsuario");
            request.setAttribute("tipoUsuario", tipoUsuario);
            if (tipoUsuario.equals("administrador")){
                administradorLogado = (Administrador) session.getAttribute("administrador");
                request.setAttribute("administrador", administradorLogado);
            } else {
                empresaLogada = (Empresa) session.getAttribute("empresa");
                request.setAttribute("empresa", empresaLogada);
            }
        } catch (NullPointerException npe){
            request.setAttribute("erroLogin", "É necessário fazer login novamente");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                request.setAttribute("administrador", (Administrador) session.getAttribute("administrador"));
            } else {
                request.setAttribute("empresa", (Empresa) session.getAttribute("empresa"));
            }
        } catch (NullPointerException npe){
            request.setAttribute("erroLogin", "É necessário fazer login novamente");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }

        if (tipoUsuario.equals("empresa")) {
            if (empresaLogada.getId() == id) {
                empresaDAO.deletarPorId(id);
            }
        } else {
            empresaDAO.deletarPorId(id);
        }

        response.sendRedirect(request.getContextPath() + "/ListarEmpresasServlet");
    }
}
