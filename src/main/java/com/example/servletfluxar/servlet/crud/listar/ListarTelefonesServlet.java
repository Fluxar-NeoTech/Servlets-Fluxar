package com.example.servletfluxar.servlet.crud.listar;

import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.dao.SetorDAO;
import com.example.servletfluxar.dao.TelefoneDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Setor;
import com.example.servletfluxar.model.Telefone;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ListarTelefonesServlet", value = "/ListarTelefonesServlet")
public class ListarTelefonesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Declaração de variáveis:
        String tipoFiltro = request.getParameter("tipoFiltro");
        String valorFiltro = request.getParameter("valorFiltro");
        int idEmpresa = 0;
        EmpresaDAO empresaDAO = new EmpresaDAO();
        int pagina = 1;
        int limite = 6;
        int totalRegistros = 0;
        int totalPaginas = 0;
        HttpSession session = request.getSession(false);
        TelefoneDAO telefoneDAO = new TelefoneDAO();
        List<Telefone> telefones = new ArrayList<>();

        try {
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (((String) session.getAttribute("tipoUsuario")).equals("administrador")) {
                request.setAttribute("administrador", (Administrador) session.getAttribute("administrador"));
            } else {
                request.setAttribute("empresa", (Empresa) session.getAttribute("empresa"));
            }
        } catch (NullPointerException npe) {
            request.setAttribute("erro", "É necessário fazer login novamente");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        if (request.getAttribute("tipoUsuario").equals("administrador")) {
            try {
                idEmpresa = Integer.parseInt(request.getParameter("id"));
            } catch (NullPointerException | NumberFormatException e) {
                request.setAttribute("mensagem", "Id empresa deve ser um número real");
                request.getRequestDispatcher("")
                        .forward(request, response);
                return;
            }
        }

//                Verificando a página atual:
        if (request.getParameter("pagina") != null) {
            pagina = Integer.parseInt(request.getParameter("pagina"));
        }

        if (session.getAttribute("tipoUsuario").equals("empresa")) {
            totalRegistros = telefoneDAO.contarPorIdEmpresa(((Empresa) session.getAttribute("empresa")).getId());
            totalPaginas = Math.max(1, (int) Math.ceil(totalRegistros / 6.0));

//              Garante que pagina está no intervalo válido [1, totalPaginas]
            pagina = Math.max(1, Math.min(pagina, totalPaginas));

            telefones = totalRegistros > 0 ? telefoneDAO.listarPorIdEmpresa(pagina, limite, ((Empresa) session.getAttribute("empresa")).getId()) : new ArrayList<>();
        } else {
            if (idEmpresa <= 0){
                request.setAttribute("erro", "Id da empresa deve ser um número positivo");
                request.getRequestDispatcher("WEB-INF/pages/empresas/verEmpresas.jsp")
                        .forward(request,response);
                return;
            }
            request.setAttribute("empresa", empresaDAO.buscarPorId(idEmpresa));
            totalRegistros = telefoneDAO.contarPorIdEmpresa(idEmpresa);
            totalPaginas = Math.max(1, (int) Math.ceil(totalRegistros / 6.0));

//              Garante que pagina está no intervalo válido [1, totalPaginas]
            pagina = Math.max(1, Math.min(pagina, totalPaginas));

            telefones = totalRegistros > 0 ? telefoneDAO.listarPorIdEmpresa(pagina, limite, idEmpresa) : new ArrayList<>();

        }

        request.setAttribute("telefones", telefones);
        request.setAttribute("tipoUsuario", session.getAttribute("tipoUsuario"));

//        Setando atributo de página atual:
        request.setAttribute("pagina", pagina);

//        Enviando retorno:
        request.getRequestDispatcher("WEB-INF/pages/telefones/verTelefones.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
