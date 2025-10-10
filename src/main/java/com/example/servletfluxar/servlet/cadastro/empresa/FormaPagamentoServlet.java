package com.example.servletfluxar.servlet.cadastro.empresa;

import com.example.servletfluxar.dao.AssinaturaDAO;
import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.model.Assinatura;
import com.example.servletfluxar.model.Empresa;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "FormaPagamentoServlet", value = "/FormaPagamentoServlet")
public class FormaPagamentoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        String formaPag = request.getParameter("formaPagamento");
        String emailAdmin;
        String senha;
        LocalDate hoje = LocalDate.now();
        EmpresaDAO empresaDAO = new EmpresaDAO();
        Empresa empresa = new Empresa();
        AssinaturaDAO assinaturaDAO = new AssinaturaDAO();
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

        if (empresaDAO.inserir(empresa)) {
            assinatura.setIdEmpresa(empresaDAO.buscarPorCNPJ(CNPJ).getId());


//        Enviando usuário para próxima página:
            if (assinaturaDAO.inserir(assinatura)){
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