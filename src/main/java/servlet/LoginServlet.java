package servlet;

import dao.AdministradorDAO;
import dao.UsuarioDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Usuario;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    private HttpServletResponse response;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declarando variáveis:
        String emailInput;
        String senhaInput;
        String tipo;
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher = null;
        Usuario usuario;
        List<Usuario> usuarios;
        Administrador admin;

//        Coletando o input do usuário:
        emailInput = request.getParameter("emailUsuario").trim();
        senhaInput = request.getParameter("senhaUsuario").trim();

//        Verificando se email é válido:
        if (!UsuarioDAO.buscarUsuarioPeloEmail(emailInput).isEmpty() || AdministradorDAO.existeEmail(emailInput)) {
            if (!UsuarioDAO.buscarUsuarioPeloEmail(emailInput).isEmpty() && AdministradorDAO.existeEmail(emailInput)) {
                if (UsuarioDAO.autenticar(emailInput, senhaInput)) {
                    usuarios = UsuarioDAO.buscarUsuarioPeloEmail(emailInput);
                    if (usuarios.size() == 1) {
                        usuario = usuarios.get(0);
                        if (usuario.getTipo().toLowerCase().equals("gestor")) {
                            admin = AdministradorDAO.buscarPorEmail(emailInput);
                            session.setAttribute("admin", admin);
                            response.sendRedirect(request.getContextPath() + "/paginasIniciais/escolhaAdminGestor/adminGestor.html");
                        } else {
                            admin = AdministradorDAO.buscarPorEmail(emailInput);
                            session.setAttribute("admin", admin);
                            response.sendRedirect(request.getContextPath() + "/paginasIniciais/escolhaAdminAnalista/adminAnalista.html");
                        }
                    } else {
                        admin = AdministradorDAO.buscarPorEmail(emailInput);
                        session.setAttribute("admin", admin);
                        response.sendRedirect(request.getContextPath() + "/paginasIniciais/escolhaTodos/todosPI.html");
                    }
                } else {
                    request.setAttribute("erroSenha", "Senha incorreta");
                    dispatcher = request.getRequestDispatcher("/fazerLogin/paginaLogin/login.jsp");
                    dispatcher.forward(request, response);
                }
            } else if (!UsuarioDAO.buscarUsuarioPeloEmail(emailInput).isEmpty()) {
                if (UsuarioDAO.autenticar(emailInput, senhaInput)) {
                    usuarios = UsuarioDAO.buscarUsuarioPeloEmail(emailInput);
                    if (usuarios.size() == 1) {
                        usuario = usuarios.get(0);
                        if (usuario.getTipo().toLowerCase().equals("gestor")) {
                            response.sendRedirect(request.getContextPath() + "/paginasIniciais/PIGestor/PIgestor.jsp");
                        } else {
                            response.sendRedirect(request.getContextPath() + "/paginasIniciais/PIAnalista/PIanalista.jsp");
                        }
                    } else {
                        response.sendRedirect(request.getContextPath() + "/paginasIniciais/escolhaGestorAnalista/gestorAnalistaPI.html");
                    }
                } else {
                    request.setAttribute("erroSenha", "Senha incorreta");
                    dispatcher = request.getRequestDispatcher("/fazerLogin/paginaLogin/login.jsp");
                    dispatcher.forward(request, response);
                }
            } else {
                if (AdministradorDAO.autenticar(emailInput, senhaInput)) {
                    admin = AdministradorDAO.buscarPorEmail(emailInput);
                    session.setAttribute("admin", admin);
                    response.sendRedirect(request.getContextPath() + "/paginasIniciais/PIAdmin/PIadmin.jsp");
                } else {
                    request.setAttribute("erroSenha", "Senha incorreta");
                    dispatcher = request.getRequestDispatcher("/fazerLogin/paginaLogin/login.jsp");
                    dispatcher.forward(request, response);
                }
            }
        } else {
            request.setAttribute("erroEmail", "Email não cadastrado");
            dispatcher = request.getRequestDispatcher("/fazerLogin/paginaLogin/login.jsp");
            dispatcher.forward(request, response);
        }
    }
}