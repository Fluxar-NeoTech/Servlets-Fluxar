package com.example.servletfluxar.servlet.crud.adicionar;

import com.example.servletfluxar.dao.SetorDAO;
import com.example.servletfluxar.dao.UnidadeDAO;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Setor;
import com.example.servletfluxar.model.Unidade;
import com.example.servletfluxar.util.RegrasBanco;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AdicionarSetorServlet", value = "/AdicionarSetorServlet")
public class AdicionarSetorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        List<Unidade> unidades = new ArrayList<>();
        UnidadeDAO unidadeDAO = new UnidadeDAO();

//        Setando atributo da request com o tipo do usuário
        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                response.sendRedirect(request.getContextPath()+"/ListarSetoresServlet");
                return;
            } else {
                request.setAttribute("empresa", (Empresa) session.getAttribute("empresa"));
            }
//            Tratando exceção para caso não seja encontrado os dados na session:
        } catch (NullPointerException npe){
            request.setAttribute("erro", "É necessário fazer login novamente");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        unidades = unidadeDAO.listarNomesPorIdEmpresa(((Empresa) session.getAttribute("empresa")).getId());

        request.setAttribute("unidades", unidades);
//        Redireciona para a página de adicionar setor:
        request.getRequestDispatcher("/WEB-INF/pages/setores/adicionarSetor.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        String nome = request.getParameter("nome").trim();
        String descricao = request.getParameter("descricao").trim();
        HttpSession session = request.getSession();
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        SetorDAO setorDAO = new SetorDAO();
        List<Unidade> unidades;
        Setor setor = new Setor();
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
            request.setAttribute("erro", "É necessário fazer login novamente");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

//        Validando se nome é válido:
        if (nome == null){
            request.setAttribute("erroNome", "Insira um nome para o setor");
            continuar = false;
        } else if (nome.length()>60) {
            request.setAttribute("erroNome", "Nome deve ter menos do que 60 caracteres");
            continuar = false;
        }else if (nome.length()<3) {
            request.setAttribute("erroNome", "Nome deve ter mais do que 3 caracteres");
            continuar = false;
        } else {
            nome = nome.toLowerCase();
            setor.setNome(RegrasBanco.nomeCapitalize(nome));
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

//        Enviando e vendo se há um retorno:
        if (setorDAO.inserir(setor)) {
            response.sendRedirect(request.getContextPath() + "/ListarSetoresServlet");
        } else {
            System.out.println("erro");
            request.setAttribute("unidades",unidadeDAO.listarNomesPorIdEmpresa(((Empresa) session.getAttribute("empresa")).getId()));
            request.setAttribute("mensagem", "Não foi possível inserir um setor no momento. Tente novamente mais tarde...");
            request.getRequestDispatcher("/WEB-INF/pages/setores/adicionarSetor.jsp")
                    .forward(request, response);
        }
    }
}
