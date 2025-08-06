package servlet;

import dao.AdministradorDAO;
import dao.EmpresaDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Administrador;
import model.Empresa;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "FormaPagamentoServlet", value = "/FormaPagamentoServlet")
public class FormaPagamentoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        HttpSession session = request.getSession();
        String formaPag = request.getParameter("formaPagamento");
        int codigo;
        LocalDate hoje = LocalDate.now();
        Date datasql = Date.valueOf(hoje);
        String nomeEmpresa = (String) session.getAttribute("nomeEmpresa");
        String CNPJ = (String) session.getAttribute("cnpjEmpresa");
        String status = (String) session.getAttribute("status");
        Integer plano = (Integer) session.getAttribute("plano");
        Integer duracao = (Integer) session.getAttribute("duracao");
        String emailAdmin = (String) session.getAttribute("emailAdmin");
        String senhaAdmin = (String) session.getAttribute("senhaAdmin");
        Empresa emp = new Empresa();
        List<Empresa> empresas = EmpresaDAO.buscarTodas();

        //        Cadastrando empresa
        emp.setNome(nomeEmpresa);
        emp.setCnpj(CNPJ);
        emp.setStatus("A");
        emp.setIdPlano(plano);
        emp.setDtInicio(datasql);
        emp.setDuracao(duracao);
        emp.setFormaPag(formaPag);
        EmpresaDAO.cadastrarEmpresa(emp);

        emp = EmpresaDAO.buscarPorCNPJ(CNPJ);
        codigo = emp.getCodigo();

//        Salvando forma de pagamento e código da empresa:
        session.setAttribute("formaPagamento", formaPag);
        session.setAttribute("codigoEmpresa",codigo);

//        Enviando usuário para próxima página:
        response.sendRedirect(request.getContextPath() +"/cadastro/pagamento/pagamentoAprovado/confirmouPag.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}