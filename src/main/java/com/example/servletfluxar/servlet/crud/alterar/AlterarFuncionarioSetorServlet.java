package com.example.servletfluxar.servlet.crud.alterar;

import com.example.servletfluxar.dao.*;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Funcionario;
import com.example.servletfluxar.model.Setor;
import com.example.servletfluxar.model.Unidade;
import com.example.servletfluxar.util.ValidacaoInput;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "/AlterarFuncionarioSetorServlet", value = "/AlterarFuncionarioSetorServlet")
public class AlterarFuncionarioSetorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        List<Setor> setores = new ArrayList<>();
        AssinaturaDAO assinaturaDAO = new AssinaturaDAO();
        SetorDAO setorDAO = new SetorDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        PlanoDAO planoDAO = new PlanoDAO();
        int idUnidade = Integer.parseInt(request.getParameter("idUnidade"));
        Empresa empresaLogada;
        Funcionario funcionario;
        boolean continuar = true;

//        Setando atributo da request com o tipo do usuário
        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                response.sendRedirect(request.getContextPath()+"/ListarFuncionariosServlet");
                return;
            } else {
                empresaLogada = (Empresa) session.getAttribute("empresa");
                request.setAttribute("empresa", empresaLogada);
            }
//            Tratando exceção para caso não seja encontrado os dados na session:
        } catch (NullPointerException npe){
            request.setAttribute("erroLogin", "É necessário fazer login novamente");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }

        try {
            session.getAttribute("continuar");
        } catch (NullPointerException nullPointerException) {
            response.sendRedirect(request.getContextPath() + "/ListarFuncionariosServlet");
        }

        if(!continuar){
            request.setAttribute("mensagem", "Número de funcionários no seu plano atingido");
            request.getRequestDispatcher("/ListarFuncionariosServlet")
                    .forward(request, response);
            return;
        }

        try{
            funcionario = (Funcionario) session.getAttribute("funcionario");
        } catch (NullPointerException npe){
            response.sendRedirect(request.getContextPath()+"/ListarFuncionariosServlet");
            return;
        }
        setores = setorDAO.listarNomesPorIdUnidade(idUnidade);

        if (setores.isEmpty()){
            request.setAttribute("erroUnidade", "Não há nenhum setor cadastrado");
            request.getRequestDispatcher("/ListarFuncionariosServlet")
                    .forward(request, response);
            return;
        }

        request.setAttribute("setores", setores);
        request.setAttribute("funcionario", funcionario);

//        Redireciona para a página de adicionar setor:
        request.getRequestDispatcher("/WEB-INF/pages/funcionarios/alterarFuncionarioSetor.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        int idSetor = 0;
        String cargo = request.getParameter("cargo");
        HttpSession session = request.getSession();
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        Funcionario funcionario = (Funcionario) session.getAttribute("funcionario");
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        SetorDAO setorDAO = new SetorDAO();
        List<Setor> setores;
        boolean continuar = true;

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                response.sendRedirect(request.getContextPath()+"/ListarFuncionariosServlet");
                return;
            } else {
                request.setAttribute("empresa", (Empresa) session.getAttribute("empresa"));
            }
        } catch (NullPointerException npe){
            request.setAttribute("erroLogin", "É necessário fazer login novamente");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }

//        Validando id da unidade:
        try {
            idSetor = Integer.parseInt(request.getParameter("idSetor"));
        } catch (NullPointerException | NumberFormatException e){
            request.setAttribute("erroIdSetor", "Id do setor deve ser um número");
            continuar = false;
        }

//        Validando cargo:
        if (cargo != null) {
            cargo = cargo.trim();
            if (!cargo.equals("Analista") && !cargo.equals("Gestor")) {
                request.setAttribute("erroCargo", "Cargo inválido");
                continuar = false;
            } else {
                funcionario.setCargo(cargo);
            }
        } else {
            request.setAttribute("erroCargo", "Cargo deve ser escolhido");
            continuar = false;
        }

        if (!continuar){
            setores = setorDAO.listarNomesPorIdUnidade(setorDAO.buscarPorId(funcionario.getIdSetor()).getIdUnidade());

            request.setAttribute("setores", setores);
            request.setAttribute("funcionario", funcionario);
            request.setAttribute("unidades",unidadeDAO.listarNomesPorIdEmpresa(((Empresa) session.getAttribute("empresa")).getId()));
            request.getRequestDispatcher("/WEB-INF/pages/funcionarios/alterarFuncionarioSetor.jsp")
                    .forward(request, response);
            return;
        }

        funcionario.setIdSetor(idSetor);

       if (funcionarioDAO.alterar(funcionario)){
           response.sendRedirect(request.getContextPath() + "/ListarFuncionariosServlet");
       } else {
           setores = setorDAO.listarNomesPorIdUnidade(setorDAO.buscarPorId(funcionario.getIdSetor()).getIdUnidade());
           request.setAttribute("funcionario", funcionario);
           request.setAttribute("setores", setores);
           request.setAttribute("unidades",unidadeDAO.listarNomesPorIdEmpresa(((Empresa) session.getAttribute("empresa")).getId()));
           request.getRequestDispatcher("/WEB-INF/pages/funcionarios/alterarFuncionarioSetor.jsp")
                   .forward(request, response);
       }
    }
}