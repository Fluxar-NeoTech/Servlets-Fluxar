package com.example.servletfluxar.servlet.crud.adicionar;

import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.dao.PlanoDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Plano;
import com.example.servletfluxar.util.ValidacaoInput;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "AdicionarEmpresaServlet", value = "/AdicionarEmpresaServlet")
public class AdicionarEmpresaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        HttpSession session = request.getSession();

//        Setando atributo da request com o tipo do usuário
        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                request.setAttribute("administrador", (Administrador) session.getAttribute("administrador"));
            } else {
                response.sendRedirect(request.getContextPath()+"/ListarEmpresasServlet");
                return;
            }
//            Tratando exceção para caso não seja encontrado os dados na session:
        } catch (NullPointerException npe){
            request.setAttribute("erroLogin", "É necessário fazer login novamente");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }

//        Redireciona para a página de adicionar empresa:
        request.getRequestDispatcher("/WEB-INF/pages/empresas/adicionarEmpresa.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        String nome = request.getParameter("nome");
        String cnpj = request.getParameter("cnpj");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        HttpSession session = request.getSession();
        EmpresaDAO empresaDAO = new EmpresaDAO();
        Empresa empresa = new Empresa()
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

//        Verificações do input:
        if (nome == null){
            request.setAttribute("erroNome", "Insira um nome para a empresa");
            continuar = false;
        }
        if (cnpj == null){
            request.setAttribute("erroCnpj", "Insira um cnpj para a empresa");
            continuar = false;
        } else {
            if (ValidacaoInput.validarCNPJ(cnpj)){
                cnpj = Inser
            } else {
                request.setAttribute("erroCnpj", "Insira um cnpj para a empresa");
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
