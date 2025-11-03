package com.example.servletfluxar.servlet.crud.adicionar;

import com.example.servletfluxar.dao.*;
import com.example.servletfluxar.model.*;
import com.example.servletfluxar.util.RegrasBanco;
import com.example.servletfluxar.util.ValidacaoInput;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AdicionarFuncionarioUnidadeServlet", value = "/AdicionarFuncionarioUnidadeServlet")
public class AdicionarFuncionarioUnidadeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        List<Unidade> unidades = new ArrayList<>();
        AssinaturaDAO assinaturaDAO = new AssinaturaDAO();
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        PlanoDAO planoDAO = new PlanoDAO();
        int idPlano = 0;
        Plano plano;
        Empresa empresaLogada;
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
            request.setAttribute("erro", "É necessário fazer login novamente");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        idPlano = assinaturaDAO.buscarPorIdEmpresa(empresaLogada.getId()).getIdPlano();

        plano = planoDAO.buscarPorId(idPlano);

        if (plano.getNome().equals("Essential")) {
            if (funcionarioDAO.contarPorIdEmpresa(empresaLogada.getId()) >= 3) {
                continuar = false;
            }
        } else if (plano.getNome().equals("Profissional")) {
            if (funcionarioDAO.contarPorIdEmpresa(empresaLogada.getId()) >= 10) {
                continuar = false;
            }
        } else if (plano.getNome().equals("Enterprise")) {
            continuar = true;
        }

        if (!continuar) {
            request.setAttribute("erro", "Número máximo de funcionários no seu plano atingido");
            request.getRequestDispatcher("/WEB-INF/pages/funcionarios/adicionarFuncionarioUnidade.jsp")
                    .forward(request, response);
            return;
        }

        unidades = unidadeDAO.listarNomesPorIdEmpresa(((Empresa) session.getAttribute("empresa")).getId());

        if (unidades.isEmpty()) {
            request.setAttribute("erro", "Não há nenhuma undade cadastrada para essa empresa");
            request.getRequestDispatcher("/WEB-INF/pages/funcionarios/adicionarFuncionarioSetor.jsp")
                    .forward(request, response);
            return;
        }

        request.setAttribute("unidades", unidades);

//        Redireciona para a página de adicionar setor:
        request.getRequestDispatcher("/WEB-INF/pages/funcionarios/adicionarFuncionarioUnidade.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        String[] nomeCompleto = new String[2];
        String nomeInput = request.getParameter("nomeCompleto");
        String email = request.getParameter("email");
        String cargo = request.getParameter("cargo");
        int idUnidade = 0;
        HttpSession session = request.getSession();
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        SetorDAO setorDAO = new SetorDAO();
        List<Unidade> unidades;
        Funcionario funcionario = new Funcionario();
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
            request.setAttribute("erro", "É necessário fazer login novamente");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

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

//        Validando id da unidade:
        try {
            idUnidade = Integer.parseInt(request.getParameter("idUnidade"));
        } catch (NullPointerException | NumberFormatException e) {
            request.setAttribute("erro", "Id da unidade deve ser um número");
            continuar = false;
        }

        if (setorDAO.listarNomesPorIdUnidade(idUnidade).isEmpty()) {
            request.setAttribute("erro", "Essa unidade não possui setores");
            continuar = false;
        }

        if (!continuar) {
            unidades = unidadeDAO.listarNomesPorIdEmpresa(((Empresa) session.getAttribute("empresa")).getId());

            if (unidades.isEmpty()) {
                request.setAttribute("unidades", unidades);
                request.setAttribute("funcionario", funcionario);
                request.setAttribute("erro", "Não há nenhuma unidade cadastrada para essa empresa");
                request.getRequestDispatcher("/WEB-INF/pages/funcionarios/adicionarFuncionarioUnidade.jsp")
                        .forward(request, response);
                return;
            }

            request.setAttribute("unidades", unidades);
            request.setAttribute("funcionario", funcionario);
            request.getRequestDispatcher("/WEB-INF/pages/funcionarios/adicionarFuncionarioUnidade.jsp")
                    .forward(request, response);
            return;
        }

        session.setAttribute("funcionario", funcionario);
        session.setAttribute("continuar", continuar);

        response.sendRedirect(request.getContextPath() + "/AdicionarFuncionarioSetorServlet?idUnidade=" + idUnidade);
    }
}