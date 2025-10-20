package com.example.servletfluxar.servlet.crud.listar;

import com.example.servletfluxar.dao.AdministradorDAO;
import com.example.servletfluxar.model.Administrador;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ListarAdminsServlet", value = "/ListarAdminsServlet")
public class ListarAdminsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        String tipoFiltro = request.getParameter("tipoFiltro");
        String valorFiltro = request.getParameter("valorFiltro");
        int pagina = 1;
        int limite = 6;
        AdministradorDAO administradorDAO = new AdministradorDAO();
        List<Administrador> administradores = new ArrayList<>();

//        Informando o tipo de usuário logado a fim de saber se pode ou não editar/adicionar dados;
        try {
            if (session.getAttribute("tipoUsuario")!=null) {
                request.setAttribute("tipoUsuario", session.getAttribute("tipoUsuario"));
            }
        }catch (NullPointerException npe){
            request.setAttribute("mensagem", "Você passou tempo demais logado, faça seu login novamente");
            request.setAttribute("erro", npe);
            request.getRequestDispatcher("")
                    .forward(request, response);
        }

//        Verificando qual página está:
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

        } else {
            administradores = administradorDAO.listar(pagina, limite);
            if (administradores.isEmpty()){
                pagina--;
                administradores = administradorDAO.listar(pagina, limite);
            }
        }

        request.setAttribute("pagina", pagina);
        request.setAttribute("administradores", administradores);
        request.getRequestDispatcher("")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
