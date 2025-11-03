package com.example.servletfluxar.servlet.crud.remover;

import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.dao.FuncionarioDAO;
import com.example.servletfluxar.dao.SetorDAO;
import com.example.servletfluxar.dao.UnidadeDAO;
import com.example.servletfluxar.model.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "RemoverFuncionarioServlet", value = "/RemoverFuncionarioServlet")
public class RemoverFuncionarioServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        int id = 0;
        SetorDAO setorDAO = new SetorDAO();
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        EmpresaDAO empresaDAO = new EmpresaDAO();
        Unidade unidade = new Unidade();
        Setor setor = new Setor();
        Funcionario funcionario = new Funcionario();
        Empresa empresa = new Empresa();
        String tipoUsuario;
        Empresa empresaLogada = null;

        try {
            tipoUsuario = (String) session.getAttribute("tipoUsuario");
            request.setAttribute("tipoUsuario", tipoUsuario);
            if (tipoUsuario.equals("administrador")) {
                response.sendRedirect(request.getContextPath()+"/ListarFuncionariosServlet");
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
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
            request.setAttribute("erro", "Id do funcionário deve ser um número");
            request.getRequestDispatcher("WEB-INF/pages/funcionarios/verFuncionarios.jsp")
                    .forward(request, response);
            return;
        }

        if (tipoUsuario.equals("empresa")) {
            funcionario = funcionarioDAO.buscarPorId(id);
            setor = setorDAO.buscarPorId(funcionario.getIdSetor());
            unidade = unidadeDAO.buscarPorId(setor.getIdUnidade());
            empresa = empresaDAO.buscarPorId(unidade.getIdEmpresa());
            if (empresaLogada.getId() != unidade.getIdEmpresa()) {
                response.sendRedirect(request.getContextPath() + "/ListarFuncionariosServlet");
                return;
            }
        } else {
            funcionario = funcionarioDAO.buscarPorId(id);
            setor = setorDAO.buscarPorId(funcionario.getIdSetor());
            unidade = unidadeDAO.buscarPorId(unidade.getIdEmpresa());
        }

        if (funcionario!=null) {
            request.setAttribute("empresa", empresa);
            request.setAttribute("funcionario", funcionario);
            request.setAttribute("unidade", unidade);
            request.setAttribute("setor", setor);
            request.getRequestDispatcher("WEB-INF/pages/funcionarios/confirmarDelecao.jsp")
                    .forward(request, response);
        } else {
            request.setAttribute("funcionarios", new ArrayList<>());
            request.setAttribute("erro", "Não existe funcionário com esse id");
            request.getRequestDispatcher("WEB-INF/pages/funcionarios/verFuncionarios.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        int id = 0;
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        SetorDAO setorDAO = new SetorDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        Funcionario funcionario = new Funcionario();
        Empresa empresaLogada = null;

//        Verificando se usuário está logado e o tipo de usuário:
        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")) {
                response.sendRedirect(request.getContextPath() + "/ListarFuncionariosServlet");
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

//        Verificando se o id foi passado corretamente:
        try{
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException | NullPointerException e){
            request.setAttribute("erro", "Id do funcionário deve ser um número inteiro");
            request.getRequestDispatcher("WEB-INF/pages/funcionarios/confirmarDelecao.jsp")
                    .forward(request, response);
            return;
        }

        funcionario = funcionarioDAO.buscarPorId(id);

        if (funcionario!=null) {
            if (empresaLogada.getId() != unidadeDAO.buscarPorId(setorDAO.buscarPorId(funcionario.getIdSetor()).getIdUnidade()).getIdEmpresa()) {
                response.sendRedirect(request.getContextPath() + "/ListarFuncionariosServlet");
            } else {
                funcionarioDAO.deletarPorId(id);
                response.sendRedirect(request.getContextPath() + "/ListarFuncionariosServlet");
            }
        } else {
            request.setAttribute("funcionarios", new ArrayList<>());
            request.setAttribute("erro", "Não existe funcionário com esse id");
            request.getRequestDispatcher("WEB-INF/pages/funcionarios/verFuncionarios.jsp")
                    .forward(request, response);
        }
    }
}
