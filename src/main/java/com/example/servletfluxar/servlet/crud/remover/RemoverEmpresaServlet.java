package com.example.servletfluxar.servlet.crud.remover;

import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RemoverEmpresaServlet", value = "/RemoverEmpresaServlet")
public class RemoverEmpresaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        int id = 0;
        EmpresaDAO empresaDAO = new EmpresaDAO();
        Empresa empresa = null;
        String tipoUsuario;
        Administrador administradorLogado = null;

        try {
            tipoUsuario = (String) session.getAttribute("tipoUsuario");
            request.setAttribute("tipoUsuario", tipoUsuario);
            if (tipoUsuario.equals("administrador")){
                administradorLogado = (Administrador) session.getAttribute("administrador");
                request.setAttribute("administrador", administradorLogado);
            } else {
                response.sendRedirect(request.getContextPath()+"/ListarEmpresasServlet");
                return;
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

        empresa = empresaDAO.buscarPorId(id);

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
        Administrador administradorLogado = null;
        Empresa empresaLogada = null;

        try {
            tipoUsuario = (String) session.getAttribute("tipoUsuario");
            request.setAttribute("tipoUsuario", tipoUsuario);
            if (tipoUsuario.equals("administrador")){
                administradorLogado = (Administrador) session.getAttribute("administrador");
                request.setAttribute("administrador", administradorLogado);
            } else {
                response.sendRedirect(request.getContextPath()+"/ListarEmpresasServlet");
                return;
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

        if (empresaDAO.deletarPorId(id)) {
            response.sendRedirect(request.getContextPath() + "/ListarEmpresasServlet");
        } else {
            request.setAttribute("mensagem", "Ocorreu um erro ao deletar essa empresa, " +
                    "tente novamente mais tarde...");
            request.getRequestDispatcher("")
                    .forward(request, response);
        }
    }
}