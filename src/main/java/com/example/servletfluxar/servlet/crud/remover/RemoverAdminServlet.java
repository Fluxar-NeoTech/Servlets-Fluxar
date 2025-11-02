package com.example.servletfluxar.servlet.crud.remover;

import com.example.servletfluxar.dao.AdministradorDAO;
import com.example.servletfluxar.model.Administrador;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RemoverAdminServlet", value = "/RemoverAdminServlet")
public class RemoverAdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        int id = 0;
        AdministradorDAO administradorDAO = new AdministradorDAO();
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
            npe.printStackTrace();
            request.setAttribute("erro", "É necessário fazer login novamente");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        try{
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Ocorreu um erro ao procurar esse administrador");
            request.getRequestDispatcher("")
                    .forward(request, response);
            return;
        }

        administrador = administradorDAO.buscarPorId(id);

        if (administradorDAO.contar() > 1 && administrador != null){
            request.setAttribute("administrador", administrador);
            request.getRequestDispatcher("WEB-INF/pages/administradores/confirmarDelecao.jsp")
                    .forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath()+"/ListarAdminsServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        int id = 0;
        AdministradorDAO administradorDAO = new AdministradorDAO();

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                request.setAttribute("administrador", (Administrador) session.getAttribute("administrador"));
            } else {
                response.sendRedirect(request.getContextPath() + "/HomeServlet");
                return;
            }
        } catch (NullPointerException npe){
            request.setAttribute("erro", "É necessário fazer login novamente");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        try{
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException | NullPointerException e){
            e.printStackTrace();
            request.setAttribute("erro", "Id deve ser um número inteiro");
            request.getRequestDispatcher("WEB-INF/pages/administradores/confirmarDelecao.jsp")
                    .forward(request, response);
            return;
        }

        if (administradorDAO.buscarPorId(id) != null) {
            if (administradorDAO.deletarPorId(id)) {
                response.sendRedirect(request.getContextPath() + "/ListarAdminsServlet");
            } else {
                request.setAttribute("erro", "Ocorreu um erro ao deletar esse administrador, tente novamente mais tarde...");
                request.getRequestDispatcher("WEB-INF/pages/administradores/confirmarDelecao.jsp")
                        .forward(request, response);
            }
        }else {
            request.setAttribute("administrador", administradorDAO.buscarPorId(id));
            request.setAttribute("erro", "Esse administrador não existe");
            request.getRequestDispatcher("WEB-INF/pages/administradores/confirmarDelecao.jsp")
                    .forward(request, response);
        }
    }
}