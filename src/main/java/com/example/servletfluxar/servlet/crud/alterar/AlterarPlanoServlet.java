package com.example.servletfluxar.servlet.crud.alterar;

import com.example.servletfluxar.dao.PlanoDAO;
import com.example.servletfluxar.model.Plano;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "AlterarPlanoServlet", value = "/AlterarPlanoServlet")
public class AlterarPlanoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Declaração de variáveis:
        int id = Integer.parseInt(request.getParameter("id"));
        PlanoDAO planoDAO = new PlanoDAO();
        Plano plano = planoDAO.buscarPorId(id);
        request.setAttribute("plano", plano);
        request.getRequestDispatcher("/WEB-INF/pages/planos/alterarPlano.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        String nome = request.getParameter("nome");
        int tempo = Integer.parseInt(request.getParameter("tempo"));
        String precoInput = request.getParameter("preco");
        Double preco = 0.0;
        PlanoDAO planoDAO = new PlanoDAO();
        Plano plano = new Plano();
        boolean continuar = true;

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
        }
        if (!continuar){
            request.getRequestDispatcher(request.getContextPath()+"/WEB-INF/pages/planos/adicionarPlano.jsp")
                    .forward(request, response);
        }

//        Criando um objeto plano:
        plano.setNome(nome);
        plano.setTempo(tempo);
        plano.setPreco(preco);

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
