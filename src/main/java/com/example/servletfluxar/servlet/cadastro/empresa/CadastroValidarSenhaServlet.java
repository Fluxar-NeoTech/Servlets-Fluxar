package com.example.servletfluxar.servlet.cadastro.empresa;

import com.example.servletfluxar.dao.AdministradorDAO;
import com.example.servletfluxar.dao.EmpresaDAO;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.util.ValidacaoInput;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "CadastroValidarSenhaServlet", value = "/CadastroValidarSenhaServlet")
public class CadastroValidarSenhaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        // Declaração de variáveis:
        EmpresaDAO empresaDAO = new EmpresaDAO();
        AdministradorDAO administradorDAO = new AdministradorDAO();
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("registroAlterar");
        String senha = request.getParameter("senha");
        String senhaConfirmada = request.getParameter("senhaConfirmada");
        Empresa empresa;
        String plano;
        double preco;
        int senhaValida;
        boolean continuar = true;

        try{
            empresa = (Empresa) session.getAttribute("empresa");
            plano = (String) session.getAttribute("plano");
            preco = (Double) session.getAttribute("preco");
        }catch (NullPointerException npe){
            System.out.println(npe);
            request.setAttribute("erro", "Tempo expirado, tente cadastrar novamente...");
            request.getRequestDispatcher("/pages/cadastro/inicioCadastro.jsp")
                    .forward(request, response);
            return;
        }

        // Verificando se senha está nos padrões:
        if (senha == null){
            request.setAttribute("erroSenha", "Defina uma senha para o administrador");
            continuar = false;
        } else {
            senha = senha.trim();
            senhaValida = ValidacaoInput.validarSenha(senha);
            if (senhaValida != 0){
                if (senhaValida == 1){
                    request.setAttribute("erroSenha", "Senha deve ser menor do que 28 caracteres");
                }
                if (senhaValida == 2){
                    request.setAttribute("erroSenha", "Senha deve ser maior do que 8 caracteres");
                }
                if (senhaValida == 3){
                    request.setAttribute("erroSenha", "Senha deve ter letras maiúsculas");
                }
                if (senhaValida == 4){
                    request.setAttribute("erroSenha", "Senha deve ter letras minúsculas");
                }
                if (senhaValida == 5){
                    request.setAttribute("erroSenha", "Senha deve ter números");
                }
                continuar = false;
            } else {
                if (!senha.equals(senhaConfirmada.trim())){
                    request.setAttribute("erroConfirmarSenha", "Senha confirmada incorreta");
                    continuar = false;
                }
            }
        }

        // Verificando se a senha e a senha confirmada são iguais:
        if (!continuar) {
            request.getRequestDispatcher("/pages/cadastro/senha.jsp")
                    .forward(request, response);
            return;
        }

        empresa.setSenha(senha);

        session.setAttribute("empresa", empresa);

        request.setAttribute("preco", preco);
        request.setAttribute("plano", plano);

//        Redirecionando usuário:
        request.getRequestDispatcher("/pages/cadastro/pagamento.jsp")
                .forward(request, response);
    }
}