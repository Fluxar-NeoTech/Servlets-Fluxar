package com.example.servletfluxar.servlet.crud.adicionar;

import com.example.servletfluxar.dao.PlanoDAO;
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
        request.getRequestDispatcher("/WEB-INF/pages/planos/adicionarPlano.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        if (planoDAO.inserir(plano)){
            response.sendRedirect(request.getContextPath()+"/ListarPlanosServlet");
        }else {
            request.setAttribute("mensagem", "Não foi possível inserir um plano no momento. Tente novamente mais tarde...");
            request.getRequestDispatcher("")
                    .forward(request, response);
        }
    }
}
