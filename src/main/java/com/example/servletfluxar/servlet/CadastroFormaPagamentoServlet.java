package com.example.servletfluxar.servlet;

import com.example.servletfluxar.dao.AssinaturaDAO;
import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.model.Assinatura;
import com.example.servletfluxar.model.Empresa;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "CadastroFormaPagamentoServlet", value = "/CadastroFormaPagamentoServlet")
public class CadastroFormaPagamentoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        String formaPag = request.getParameter("formaPagamento");
        String emailAdmin;
        String senha;
        LocalDate hoje = LocalDate.now();
        Empresa empresa = new Empresa();
        Assinatura assinatura = new Assinatura();
        String nome = (String) session.getAttribute("nomeEmpresa");
        String CNPJ = (String) session.getAttribute("cnpjEmpresa");
        Integer plano = (Integer) session.getAttribute("plano");

//        Salvando forma de pagamento:
        session.setAttribute("formaPagamento", formaPag);

//        Pegando input do usuário:
        emailAdmin = (String) session.getAttribute("emailAdmin");
        senha = (String) session.getAttribute("senhaAdmin");

//        Adicionando os dados da empresa e do seu admin em um objeto da classe Empresa:
        empresa.setNome(nome);
        empresa.setCnpj(CNPJ);
        empresa.setEmail(emailAdmin);
        empresa.setSenha(senha);

        assinatura.setStatus('A');
        assinatura.setDtInicio(hoje);
        assinatura.setDtFim(plano % 2 == 0? hoje.plusYears(1): hoje.plusMonths(1));
        assinatura.setIdPlano(plano);
        assinatura.setFormaPagamento(formaPag);

        if (EmpresaDAO.cadastrar(empresa)) {
            assinatura.setIdEmpresa(EmpresaDAO.buscarPorCNPJ(CNPJ).getId());
            if (AssinaturaDAO.cadastrar(assinatura)){
//        Enviando usuário para próxima página:
                response.sendRedirect(request.getContextPath() + "/cadastro/fimCadastro/agradecimentos.html");
            }else{
                response.sendRedirect(request.getContextPath() +"/cadastro/cnpjNomeEmpresa/cadastro.jsp");
            }
        }else{
            response.sendRedirect(request.getContextPath() +"/cadastro/cnpjNomeEmpresa/cadastro.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}