package com.example.servletfluxar.servlet;

import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.model.Empresa;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

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
        String senhaCriptografada;
        RequestDispatcher dispatcher = null;
        Empresa empresaJaCadastrada;
        Empresa empresa;
        LocalDate hoje = LocalDate.now();
        Date datasql = Date.valueOf(hoje);
        String nomeEmpresa = (String) session.getAttribute("nomeEmpresa");
        String CNPJ = (String) session.getAttribute("cnpjEmpresa");
        Integer plano = (Integer) session.getAttribute("plano");

//        Salvando forma de pagamento:
        session.setAttribute("formaPagamento", formaPag);

//        Pegando input do usuário:
        emailAdmin = (String) session.getAttribute("emailAdmin");
        senha = (String) session.getAttribute("senhaAdmin");

//        Adicionando os dados da empresa e do seu admin em um objeto da classe Empresa:
        empresa = new Empresa();

        if (EmpresaDAO.cadastrar(empresa)){
//        Enviando usuário para próxima página:
            response.sendRedirect(request.getContextPath() +"/cadastro/fimCadastro/agradecimentos.html");
        }else{
            response.sendRedirect(request.getContextPath() +"/cadastro/cnpjNomeEmpresa/cadastro.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}