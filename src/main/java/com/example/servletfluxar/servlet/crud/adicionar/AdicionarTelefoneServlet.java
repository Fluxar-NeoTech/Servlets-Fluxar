package com.example.servletfluxar.servlet.crud.adicionar;

import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.dao.TelefoneDAO;
import com.example.servletfluxar.dao.UnidadeDAO;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Telefone;
import com.example.servletfluxar.model.Unidade;
import com.example.servletfluxar.util.RegrasBanco;
import com.example.servletfluxar.util.ValidacaoInput;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "AdicionarTelefoneServlet", value = "/AdicionarTelefoneServlet")
public class AdicionarTelefoneServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        HttpSession session = request.getSession();

//        Setando atributo da request com o tipo do usuário
        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                response.sendRedirect(request.getContextPath()+"/ListarUnidadesServlet");
                return;
            } else {
                request.setAttribute("empresa", (Empresa) session.getAttribute("empresa"));
            }
//            Tratando exceção para caso não seja encontrado os dados na session:
        } catch (NullPointerException npe){
            request.setAttribute("erroLogin", "É necessário fazer login novamente");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }

//        Redireciona para a página de adicionar empresa:
        request.getRequestDispatcher("/WEB-INF/pages/telefones/adicionarTelefone.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        String numero = request.getParameter("telefone").trim();
        HttpSession session = request.getSession();
        EmpresaDAO empresaDAO = new EmpresaDAO();
        Empresa empresaLogada;
        TelefoneDAO telefoneDAO = new TelefoneDAO();
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        Telefone telefone = new Telefone();
        Unidade unidade = new Unidade();
        boolean continuar = true;

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")){
                response.sendRedirect(request.getContextPath()+"/ListarUnidadesServlet");
                return;
            } else {
                empresaLogada = (Empresa) session.getAttribute("empresa");
                request.setAttribute("empresa", empresaLogada);
            }
        } catch (NullPointerException npe){
            request.setAttribute("erroLogin", "É necessário fazer login novamente");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }

//        Vaidando se número é válido:
        telefone.setNumero(numero);
        if (numero == null || numero.isEmpty()){
            request.setAttribute("erroTelefone", "Insira um número para o telefone");
            continuar = false;
        } else {
            if (ValidacaoInput.validarTelefone(numero)){
                numero = RegrasBanco.telefone(numero);
                if (telefoneDAO.buscarPorNumero(numero) != null){
                    request.setAttribute("erroTelefone", "Número de telefone já cadastrado");
                    continuar = false;
                } else {
                    telefone.setNumero(numero);
                }
            } else {
                request.setAttribute("erroTelefone", "Formato de telefone inválido");
                continuar = false;
            }
        }

//        Validando se o id da empresa é válido:
        try {
            telefone.setIdEmpresa(empresaLogada.getId());
        } catch (NullPointerException | NumberFormatException e){
            request.setAttribute("erroNumero", "Id da empresa deve ser um número");
            continuar = false;
        }

        if (!continuar){
            request.getRequestDispatcher("/WEB-INF/pages/telefones/adicionarTelefone.jsp")
                    .forward(request, response);
            return;
        }

//        Enviando e vendo se há um retorno:
        if (telefoneDAO.inserir(telefone)){
            response.sendRedirect(request.getContextPath() + "/ListarTelefonesServlet");
        }else {
            request.setAttribute("mensagem", "Não foi possível inserir um telefone no momento. Tente novamente mais tarde...");
            request.getRequestDispatcher("")
                    .forward(request, response);
        }
    }
}
