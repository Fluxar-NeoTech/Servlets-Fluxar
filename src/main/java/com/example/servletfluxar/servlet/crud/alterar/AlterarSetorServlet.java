package com.example.servletfluxar.servlet.crud.alterar;

import com.example.servletfluxar.dao.SetorDAO;
import com.example.servletfluxar.dao.UnidadeDAO;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Setor;
import com.example.servletfluxar.model.Unidade;
import com.example.servletfluxar.util.RegrasBanco;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AlterarSetorServlet", value = "/AlterarSetorServlet")
public class AlterarSetorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        List<Unidade> unidades = new ArrayList<>();
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        Empresa empresaLogada;
        SetorDAO setorDAO = new SetorDAO();
        Setor setor = new Setor();
        int id = 0;

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
            npe.printStackTrace();
            request.setAttribute("erro", "É necessário fazer login novamente");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        try{
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NullPointerException | NumberFormatException e){
            e.printStackTrace();
            request.setAttribute("erro", "O id do setor passado deve ser um número");
            return;
        }

        setor = setorDAO.buscarPorId(id);

        unidades = unidadeDAO.listarNomesPorIdEmpresa(((Empresa) session.getAttribute("empresa")).getId());

        if (unidades.get(0).getIdEmpresa() == empresaLogada.getId() || setor == null){
            response.sendRedirect(request.getContextPath()+"/ListarSetoresServlet");
            return;
        }

        request.setAttribute("setor", setor);
        request.setAttribute("unidades", unidades);
//        Redireciona para a página de adicionar setor:
        request.getRequestDispatcher("/WEB-INF/pages/setores/alterarSetor.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        String nome = request.getParameter("nome").trim();
        String descricao = request.getParameter("descricao").trim();
        HttpSession session = request.getSession();
        Empresa empresaLogada;
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        SetorDAO setorDAO = new SetorDAO();
        Unidade unidade;
        int id = 0;
        int idUnidade = 0;
        Setor setor = new Setor();
        boolean continuar = true;

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                response.sendRedirect(request.getContextPath()+"/ListarUnidadesServlet");
                return;
            } else {
                empresaLogada = (Empresa) session.getAttribute("empresa");
                request.setAttribute("empresa", empresaLogada);
            }
        } catch (NullPointerException npe){
            request.setAttribute("erroLogin", "É necessário fazer login novamente");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }

        try{
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NullPointerException | NumberFormatException e){
            System.out.println(e.getMessage());
            request.setAttribute("erro", e.getMessage());
            request.setAttribute("mendagem", "O id do setor passado deve ser um número inteiro");
            return;
        }

        setor = setorDAO.buscarPorId(id);
        if(setor==null){
            setor = new Setor();
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
        }
        nome = nome.toLowerCase();
        setor.setNome(RegrasBanco.nomeCapitalize(nome));

//        Validando id da unidade:
        try {
            idUnidade = Integer.parseInt(request.getParameter("idUnidade"));
            setor.setIdUnidade(idUnidade);
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
            request.getRequestDispatcher("/WEB-INF/pages/setores/alterarSetor.jsp")
                    .forward(request, response);
            return;
        }

        unidade = unidadeDAO.buscarPorId(setor.getIdUnidade());

        if (unidade.getIdEmpresa() == empresaLogada.getId()){
            response.sendRedirect(request.getContextPath()+"/ListarSetoresServlet");
            return;
        }

//        Enviando e vendo se há um retorno:
        if (setorDAO.alterar(setor)) {
            response.sendRedirect(request.getContextPath() + "/ListarSetoresServlet");
        } else {
            request.setAttribute("unidades",unidadeDAO.listarNomesPorIdEmpresa(((Empresa) session.getAttribute("empresa")).getId()));
            request.setAttribute("error", "Não foi possível alterar esse setor no momento. Tente novamente mais tarde...");
            request.getRequestDispatcher("/WEB-INF/pages/setores/alterarSetor.jsp")
                    .forward(request, response);
        }
    }
}
