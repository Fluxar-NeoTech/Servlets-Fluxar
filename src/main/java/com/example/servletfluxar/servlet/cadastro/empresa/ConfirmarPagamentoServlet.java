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

@WebServlet(name = "ConfirmarPagamentoServlet", value = "/ConfirmarPagamentoServlet")
public class ConfirmarPagamentoServlet extends HttpServlet {
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
        Empresa empresa;
        AssinaturaDAO assinaturaDAO = new AssinaturaDAO();
        Assinatura assinatura = new Assinatura();
        int idPlano;

        try{
            empresa = (Empresa) session.getAttribute("empresa");
            idPlano = (Integer) session.getAttribute("idPlano");
        }catch (NullPointerException npe){
            System.out.println(npe);
            request.setAttribute("erro", "Tempo expirado, tente cadastrar novamente...");
            request.getRequestDispatcher("/pages/cadastro/inicioCadastro.jsp")
                    .forward(request, response);
            return;
        }

//        Adicionando dentro da empresa a data de cadastro, ou seja hoje:
        empresa.setDataCadastro(hoje);

        assinatura.setStatus('A');
        assinatura.setDtInicio(hoje);
        assinatura.setDtFim(idPlano % 2 == 0? hoje.plusYears(1): hoje.plusMonths(1));
        assinatura.setIdPlano(idPlano);
        assinatura.setFormaPagamento(formaPag);

        if (empresaDAO.inserir(empresa)) {
            assinatura.setIdEmpresa(empresaDAO.buscarPorCNPJ(empresa.getCnpj()).getId());

//        Enviando usuário para próxima página:
            if (assinaturaDAO.inserir(assinatura)){
                request.setAttribute("empresa", empresa);
                request.getRequestDispatcher("/pages/cadastro/agradecimentos.jsp")
                        .forward(request, response);
            }else{
                request.setAttribute("erro", "Não foi possível cadastrar, tente novamente mais tarde...");
                request.getRequestDispatcher("/pages/cadastro/inicioCadastro.jsp")
                        .forward(request, response);
            }
        }else{
            request.setAttribute("erro", "Não foi possível cadastrar, tente novamente mais tarde...");
            request.getRequestDispatcher("/pages/cadastro/inicioCadastro.jsp")
                    .forward(request, response);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
