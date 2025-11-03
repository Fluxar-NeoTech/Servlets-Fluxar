package com.example.servletfluxar.servlet.crud.remover;

import com.example.servletfluxar.dao.AssinaturaDAO;
import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Assinatura;
import com.example.servletfluxar.model.Empresa;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "RemoverAssinaturaServlet", value = "/RemoverAssinaturaServlet")
public class RemoverAssinaturaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        int id = 0;
        EmpresaDAO empresaDAO = new EmpresaDAO();
        AssinaturaDAO assinaturaDAO = new AssinaturaDAO();
        Empresa empresa = null;
        Assinatura assinatura = null;

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                request.setAttribute("administrador", (Administrador) session.getAttribute("administrador"));
            } else {
                response.sendRedirect(request.getContextPath()+"/ListarAssinaturasServlet");
            }
        } catch (NullPointerException npe){
            request.setAttribute("erro", "É necessário fazer login novamente");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        try{
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
            request.setAttribute("erro", "Id da assinatura deve ser um número inteiro");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        assinatura = assinaturaDAO.buscarPorId(id);

        empresa = empresaDAO.buscarPorId(assinatura.getIdEmpresa());

        if (assinatura!= null) {
            request.setAttribute("empresa", empresa);
            request.setAttribute("assinatura", assinatura);
            request.getRequestDispatcher("WEB-INF/pages/assinaturas/confirmarDelecao.jsp")
                    .forward(request, response);
        } else {
            request.setAttribute("assinaturas", new ArrayList<>());
            request.setAttribute("erro", "Não existe uma assinatura com esse id");
            request.getRequestDispatcher("WEB-INF/pages/assinaturas/verAssinaturas.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        int id = 0;
        EmpresaDAO empresaDAO = new EmpresaDAO();
        AssinaturaDAO assinaturaDAO = new AssinaturaDAO();

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                request.setAttribute("administrador", (Administrador) session.getAttribute("administrador"));
            } else {
                response.sendRedirect(request.getContextPath()+"/ListarAssinaturasServlet");
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
            request.setAttribute("erro", "Id da assinatura deve ser um número inteiro");
            request.getRequestDispatcher("WEB-INF/pages/assinaturas/confirmarDelecao.jsp")
                    .forward(request, response);
            return;
        }

        if (assinaturaDAO.buscarPorIdEmpresa(id)!=null) {
            if (empresaDAO.deletarPorId(assinaturaDAO.buscarPorId(id).getIdEmpresa()) || assinaturaDAO.deletarPorId(id)) {
                response.sendRedirect(request.getContextPath() + "/ListarAssinaturasServlet");
            } else {
                request.setAttribute("assinaturas", new ArrayList<>());
                request.setAttribute("erro", "Ocorreu um erro ao deletar essa assinatura, tente novamente mais tarde...");
                request.getRequestDispatcher("WEB-INF/pages/assinaturas/verAssinaturas.jsp")
                        .forward(request, response);
            }
        }else {
            request.setAttribute("assinaturas", new ArrayList<>());
            request.setAttribute("erro", "Não existe uma assinatura com esse id");
            request.getRequestDispatcher("WEB-INF/pages/assinaturas/verAssinaturas.jsp")
                    .forward(request, response);
        }
    }
}