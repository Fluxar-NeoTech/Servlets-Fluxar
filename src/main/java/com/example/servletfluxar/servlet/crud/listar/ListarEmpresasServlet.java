package com.example.servletfluxar.servlet.crud.listar;

import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.dao.TelefoneDAO;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Telefone;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ListarEmpresaServlet", value = "/ListarEmpresaServlet")
public class ListarEmpresasServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Declaração de variáveis:
        String tipoFiltro = request.getParameter("tipoFiltro");
        String valorFiltro = request.getParameter("valorFiltro");
        int pagina = 1;
        int limite = 6;
        HttpSession session = request.getSession();
        EmpresaDAO empresaDAO = new EmpresaDAO();
        Map<Integer, Empresa> empresas = new HashMap<>();

        try {
            if (session.getAttribute("usuario")!=null) {
                request.setAttribute("usuario", session.getAttribute("usuario"));
            }
        }catch (NullPointerException npe){
            request.setAttribute("mensagem", "Você passou tempo demais logado, faça seu login novamente");
            request.setAttribute("erro", npe);
            request.getRequestDispatcher("")
                    .forward(request, response);
        }

//        Verificando a página atual:
        if (request.getParameter("pagina") != null){
            try {
                pagina = Integer.parseInt(request.getParameter("pagina"));
                if (pagina<1){
                    pagina=1;
                }
            } catch (NullPointerException npe){
                pagina = 1;
            }
        }

//        Vendo se há algum filtro definido:
        if (tipoFiltro != null){
//            Verificando se há algum valor definido para o filtro:
            if (valorFiltro != null) {

            } else {
                request.setAttribute("erroFiltro", "Defina um valor para o filtro");
                request.getRequestDispatcher("")
                        .forward(request, response);
                return;
            }

        } else {
            empresas = empresaDAO.listar(pagina, limite);
            if (empresas.isEmpty()){
                pagina--;
                empresas = empresaDAO.listar(pagina, limite);
            }
        }

//        Setando atributos:
        request.setAttribute("pagina", pagina);
        request.setAttribute("empresas", empresas);
//        Enviando retorno:
        request.getRequestDispatcher("")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
