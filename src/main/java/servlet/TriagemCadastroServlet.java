package servlet;

import dao.EmpresaDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Empresa;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "TriagemCadastroServlet", value = "/TriagemCadastroServlet")
public class TriagemCadastroServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//      Declarando variáveis:
        String nomeInput;
        String CNPJInput;
        String CNPJ = null;
        Empresa empresa;
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher;
        Pattern pattern;
        Matcher matcher;
        boolean existeCNPJ;

//        Pegando Input do usuário:
        nomeInput = request.getParameter("nome").trim();
        CNPJInput = request.getParameter("CNPJ").trim();

// Validar e extrair CNPJ limpo:
        pattern = Pattern.compile("(\\d{2})\\.(\\d{3})\\.(\\d{3})/(\\d{4})-(\\d{2})");
        matcher = pattern.matcher(CNPJInput);

        if (matcher.matches()) {
            // Extrai só os dígitos, sem pontuação
            CNPJ = matcher.group(1) + matcher.group(2) + matcher.group(3) + matcher.group(4) + matcher.group(5);

        } else if (CNPJInput.matches("\\d{14}")) {
            // Já está no formato limpo
            CNPJ = CNPJInput;

        } else {
            // Formato inválido
            request.setAttribute("erroCNPJ", "Formato de CNPJ inválido");
            dispatcher = request.getRequestDispatcher("/cadastro/cnpjNomeEmpresa/cadastro.jsp");
            dispatcher.forward(request, response);
            return;
        }

//        Verificando se o nome da empresa já existe no banco de dados:
        if (!EmpresaDAO.verificarCampo("nome",nomeInput)) {
//            Verificando se o CNPJ já está cadastrado e se ele existe:
            empresa = EmpresaDAO.buscarEmpresa("cnpj",CNPJ);
            if (empresa == null) {
                session.setAttribute("nomeEmpresa", nomeInput);
                session.setAttribute("cnpjEmpresa", CNPJ);
                session.setAttribute("status", null);
                response.sendRedirect(request.getContextPath() + "/cadastro/plano/contato/escolherPlano.html");
            } else {
                if (empresa.getStatus() == "I") {
                    session.setAttribute("status", "I");
                    response.sendRedirect(request.getContextPath() + "/cadastro/plano/contato/escolherPlano.html");
                } else {
                    request.setAttribute("erroCNPJ", "CNPJ em uso");
                    dispatcher = request.getRequestDispatcher("/cadastro/cnpjNomeEmpresa/cadastro.jsp");
                    dispatcher.forward(request, response);
                }
            }
        } else {
            request.setAttribute("erroNome", "Nome de empresa em uso");
            dispatcher = request.getRequestDispatcher("/cadastro/cnpjNomeEmpresa/cadastro.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}