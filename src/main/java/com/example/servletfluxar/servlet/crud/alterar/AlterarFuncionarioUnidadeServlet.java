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
        List<Unidade> unidades = new ArrayList<>();
        AssinaturaDAO assinaturaDAO = new AssinaturaDAO();
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        SetorDAO setorDAO = new SetorDAO();
        PlanoDAO planoDAO = new PlanoDAO();
        List<Funcionario> funcionarios = new ArrayList<>();
        List<Setor> setores = new ArrayList<>();
        int idPlano = 0;
        int idFuncionario = 0;
        Funcionario funcionario;
        Plano plano;
        Empresa empresaLogada;
        boolean continuar = true;

//        Setando atributo da request com o tipo do usuário
        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                response.sendRedirect(request.getContextPath()+"/ListarSetoresServlet");
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
            idFuncionario = Integer.parseInt(request.getParameter("id"));
        } catch (NullPointerException | NumberFormatException e){
            System.out.println(e.getMessage());
            request.setAttribute("erro", e.getMessage());
            request.setAttribute("mensagem", "Id deve ser um número postivo");
            request.getRequestDispatcher("/WEB-INF/pages/funcionarios/alterarFuncionarioUnidade.jsp")
                    .forward(request, response);
            return;
        }

        funcionario = funcionarioDAO.buscarPorId(idFuncionario);

        idPlano = assinaturaDAO.buscarPorIdEmpresa(empresaLogada.getId()).getIdPlano();

        plano = planoDAO.buscarPorId(idPlano);

        if (plano.getNome().equals("Essential")){
            if (funcionarioDAO.contarPorIdEmpresa(empresaLogada.getId()) >= 3){
                continuar = false;
            }
        } else if (plano.getNome().equals("Profissional")) {
            if (funcionarioDAO.contarPorIdEmpresa(empresaLogada.getId()) >= 10){
                continuar = false;
            }
        } else if (plano.getNome().equals("Enterprise")){
            continuar = true;
        }

        funcionarios = funcionarioDAO.listarPorIdEmpresa(1, funcionarioDAO.contarPorIdEmpresa(empresaLogada.getId()), empresaLogada.getId());

        if(!funcionarios.contains(funcionario)){
            request.setAttribute("mensagem", "Você não possui acesso a esse funcionário");
            continuar = false;
        }

        if(!continuar){
            request.getRequestDispatcher("/WEB-INF/pages/funcionarios/alterarFuncionarioUnidade.jsp")
                    .forward(request, response);
            return;
        }

        continuar = false;

        for (Funcionario funcionario1 : funcionarios) {
            if (funcionario1.getId() == funcionario.getId()) {
                continuar = true;
                break;
            }
        }

        if (!continuar){
            request.setAttribute("mensagem", "Você não tem acesso a esse funcionário");
            request.getRequestDispatcher("/WEB-INF/pages/funcionarios/alterarFuncionarioUnidade.jsp")
                    .forward(request, response);
            return;
        }

        unidades = unidadeDAO.listarNomesPorIdEmpresa(((Empresa) session.getAttribute("empresa")).getId());
        setores = setorDAO.listarNomesPorIdEmpresa(((Empresa)session.getAttribute("empresa")).getId());

        request.setAttribute("unidades", unidades);
        request.setAttribute("setores", setores);
        request.setAttribute("funcionario", funcionario);

//        Redireciona para a página de adicionar setor:
        request.getRequestDispatcher("/WEB-INF/pages/funcionarios/alterarFuncionarioUnidade.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        String[] nomeCompleto = new String[2];
        String nomeInput = request.getParameter("nomeCompleto").trim();
        String email = request.getParameter("email").trim();
        String cargo = request.getParameter("cargo").trim();
        int idUnidade = 0;
        HttpSession session = request.getSession();
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        SetorDAO setorDAO = new SetorDAO();
        List<Unidade> unidades;
        Funcionario funcionario = new Funcionario();
        boolean continuar = true;

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                response.sendRedirect(request.getContextPath()+"/ListarUnidadesServlet");
                return;
            } else {
                request.setAttribute("empresa", (Empresa) session.getAttribute("empresa"));
            }
        } catch (NullPointerException npe){
            request.setAttribute("erroLogin", "É necessário fazer login novamente");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }

//        Validando se nome é válido:
        if (nomeInput == null){
            request.setAttribute("erroNome", "Insira um nome para o funcionário");
            continuar = false;
        } else if (nomeInput.length()>200) {
            request.setAttribute("erroNome", "Nome deve ter menos do que 200 caracteres");
            continuar = false;
        }else if (nomeInput.length()<3) {
            request.setAttribute("erroNome", "Nome deve ter mais do que 3 caracteres");
            continuar = false;
        } else {
            nomeInput = nomeInput.toLowerCase();
            nomeCompleto = RegrasBanco.separarNomeCompleto(nomeInput);
        }
        funcionario.setNome(nomeCompleto[0]);
        funcionario.setSobrenome(nomeCompleto[1]);

//        Validando email do usuário:
        if (email == null){
            request.setAttribute("erroEmail", "Insira um email para o administrador");
            continuar = false;
        } else {
            if (!ValidacaoInput.validarEmail(email)) {
                request.setAttribute("erroEmail", "Formato de email inválido");
                continuar = false;
            }
        }
        funcionario.setEmail(email);

//        Validando cargo:
        if (!cargo.equals("Analista") && !cargo.equals("Gestor")){
            request.setAttribute("erroCargo", "Cargo inválido");
            continuar = false;
        }
        funcionario.setCargo(cargo);

//        Validando id da unidade:
        try {
            idUnidade = Integer.parseInt(request.getParameter("idUnidade"));
        } catch (NullPointerException | NumberFormatException e){
            request.setAttribute("erroIdUnidade", "Id da unidade deve ser um número");
            continuar = false;
        }

        if (setorDAO.listarNomesPorIdUnidade(idUnidade).isEmpty()){
            request.setAttribute("erroIdUnidade", "Essa unidade não possui setores");
            continuar = false;
        }

        if (!continuar){
            request.setAttribute("funcionario", funcionario);
            request.setAttribute("unidades",unidadeDAO.listarNomesPorIdEmpresa(((Empresa) session.getAttribute("empresa")).getId()));
            request.getRequestDispatcher("/WEB-INF/pages/funcionarios/adicionarFuncionarioUnidade.jsp")
                    .forward(request, response);
            return;
        }

        session.setAttribute("funcionario", funcionario);
        session.setAttribute("continuar", continuar);

        response.sendRedirect(request.getContextPath() + "/AlterarFuncionarioSetorServlet?idUnidade="+idUnidade);
    }
}
