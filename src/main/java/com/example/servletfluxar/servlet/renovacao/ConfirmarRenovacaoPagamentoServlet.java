package com.example.servletfluxar.servlet.renovacao;

import com.example.servletfluxar.dao.AssinaturaDAO;
import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.model.Assinatura;
import com.example.servletfluxar.model.Empresa;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "ConfirmarRenovacaoPagamentoServlet", value = "/ConfirmarRenovacaoPagamentoServlet")
public class ConfirmarRenovacaoPagamentoServlet extends HttpServlet {
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
            request.setAttribute("erro", "Tempo expirado, tente logar para renovar novamente...");
            request.getRequestDispatcher("/index.jsp")
                    .forward(request, response);
            return;
        }

        assinatura.setId(assinaturaDAO.buscarPorIdEmpresa(empresa.getId()).getId());
        assinatura.setIdEmpresa(empresa.getId());
        assinatura.setStatus('A');
        assinatura.setDtInicio(hoje);
        assinatura.setDtFim(idPlano % 2 == 0? hoje.plusYears(1): hoje.plusMonths(1));
        assinatura.setIdPlano(idPlano);
        assinatura.setFormaPagamento(formaPag);

        if (assinaturaDAO.alterar(assinatura)){
            response.sendRedirect(request.getContextPath()+"/pages/agradecimento.jsp");
        }else{
            request.setAttribute("erro", "Não foi possível cadastrar, tente novamente mais tarde...");
            request.getRequestDispatcher("/pages/renovacao/escolhaPlano.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
