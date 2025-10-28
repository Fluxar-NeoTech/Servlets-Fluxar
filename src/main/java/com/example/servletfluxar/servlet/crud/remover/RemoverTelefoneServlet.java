package com.example.servletfluxar.servlet.crud.remover;

import com.example.servletfluxar.dao.AdministradorDAO;
import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.dao.TelefoneDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Telefone;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RemoverTelefoneServlet", value = "/RemoverTelefoneServlet")
public class RemoverTelefoneServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        int id = 0;
        TelefoneDAO telefoneDAO = new TelefoneDAO();
        EmpresaDAO empresaDAO = new EmpresaDAO();
        Telefone telefone;
        Empresa empresa;

        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException nfe) {
            request.setAttribute("erro", nfe.getMessage());
            request.setAttribute("mensagem", "Id deve conter apenas números");
            request.getRequestDispatcher("")
                    .forward(request, response);
            return;
        } catch (NullPointerException npe) {
            request.setAttribute("erro", npe.getMessage());
            request.setAttribute("mensagem", "Ocorreu um erro ao procurar esse telefone");
            request.getRequestDispatcher("")
                    .forward(request, response);
            return;
        }

        telefone = telefoneDAO.buscarPorId(id);

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")) {
                response.sendRedirect(request.getContextPath() + "/ListarTelefonesServlet?id="+telefone.getIdEmpresa());
                return;
            }
        } catch (NullPointerException npe) {
            request.setAttribute("erroLogin", "É necessário fazer login novamente");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }

        empresa = empresaDAO.buscarPorId(telefone.getId());

        request.setAttribute("empresa", empresa);
        request.setAttribute("telefone", telefone);
        request.getRequestDispatcher("WEB-INF/pages/telefones/confirmarDelecao.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        int id = 0;
        TelefoneDAO telefoneDAO = new TelefoneDAO();
        Telefone telefone = null;

        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException nfe) {
            request.setAttribute("erro", nfe.getMessage());
            request.setAttribute("mensagem", "Ocorreu um erro ao procurar essa empresa");
            request.getRequestDispatcher("")
                    .forward(request, response);
            return;
        } catch (NullPointerException npe) {
            request.setAttribute("erro", npe.getMessage());
            request.setAttribute("mensagem", "Ocorreu um erro ao procurar essa empresa");
            request.getRequestDispatcher("")
                    .forward(request, response);
            return;
        }

        telefone = telefoneDAO.buscarPorId(id);

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")) {
                response.sendRedirect(request.getContextPath() + "/ListarTelefonesServlet?id="+telefone.getIdEmpresa());
                return;
            }
        } catch (NullPointerException npe) {
            request.setAttribute("erroLogin", "É necessário fazer login novamente");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }

        if (telefoneDAO.deletarPorId(id)) {
            response.sendRedirect(request.getContextPath() + "/ListarTelefonesServlet?id="+telefone.getIdEmpresa());
        } else {
            request.setAttribute("mensagem", "Ocorreu um erro ao deletar esse administrador, tente novamente mais tarde...");
            request.getRequestDispatcher("")
                    .forward(request, response);
        }
    }
}
