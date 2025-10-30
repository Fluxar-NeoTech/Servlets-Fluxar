package com.example.servletfluxar.servlet.crud.alterar;

import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.dao.UnidadeDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.util.RegrasBanco;
import com.example.servletfluxar.util.ValidacaoInput;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "AlterarEmpresaServlet", value = "/AlterarEmpresaServlet")
public class AlterarEmpresaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        EmpresaDAO empresaDAO = new EmpresaDAO();
        Empresa empresa;
        Empresa empresaLogada;

//        Setando atributo da request com o tipo do usuário
        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                response.sendRedirect(request.getContextPath()+"/ListarEmpresasServlet");
                return;
            } else {
                empresaLogada = (Empresa) session.getAttribute("empresa");
                request.setAttribute("administrador", empresaLogada);
            }
//            Tratando exceção para caso não seja encontrado os dados na session:
        } catch (NullPointerException npe){
            request.setAttribute("erroLogin", "É necessário fazer login novamente");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }

        request.setAttribute("empresa", empresaLogada);
//        Redireciona para a página de adicionar empresa:
        request.getRequestDispatcher("/WEB-INF/pages/empresas/alterarEmpresa.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        String nome = request.getParameter("nome").trim();
        String cnpj = request.getParameter("cnpj").trim();
        String email = request.getParameter("email").trim();
        HttpSession session = request.getSession();
        EmpresaDAO empresaDAO = new EmpresaDAO();
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        Empresa empresa = (Empresa) session.getAttribute("empresa");
        boolean continuar = true;

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                response.sendRedirect(request.getContextPath()+"/ListarEmpresasServlet");
                return;
            } else {
                request.setAttribute("empresa", (Empresa) session.getAttribute("empresa"));
            }
        } catch (NullPointerException npe){
            request.setAttribute("erroLogin", "É necessário fazer login novamente");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }

        request.setAttribute("nome", nome);
//        Validando se nome é válido:
        if (nome == null && nome.isEmpty()){
            request.setAttribute("erroNome", "Insira um nome para a empresa");
            continuar = false;
        } else {
            nome = RegrasBanco.nomeCapitalize(nome);
            if (empresaDAO.buscarPorNome(nome) != null && !empresa.getNome().equals(nome)){
                request.setAttribute("erroNome", "Nome já está sendo usado");
                continuar = false;
            }
            empresa.setNome(nome);
        }

        request.setAttribute("cnpj", cnpj);
//        Validando se CNPJ é válido:
        if (cnpj == null && cnpj.isEmpty()){
            request.setAttribute("erroCnpj", "Insira um cnpj para a empresa");
            continuar = false;
        } else {
            if (ValidacaoInput.validarCNPJ(cnpj)){
                cnpj = RegrasBanco.cnpj(cnpj);
                if ((empresaDAO.buscarPorCNPJ(cnpj) == null && unidadeDAO.buscarPorCnpj(cnpj) == null) || empresa.getCnpj().equals(cnpj)){
                    empresa.setCnpj(cnpj);
                } else {
                    request.setAttribute("erroCnpj", "Cnpj já está cadastrado");
                    continuar = false;
                }
            } else {
                request.setAttribute("erroCnpj", "Formato de cnpj inválido");
                continuar = false;
            }
        }

        request.setAttribute("email", email);
//        Vaidando se email é válido:
        if (email == null && email.isEmpty()){
            request.setAttribute("erroEmail", "Insira um email para a empresa");
            continuar = false;
        } else {
            if (ValidacaoInput.validarEmail(email)){
                if (empresaDAO.buscarPorEmail(email) == null || empresa.getEmail().equals(email)){

                } else {
                    request.setAttribute("erroEmail", "Email em uso");
                    continuar = false;
                }
                empresa.setEmail(email);
            } else {
                request.setAttribute("erroEmail", "Formato de email inválido");
                continuar = false;
            }
        }

        if (!continuar){
            request.setAttribute("empresa", empresa);
            request.getRequestDispatcher("/WEB-INF/pages/empresas/alterarEmpresa.jsp")
                    .forward(request, response);
            return;
        }

//        Enviando e vendo se há um retorno:
        if (empresaDAO.alterar(empresa)){
            response.sendRedirect(request.getContextPath() + "/ListarEmpresasServlet");
        }else {
            request.setAttribute("mensagem", "Não foi possível inserir uma empresa no momento. Tente novamente mais tarde...");
            request.getRequestDispatcher("")
                    .forward(request, response);
        }
    }
}
