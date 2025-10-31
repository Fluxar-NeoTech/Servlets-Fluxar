package com.example.servletfluxar.servlet.crud.adicionar;

import com.example.servletfluxar.dao.AssinaturaDAO;
import com.example.servletfluxar.dao.SetorDAO;
import com.example.servletfluxar.dao.UnidadeDAO;
import com.example.servletfluxar.model.*;
import com.example.servletfluxar.util.RegrasBanco;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AdicionarFuncionarioServlet", value = "/AdicionarFuncionarioServlet")
public class AdicionarFuncionarioServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        List<Unidade> unidades = new ArrayList<>();
        AssinaturaDAO assinaturaDAO = new AssinaturaDAO();
        int idPlano = 0;
        Plano plano
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        Empresa empresaLogada;

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

        idPlano = assinaturaDAO.buscarPorIdEmpresa(empresaLogada.getId()).getIdPlano();

        if ()

        unidades = unidadeDAO.listarNomesPorIdEmpresa(((Empresa) session.getAttribute("empresa")).getId());

        request.setAttribute("unidades", unidades);
//        Redireciona para a página de adicionar setor:
        request.getRequestDispatcher("/WEB-INF/pages/funcionarios/alterarFuncionario.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        String[] nomeCompleto = new String[2];
        String nomeInput = request.getParameter("nomeCompleto");
        String email;
        HttpSession session = request.getSession();
        UnidadeDAO unidadeDAO = new UnidadeDAO();
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
            request.setAttribute("erroNome", "Insira um nome para o setor");
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

//        Validando id da unidade:
        try {
            setor.setIdUnidade(Integer.parseInt(request.getParameter("idUnidade")));
        } catch (NullPointerException | NumberFormatException e){
            request.setAttribute("erroNumero", "Id da unidade deve ser um número");
            continuar = false;
        }

//        Validando descrição:
        if (descricao == null || descricao.equals("")){
            descricao = "Sem descrição";
        } else if (descricao.length() < 3){
            request.setAttribute("erroDescricao", "Descrição deve ser maior do que 3 caracteres");
            continuar = false;
        }
        setor.setDescricao(descricao.trim());

        if (!continuar){
            request.setAttribute("setor", setor);
            request.setAttribute("unidades",unidadeDAO.listarNomesPorIdEmpresa(((Empresa) session.getAttribute("empresa")).getId()));
            request.getRequestDispatcher("/WEB-INF/pages/setores/adicionarSetor.jsp")
                    .forward(request, response);
            return;
        }
    }
}
