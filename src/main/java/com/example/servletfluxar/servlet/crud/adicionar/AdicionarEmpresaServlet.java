package com.example.servletfluxar.servlet.crud.adicionar;

import com.example.servletfluxar.dao.AdministradorDAO;
import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.dao.PlanoDAO;
import com.example.servletfluxar.dao.UnidadeDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Plano;
import com.example.servletfluxar.util.RegrasBanco;
import com.example.servletfluxar.util.ValidacaoInput;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "AdicionarEmpresaServlet", value = "/AdicionarEmpresaServlet")
public class AdicionarEmpresaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        HttpSession session = request.getSession();

//        Setando atributo da request com o tipo do usuário
        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                request.setAttribute("administrador", (Administrador) session.getAttribute("administrador"));
            } else {
                response.sendRedirect(request.getContextPath()+"/ListarEmpresasServlet");
                return;
            }
//            Tratando exceção para caso não seja encontrado os dados na session:
        } catch (NullPointerException npe){
            request.setAttribute("erro", "É necessário fazer login novamente");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

//        Redireciona para a página de adicionar empresa:
        request.getRequestDispatcher("/WEB-INF/pages/empresas/adicionarEmpresa.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        String nome = request.getParameter("nome");
        String cnpj = request.getParameter("cnpj");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String confirmarSenha = request.getParameter("confirmarSenha");
        int senhaValida;
        HttpSession session = request.getSession();
        EmpresaDAO empresaDAO = new EmpresaDAO();
        AdministradorDAO administradorDAO = new AdministradorDAO();
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        Empresa empresa = new Empresa();
        boolean continuar = true;

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                request.setAttribute("administrador", (Administrador) session.getAttribute("administrador"));
            } else {
                response.sendRedirect(request.getContextPath()+"/ListarEmpresasServlet");
                return;
            }
        } catch (NullPointerException npe){
            request.setAttribute("erro", "É necessário fazer login novamente");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

//        Validando se nome é válido:
        if (nome == null){
            request.setAttribute("erroNome", "Insira um nome para a empresa");
            continuar = false;
        } else {
            nome = nome.trim().toLowerCase();
            nome = RegrasBanco.nomeCapitalize(nome);
            if (empresaDAO.buscarPorNome(nome) != null){
                request.setAttribute("erroNome", "Nome já está sendo usado");
                continuar = false;
            }
        }

//        Validando se CNPJ é válido:
        if (cnpj == null){
            request.setAttribute("erroCnpj", "Insira um cnpj para a empresa");
            continuar = false;
        } else {
            cnpj = cnpj.trim();
            if (ValidacaoInput.validarCNPJ(cnpj)){
                cnpj = RegrasBanco.cnpj(cnpj);
                if (empresaDAO.buscarPorCNPJ(cnpj) != null || unidadeDAO.buscarPorCnpj(cnpj) != null){
                    request.setAttribute("erroCnpj", "Cnpj já está cadastrado");
                    continuar = false;
                }
            } else {
                request.setAttribute("erroCnpj", "Formato de cnpj inválido");
                continuar = false;
            }
        }

//        Vaidando se email é válido:
        if (email == null){
            request.setAttribute("erroEmail", "Insira um email para a empresa");
            continuar = false;
        } else {
            email = email.trim().toLowerCase();
            if (ValidacaoInput.validarEmail(email)){
                if (empresaDAO.buscarPorEmail(email) != null || administradorDAO.buscarPorEmail(email) != null){
                    request.setAttribute("erroEmail", "Email já cadastrado");
                    continuar = false;
                }
            } else {
                request.setAttribute("erroEmail", "Formato de email inválido");
                continuar = false;
            }
        }

//        Validade se a senha é válida
        if (senha == null){
            request.setAttribute("erroSenha", "Defina uma senha para a empresa");
            continuar = false;
        } else {
            senha = senha.trim();
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
                if (!senha.equals(confirmarSenha.trim())){
                    request.setAttribute("erroConfirmarSenha", "Senha confirmada incorreta");
                    continuar = false;
                }
            }
        }

        if (!continuar){
            request.getRequestDispatcher("/WEB-INF/pages/empresas/adicionarEmpresa.jsp")
                    .forward(request, response);
            return;
        }

//        Setnando objeto empresa para adicionar:
        empresa.setNome(nome);
        empresa.setCnpj(cnpj);
        empresa.setEmail(email);
        empresa.setSenha(senha);

//        Enviando e vendo se há um retorno:
        if (empresaDAO.inserir(empresa)){
            response.sendRedirect(request.getContextPath() + "/ListarEmpresasServlet");
        }else {
            request.setAttribute("erro", "Não foi possível inserir uma empresa no momento. Tente novamente mais tarde...");
            request.getRequestDispatcher("/WEB-INF/pages/empresas/adicionarEmpresa.jsp")
                    .forward(request, response);
        }
    }
}
