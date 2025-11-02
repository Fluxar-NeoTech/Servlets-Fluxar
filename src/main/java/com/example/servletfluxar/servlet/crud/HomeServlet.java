package com.example.servletfluxar.servlet.crud;

import com.example.servletfluxar.dao.*;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "HomeServlet", value = "/HomeServlet")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Declarando variáveis:
        HttpSession session = request.getSession();
        Empresa empresaLogada = null;
        Administrador administradorLogado = null;
        AssinaturaDAO assinaturaDAO = new AssinaturaDAO();
        EmpresaDAO empresaDAO = new EmpresaDAO();
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        SetorDAO setorDAO = new SetorDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        PlanoDAO planoDAO = new PlanoDAO();

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
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

        if (empresaLogada != null){
            request.setAttribute("tipoUsuario", "empresa");

            request.setAttribute("planoAssinado", planoDAO.buscarPorId(assinaturaDAO.buscarPorIdEmpresa(empresaLogada.getId()).getIdPlano()));
            request.setAttribute("numeroUnidades", unidadeDAO.contarPorIdEmpresa(empresaLogada.getId()));
            request.setAttribute("numeroSetores", setorDAO.contarPorIdEmpresa(empresaLogada.getId()));
            request.setAttribute("numeroFuncionarios", funcionarioDAO.contarPorIdEmpresa(empresaLogada.getId()));
            request.setAttribute("dataInicio", assinaturaDAO.buscarPorIdEmpresa(empresaLogada.getId()).getDtInicio());
            request.setAttribute("dataFim", assinaturaDAO.buscarPorIdEmpresa(empresaLogada.getId()).getDtFim());

        } else if (administradorLogado != null){
            request.setAttribute("tipoUsuario", "administrador");

            request.setAttribute("numeroEmpresas", empresaDAO.contar());
            request.setAttribute("numeroEmpresasAtivas", empresaDAO.contarPorEmpresaStatus('A'));
            request.setAttribute("numeroFuncionariosAtivos", funcionarioDAO.contarPorEmpresaStatus('A'));
            request.setAttribute("planoMaisAssinado",  planoDAO.buscarPlanoMaisVendido());
            request.setAttribute("numeroUnidadesAtivas", unidadeDAO.contarPorEmpresaStatus('A'));
            request.setAttribute("numeroSetoresAtivos", setorDAO.contarPorEmpresaStatus('A'));
        }

        request.getRequestDispatcher("WEB-INF/pages/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
