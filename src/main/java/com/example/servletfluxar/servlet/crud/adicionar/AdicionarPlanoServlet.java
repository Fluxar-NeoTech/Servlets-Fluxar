package com.example.servletfluxar.servlet.crud.adicionar;

import com.example.servletfluxar.dao.PlanoDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Plano;
import com.example.servletfluxar.util.ValidacaoInput;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "AdicionarPlanoServlet", value = "/AdicionarPlanoServlet")
public class AdicionarPlanoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        HttpSession session = request.getSession();

//        Setando o atributo com o tipo do usuário
        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                request.setAttribute("administrador", (Administrador) session.getAttribute("administrador"));
            } else {
                response.sendRedirect(request.getContextPath()+"/ListarPlanosServlet");
                return;
            }
        } catch (NullPointerException npe){
            request.setAttribute("erroLogin", "É necessário fazer login novamente");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/WEB-INF/pages/planos/adicionarPlano.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        String nome = request.getParameter("nome");
        int tempo = 0;
        String precoInput = request.getParameter("preco");
        HttpSession session = request.getSession();
        Double preco = 0.0;
        PlanoDAO planoDAO = new PlanoDAO();
        Plano plano = new Plano();
        boolean continuar = true;

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                request.setAttribute("administrador", (Administrador) session.getAttribute("administrador"));
            } else {
                response.sendRedirect(request.getContextPath()+"/ListarPlanosServlet");
                return;
            }
        } catch (NullPointerException npe){
            request.setAttribute("erroLogin", "É necessário fazer login novamente");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }

        try{
            tempo = Integer.parseInt(request.getParameter("tempo"));
        } catch (Exception e){
            request.setAttribute("erroTempo", "Tempo inválido");
            continuar = false;
        }

//        Verificações do input:
        if (nome == null){
            request.setAttribute("erroNome", "Insira um nome para o plano");
            continuar = false;
        }
        if (precoInput == null){
            request.setAttribute("erroNome", "Insira um nome para o plano");
            continuar = false;
        } else {
            precoInput = precoInput.replace(",", ".");

            try {
                preco = Double.parseDouble(precoInput);
                plano.setPreco(preco);
                if (!ValidacaoInput.validarPreco(preco)){
                    request.setAttribute("erroPreco", "Preço deve ser maior do que 0");
                    continuar = false;
                }
            } catch (NumberFormatException nfe){
                request.setAttribute("erroPreco", "Preço deve ser um número real");
                continuar = false;
            }
        }

//        Adicinando nome e tempo:
        plano.setNome(nome);
        plano.setTempo(tempo);

        if (!continuar){
            request.getRequestDispatcher("/WEB-INF/pages/planos/adicionarPlano.jsp")
                    .forward(request, response);
            return;
        }

//        Enviando e vendo se há um retorno:
        if (planoDAO.inserir(plano)){
            response.sendRedirect(request.getContextPath() + "/ListarPlanosServlet");
        }else {
            request.setAttribute("mensagem", "Não foi possível inserir um plano no momento. Tente novamente mais tarde...");
            request.getRequestDispatcher("")
                    .forward(request, response);
        }
    }
}