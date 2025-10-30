package com.example.servletfluxar.servlet.crud.alterar;

import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.dao.UnidadeDAO;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Unidade;
import com.example.servletfluxar.util.RegrasBanco;
import com.example.servletfluxar.util.ValidacaoInput;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "AlterarUnidadeServlet", value = "/AlterarUnidadeServlet")
public class AlterarUnidadeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        int id = 0;
        HttpSession session = request.getSession();
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        Unidade unidade = new Unidade();
        Empresa empresaLogada;

//        Setando atributo da request com o tipo do usuário
        try {
            empresaLogada = (Empresa) session.getAttribute("empresa");
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

        try{
            id = Integer.parseInt(request.getParameter("id"));
            if (unidadeDAO.buscarPorId(id).getIdEmpresa() != empresaLogada.getId()){
                response.sendRedirect("/ListarUnidadesServlet");
                return;
            }
        } catch (NullPointerException | NumberFormatException e){
            request.setAttribute("erro", e.getMessage());
            request.setAttribute("mensagem", "Ocorreu um erro ao procurar essa unidade");
            request.getRequestDispatcher("")
                    .forward(request, response);
            return;
        }

        unidade = unidadeDAO.buscarPorId(id);

        request.setAttribute("unidade", unidade);

//        Redireciona para a página de alterar unidade:
        request.getRequestDispatcher("/WEB-INF/pages/unidades/alterarUnidade.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        String nome = request.getParameter("nome");
        String cnpj = request.getParameter("cnpj");
        String cep = request.getParameter("cep");
        int numero = 0;
        int id = 0;
        String complemento = request.getParameter("complemento");
        String email = request.getParameter("email");
        String confirmarSenha = request.getParameter("confirmarSenha");
        HttpSession session = request.getSession();
        EmpresaDAO empresaDAO = new EmpresaDAO();
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        Unidade unidade = new Unidade();
        boolean continuar = true;
        Empresa empresaLogada;

        try {
            empresaLogada = (Empresa) session.getAttribute("empresa");
            request.setAttribute("tipoUsuario", (String) session.getAttribute("tipoUsuario"));
            if (empresaLogada == null){
                response.sendRedirect(request.getContextPath()+"/ListarUnidadesServlet");
                return;
            } else {
                request.setAttribute("empresa", (Empresa) session.getAttribute("empresa"));
            }
        } catch (NullPointerException npe){
            request.setAttribute("erroLogin", "É necessário fazer login novamente");
            request.getRequestDispatcher("/pages/error/erroLogin.jsp").forward(request, response);
            return;
        }

//        Validando se o id é passado:
        try {
            id = Integer.parseInt(request.getParameter("id"));
            if (unidadeDAO.buscarPorId(id).getIdEmpresa() != empresaLogada.getId()){
                response.sendRedirect("/ListarUnidadesServlet");
                return;
            }
            unidade.setId(id);
        } catch (NullPointerException | NumberFormatException e){
            request.setAttribute("erroNumero", "Número do endereço deve conter apenas números");
            continuar = false;
        }

//        Validando se nome é válido:
        if (nome == null){
            request.setAttribute("erroNome", "Insira um nome para a unidade");
            continuar = false;
        } else {
            nome = RegrasBanco.nomeCapitalize(nome);
            unidade.setNome(nome);
        }

//        Validando se CNPJ é válido:
        if (cnpj == null){
            request.setAttribute("erroCnpj", "Insira um cnpj para a empresa");
            continuar = false;
        } else {
            if (ValidacaoInput.validarCNPJ(cnpj)){
                cnpj = RegrasBanco.cnpj(cnpj);
                if (empresaDAO.buscarPorCNPJ(cnpj) != null || unidadeDAO.buscarPorCnpj(cnpj) != null){
                    request.setAttribute("erroCnpj", "Cnpj já está cadastrado");
                    continuar = false;
                }
            } else {
                request.setAttribute("erroCnpj", "Formato de cnpj inválido");
                continuar = false;
            }
        }

//        Validando se email é válido:
        if (email == null){
            request.setAttribute("erroEmail", "Insira um email para a unidade");
            continuar = false;
        } else {
            if (ValidacaoInput.validarEmail(email)){
                if (empresaDAO.buscarPorEmail(email) == null){

                } else {
                    request.setAttribute("erroEmail", "Email em uso");
                    continuar = false;
                }
            } else {
                request.setAttribute("erroEmail", "Formato de email inválido");
                continuar = false;
            }
        }

//        Validando se o cep é válido:
        if (cep == null){
            request.setAttribute("erroCep", "Insira uma cep para a unidade");
            continuar = false;
        } else {
            if (ValidacaoInput.validarCEP(cep)){
                cep = RegrasBanco.cep(cep);
            } else {
                request.setAttribute("erroCep", "Formato de cep inválido");
                continuar = false;
            }
        }

//        Verificando se há complemento para o endereço, caso não haja, é colocado sem complemento:
        if (complemento == null){
            complemento = "Sem complemento";
        }

//        Validando número:
        try {
            numero = Integer.parseInt(request.getParameter("numero"));
        } catch (NullPointerException | NumberFormatException e){
            request.setAttribute("erroNumero", "Número do endereço deve conter apenas números");
            continuar = false;
        }

        if (!continuar){
            request.getRequestDispatcher("/WEB-INF/pages/unidades/alterarUnidade.jsp")
                    .forward(request, response);
            return;
        }

//        Setando objeto empresa para adicionar:
        unidade.setNome(nome);
        unidade.setEmail(email);
        unidade.setCnpj(cnpj);
        unidade.setCep(cep);
        unidade.setNumero(numero);
        unidade.setComplemento(complemento);
        unidade.setIdEmpresa(((Empresa) request.getAttribute("empresa")).getId());

//        Enviando e vendo se há um retorno:
        if (unidadeDAO.alterar(unidade)){
            response.sendRedirect(request.getContextPath() + "/ListarUnidadesServlet");
        }else {
            request.setAttribute("mensagem", "Não foi possível inserir uma unidade no momento. Tente novamente mais tarde...");
            request.getRequestDispatcher("")
                    .forward(request, response);
        }
    }
}
