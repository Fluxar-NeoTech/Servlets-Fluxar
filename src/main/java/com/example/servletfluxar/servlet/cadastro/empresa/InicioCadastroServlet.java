package com.example.servletfluxar.servlet.cadastro.empresa;

import com.example.servletfluxar.dao.*;
import com.example.servletfluxar.model.Assinatura;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.util.RegrasBanco;
import com.example.servletfluxar.util.ValidacaoInput;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "InicioCadastroServlet", value = "/InicioCadastroServlet")
public class InicioCadastroServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//      Declarando variáveis:
        String nome;
        String cnpj;
        String email;
        boolean continuar = true;
        EmpresaDAO empresaDAO = new EmpresaDAO();
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        AdministradorDAO administradorDAO = new AdministradorDAO();
        HttpSession session = request.getSession();
        Empresa empresa = new Empresa();

//        Pegando Input do usuário:
        nome = request.getParameter("nome");
        cnpj = request.getParameter("cnpj");
        email = request.getParameter("email");

//        Validando nome:
        if (nome == null){
            request.setAttribute("erroNome", "Digite um nome");
            continuar = false;
        } else {
            nome = nome.trim().toLowerCase();
            nome = RegrasBanco.nomeCapitalize(nome);
        }

//      Validar e extrair CNPJ limpo:
        if (cnpj == null){
            request.setAttribute("erroCnpj", "Digite um cnpj");
            continuar = false;
        } else {
            cnpj = cnpj.trim().toLowerCase();
            if (ValidacaoInput.validarCNPJ(cnpj)) {
                cnpj = RegrasBanco.cnpj(cnpj);
            } else {
                // Formato inválido
                request.setAttribute("erroCNPJ", "Formato de CNPJ inválido");
                continuar = false;
            }
        }

//        Validando email:
        if (email == null){
            request.setAttribute("erroEmail", "Digite um email");
            continuar = false;
        } else {
            email = email.trim().toLowerCase();
            if (!ValidacaoInput.validarEmail(email)){
                request.setAttribute("erroEmail", "Formato de email inválido");
                continuar = false;
            }
        }

//        Se não for para continuar:
        if (!continuar){
            request.getRequestDispatcher("/pages/cadastro/inicioCadastro.jsp")
                    .forward(request, response);
            return;
        }

//        Verificando se nome já está cadastrado:
        if (empresaDAO.buscarPorNome(nome) != null){
            request.setAttribute("erroNome", "Nome já cadastrado");
            request.getRequestDispatcher("/pages/cadastro/inicioCadastro.jsp").forward(request, response);
        }

//        Verificando se cnpj já está em uso:
        if (empresaDAO.buscarPorCNPJ(cnpj) != null || unidadeDAO.buscarPorCnpj(cnpj) != null){
            request.setAttribute("erroCnpj", "Cnpj já cadastrado");
            request.getRequestDispatcher("/pages/cadastro/inicioCadastro.jsp").forward(request, response);
        }

//        Verificando se email já está em uso:
        if (empresaDAO.buscarPorEmail(email) != null || funcionarioDAO.buscarPorEmail(email) != null || administradorDAO.buscarPorEmail(email) != null){
            request.setAttribute("erroEmail", "Email já cadastrado");
            request.getRequestDispatcher("/pages/cadastro/inicioCadastro.jsp").forward(request, response);
        }

        empresa.setNome(nome);
        empresa.setCnpj(cnpj);
        empresa.setEmail(email);

        session.setAttribute("empresa", empresa);

        response.sendRedirect(request.getContextPath() + "/pages/cadastro/escolhaPlano.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
