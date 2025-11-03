package com.example.servletfluxar.servlet.crud.alterar;

import com.example.servletfluxar.dao.*;
import com.example.servletfluxar.model.*;
import com.example.servletfluxar.util.RegrasBanco;
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

@WebServlet(name = "AlterarFuncionarioUnidadeServlet", value = "/AlterarFuncionarioUnidadeServlet")
public class AlterarFuncionarioUnidadeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        AssinaturaDAO assinaturaDAO = new AssinaturaDAO();
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        SetorDAO setorDAO = new SetorDAO();
        PlanoDAO planoDAO = new PlanoDAO();
        List<Unidade> unidades;
        List<Setor> setores;
        List<Funcionario> funcionarios;
        Funcionario funcionario;
        Plano plano;
        Empresa empresaLogada;
        int idPlano = 0;
        int idFuncionario = 0;
        boolean continuar = true;

//        Setando atributo da request com o tipo do usuário
        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")) {
                response.sendRedirect(request.getContextPath() + "/ListarSetoresServlet");
                return;
            } else {
                empresaLogada = (Empresa) session.getAttribute("empresa");
                request.setAttribute("empresa", empresaLogada);
            }
//            Tratando exceção para caso não seja encontrado os dados na session:
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            request.setAttribute("erro", "É necessário fazer login novamente");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        try {
            idFuncionario = Integer.parseInt(request.getParameter("id"));
        } catch (NullPointerException | NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("erro", "Id deve ser um número postivo");
            request.getRequestDispatcher("/WEB-INF/pages/funcionarios/alterarFuncionarioUnidade.jsp")
                    .forward(request, response);
            return;
        }

        funcionario = funcionarioDAO.buscarPorId(idFuncionario);

        if (!continuar) {
            request.getRequestDispatcher("/WEB-INF/pages/funcionarios/alterarFuncionarioUnidade.jsp")
                    .forward(request, response);
            return;
        }

        funcionarios = funcionarioDAO.listarPorIdEmpresa(1, funcionarioDAO.contarPorIdEmpresa(empresaLogada.getId()), empresaLogada.getId());

        continuar = false;

        for (Funcionario funcionario1 : funcionarios) {
            if (funcionario1.getId() == funcionario.getId()) {
                continuar = true;
                break;
            }
        }

        if (!continuar) {
            request.setAttribute("erro", "Você não tem acesso a esse funcionário");
            request.getRequestDispatcher("/WEB-INF/pages/funcionarios/alterarFuncionarioUnidade.jsp")
                    .forward(request, response);
            return;
        }

        unidades = unidadeDAO.listarNomesPorIdEmpresa(((Empresa) session.getAttribute("empresa")).getId());

        if (unidades.isEmpty()) {
            request.setAttribute("erro", "Não há nenhuma unidade cadastrada por sua empresa");
            request.getRequestDispatcher("/WEB-INF/pages/funcionarios/alterarFuncionarioUnidade.jsp")
                    .forward(request, response);
            return;
        }

        if (funcionario != null) {
            request.setAttribute("unidades", unidades);
            request.setAttribute("funcionario", funcionario);
            request.setAttribute("setor", setorDAO.buscarPorId(funcionario.getIdSetor()));

//        Redireciona para a página de adicionar setor:
            request.getRequestDispatcher("/WEB-INF/pages/funcionarios/alterarFuncionarioUnidade.jsp")
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
        String[] nomeCompleto = new String[2];
        String nomeInput = request.getParameter("nomeCompleto");
        String email = request.getParameter("email");
        int idUnidade = 0;
        int idFuncionario = 0;
        HttpSession session = request.getSession();
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        SetorDAO setorDAO = new SetorDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        List<Unidade> unidades;
        Funcionario funcionario;
        boolean continuar = true;

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")) {
                response.sendRedirect(request.getContextPath() + "/ListarUnidadesServlet");
                return;
            } else {
                request.setAttribute("empresa", (Empresa) session.getAttribute("empresa"));
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            request.setAttribute("erro", "É necessário fazer login novamente");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        try {
            idFuncionario = Integer.parseInt(request.getParameter("id"));
        } catch (NullPointerException | NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("erro", "Id deve ser um número postivo");
            request.getRequestDispatcher("/WEB-INF/pages/funcionarios/alterarFuncionarioUnidade.jsp")
                    .forward(request, response);
            return;
        }

        funcionario = funcionarioDAO.buscarPorId(idFuncionario);
        funcionario.setId(idFuncionario);

//        Validando se nome é válido:
        if (nomeInput == null) {
            request.setAttribute("erroNome", "Insira um nome para o funcionário");
            continuar = false;
        } else if (nomeInput.length() > 200) {
            request.setAttribute("erroNome", "Nome deve ter menos do que 200 caracteres");
            continuar = false;
        } else if (nomeInput.length() < 3) {
            request.setAttribute("erroNome", "Nome deve ter mais do que 3 caracteres");
            continuar = false;
        } else {
            nomeInput = nomeInput.trim().toLowerCase();
            nomeCompleto = RegrasBanco.separarNomeCompleto(nomeInput);
        }
        funcionario.setNome(nomeCompleto[0]);
        funcionario.setSobrenome(nomeCompleto[1]);

//        Validando email do usuário:
        if (email == null) {
            request.setAttribute("erroEmail", "Insira um email para o administrador");
            continuar = false;
        } else {
            email = email.trim().toLowerCase();
            if (!ValidacaoInput.validarEmail(email)) {
                request.setAttribute("erroEmail", "Formato de email inválido");
                continuar = false;
            }
        }
        funcionario.setEmail(email);

//        Validando id da unidade:
        try {
            idUnidade = Integer.parseInt(request.getParameter("idUnidade"));
        } catch (NullPointerException | NumberFormatException e) {
            request.setAttribute("erroIdUnidade", "Id da unidade deve ser um número");
            continuar = false;
        }

        if (setorDAO.listarNomesPorIdUnidade(idUnidade).isEmpty()) {
            request.setAttribute("erroIdUnidade", "Essa unidade não possui setores");
            continuar = false;
        }

        if (!continuar) {
            unidades = unidadeDAO.listarNomesPorIdEmpresa(((Empresa) session.getAttribute("empresa")).getId());

            if (unidades.isEmpty()) {
                request.setAttribute("erro", "Não há nenhuma unidade cadastrada para essa empresa");
                request.getRequestDispatcher("/WEB-INF/pages/funcionarios/adicionarFuncionarioUnidade.jsp")
                        .forward(request, response);
                return;
            }

            request.setAttribute("funcionario", funcionario);
            request.setAttribute("unidades", unidades);
            request.getRequestDispatcher("/WEB-INF/pages/funcionarios/adicionarFuncionarioUnidade.jsp")
                    .forward(request, response);
            return;
        }

        if (funcionario!=null) {
            session.setAttribute("funcionario", funcionario);
            session.setAttribute("continuar", continuar);

            response.sendRedirect(request.getContextPath() + "/AlterarFuncionarioSetorServlet?idUnidade=" + idUnidade);
        } else {
            request.setAttribute("funcionarios", new ArrayList<>());
            request.setAttribute("erro", "Não existe funcionário com esse id");
            request.getRequestDispatcher("WEB-INF/pages/funcionarios/verFuncionarios.jsp")
                    .forward(request, response);
        }
    }
}
