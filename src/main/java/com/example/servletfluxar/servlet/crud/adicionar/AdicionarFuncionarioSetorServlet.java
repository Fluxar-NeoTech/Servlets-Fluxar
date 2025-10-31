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

@WebServlet(name = "AdicionarFuncionarioSetorServlet", value = "/AdicionarFuncionarioSetorServlet")
public class AdicionarFuncionarioSetorServlet extends HttpServlet {
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

        setores = setorDAO.listarNomesPorIdUnidade(idUnidade);

        if (setores.isEmpty()){
            request.setAttribute("erroUnidade", "Não há nenhum setor cadastrado");
            request.getRequestDispatcher("/ListarFuncionariosServlet")
                    .forward(request, response);
            return;
        }

        request.setAttribute("setores", setores);
        request.setAttribute("funcionario", session.getAttribute("funcionario"));

//        Redireciona para a página de adicionar setor:
        request.getRequestDispatcher("/WEB-INF/pages/funcionarios/adicionarFuncionarioSetor.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        String senha = request.getParameter("senha");
        String confirmarSenha = request.getParameter("confirmarSenha");
        int idSetor = 0;
        int senhaValida;
        HttpSession session = request.getSession();
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        List<Unidade> unidades;
        Funcionario funcionario = (Funcionario) session.getAttribute("funcionario");
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
            request.setAttribute("erroNumero", "Id da unidade deve ser um número");
            continuar = false;
        }

//        Validando senha:
        if (senha == null){
            request.setAttribute("erroSenha", "Defina uma senha para o administrador");
            continuar = false;
        } else {
            senhaValida = ValidacaoInput.validarSenha(senha);
            if (senhaValida != 0){
                if (senhaValida == 1){
                    request.setAttribute("erroSenha", "Senha deve ser menor do que 28 caracteres");
                }
                if (senhaValida == 2){
                    request.setAttribute("erroSenha", "Senha deve ser maior do que 8 caracteres");
                }
                if (senhaValida == 3){
                    request.setAttribute("erroSenha", "Senha deve ter letras maiúsculas");
                }
                if (senhaValida == 4){
                    request.setAttribute("erroSenha", "Senha deve ter letras minúsculas");
                }
                if (senhaValida == 5){
                    request.setAttribute("erroSenha", "Senha deve ter números");
                }
                continuar = false;
            } else {
                if (!senha.equals(confirmarSenha)){
                    request.setAttribute("erroConfirmarSenha", "Senha confirmada incorreta");
                    continuar = false;
                }
            }
        }

        if (!continuar){
            request.setAttribute("funcionario", funcionario);
            request.setAttribute("unidades",unidadeDAO.listarNomesPorIdEmpresa(((Empresa) session.getAttribute("empresa")).getId()));
            request.getRequestDispatcher("/WEB-INF/pages/funcionarios/adicionarFuncionarioSetor.jsp")
                    .forward(request, response);
            return;
        }

        funcionario.setIdSetor(idSetor);
        funcionario.setSenha(senha);

       if (funcionarioDAO.inserir(funcionario)){
           response.sendRedirect(request.getContextPath() + "/ListarFuncionariosServlet");
       } else {
           request.setAttribute("unidades",unidadeDAO.listarNomesPorIdEmpresa(((Empresa) session.getAttribute("empresa")).getId()));
           request.getRequestDispatcher("/WEB-INF/pages/funcionarios/adicionarFuncionarioSetor.jsp")
                   .forward(request, response);
           return;
       }
    }
}
