package com.example.servletfluxar.servlet.cadastro.empresa;

import com.example.servletfluxar.dao.AssinaturaDAO;
import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.model.Assinatura;
import com.example.servletfluxar.model.Empresa;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "NomeCNPJEmpresaServlet", value = "/NomeCNPJEmpresaServlet")
public class NomeCNPJEmpresaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//      Declarando variáveis:
        String nomeInput;
        String CNPJInput;
        String CNPJ = null;
        EmpresaDAO empresaDAO = new EmpresaDAO();
        Empresa empresaNome;
        Empresa empresaCNPJ;
        AssinaturaDAO assinaturaDAO = new AssinaturaDAO();
        Assinatura assinatura;
        HttpSession session = request.getSession();

//        Pegando Input do usuário:
        nomeInput = request.getParameter("nome").trim();
        CNPJInput = request.getParameter("CNPJ").trim();

// Validar e extrair CNPJ limpo:
        if (CNPJInput.matches("\\d{2}[\\. ]?\\d{3}[\\. ]?\\d{3}/\\d{4}[- ]?\\d{2}")) {
            // Extrai só os dígitos, sem pontuação
            CNPJ = CNPJInput.replaceAll("[^0-9]", "");

        } else {
            // Formato inválido
            request.setAttribute("erroCNPJ", "Formato de CNPJ inválido");
            request.getRequestDispatcher("/cadastro/cnpjNomeEmpresa/cadastro.jsp").forward(request, response);
        }

//        Verificando se o nome da empresa já existe no banco de dados:
        empresaNome = empresaDAO.buscarPorNome(nomeInput);
        empresaCNPJ = empresaDAO.buscarPorCNPJ(CNPJ);

//        Verificando se a empresa já está cadastrada:
        if (empresaNome != null && empresaCNPJ != null) {

            request.setAttribute("erroNome", "Nome de empresa já cadastrado");
            request.setAttribute("erroCNPJ", "CNPJ já cadastrado");
            request.getRequestDispatcher("/cadastro/cnpjNomeEmpresa/cadastro.jsp").forward(request, response);

        } else if (empresaNome != null) {

            request.setAttribute("erroNome", "Este nome já está cadastrado");

            request.getRequestDispatcher("/cadastro/cnpjNomeEmpresa/cadastro.jsp").forward(request, response);

        } else if (empresaCNPJ != null){

            request.setAttribute("erroCNPJ", "Este CNPJ já está cadastrado");

            request.getRequestDispatcher("/cadastro/cnpjNomeEmpresa/cadastro.jsp").forward(request, response);

        } else {

            session.setAttribute("nomeEmpresa", nomeInput);
            session.setAttribute("cnpjEmpresa", CNPJ);
            response.sendRedirect(request.getContextPath() + "/cadastro/plano/contato/escolherPlano.html");

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}