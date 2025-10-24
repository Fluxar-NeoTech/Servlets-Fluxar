package com.example.servletfluxar.servlet.crud.alterar;

import com.example.servletfluxar.dao.PlanoDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Plano;
import com.example.servletfluxar.util.ValidacaoInput;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "AlterarPlanoServlet", value = "/AlterarPlanoServlet")
public class AlterarPlanoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter("id"));
        PlanoDAO planoDAO = new PlanoDAO();
        Plano plano = planoDAO.buscarPorId(id);
        session.setAttribute("plano", plano);

        request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
        if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
            request.setAttribute("administrador", (Administrador) session.getAttribute("administrador"));
        } else {
            request.setAttribute("empresa", (Empresa) session.getAttribute("empresa"));
        }

        request.setAttribute("plano", plano);
        request.getRequestDispatcher("/WEB-INF/pages/planos/alterarPlano.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        String nome = request.getParameter("nome");
        int tempo = Integer.parseInt(request.getParameter("tempo"));
        String precoInput = request.getParameter("preco");
        Double preco = 0.0;
        PlanoDAO planoDAO = new PlanoDAO();
        Plano plano = new Plano();
        boolean continuar = true;

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                request.setAttribute("administrador", (Administrador) session.getAttribute("administrador"));
            } else {
                request.setAttribute("empresa", (Empresa) session.getAttribute("empresa"));
            }

            request.getRequestDispatcher("/WEB-INF/pages/planos/adicionarPlano.jsp")
                    .forward(request, response);
        } catch (NullPointerException npe){
            request.setAttribute("erroLogin", "É necessário fazer login novamente");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
        }

//        Criando um objeto plano:
        plano.setNome(nome);
        plano.setTempo(tempo);

//        Verificações do input:
        if (nome == null){
            request.setAttribute("erroNome", "Insira um nome para o plano");
            continuar = false;
        }
        if (precoInput == null){
            request.setAttribute("erroNome", "Insira um nome para o plano");
            continuar = false;
        } else {
            preco = Double.parseDouble(precoInput);
            try {
                preco = Double.parseDouble(precoInput);
                plano.setPreco(preco);
                if (!ValidacaoInput.validarPreco(preco)){
                    request.setAttribute("erroPreco", "Preço deve ser maior do que 0");
                    continuar = false;
                }
            } catch (NumberFormatException nfe){
                request.setAttribute("erroPreco", "Preço deve ser um número");
                continuar = false;
            }
        }

        if (!continuar){
            request.setAttribute("plano", plano);
            request.getRequestDispatcher("/WEB-INF/pages/planos/alterarPlano.jsp")
                    .forward(request, response);
            return;
        }

//        Enviando e vendo se há um retorno:
        if (planoDAO.alterar(plano)){
            response.sendRedirect(request.getContextPath()+"/ListarPlanosServlet");
        }else {
            request.setAttribute("mensagem", "Não foi possível alterar este plano no momento. Tente novamente mais tarde...");
            request.getRequestDispatcher("")
                    .forward(request, response);
        }
    }
}