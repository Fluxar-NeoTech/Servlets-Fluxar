<%@ page import="com.example.servletfluxar.model.Administrador" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Adicionar administrador</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
</head>

<body>
<%
  String tipoUsuario = (String) request.getAttribute("tipoUsuario");
%>
<header>
  <div id="nome">
    <p>
      <%=((Administrador) session.getAttribute("administrador")).getNome() + " " +
                      ((Administrador) session.getAttribute("administrador")).getSobrenome()%>
    </p>
  </div>
</header>
<aside>
  <div class="maior">
    <img src="${pageContext.request.contextPath}/Assets/Images/logo.png"
         alt="Logo do aplicativo. Palavra Fluxar com letras roxas, porém o X tem um gradiente em cada linha (roxo ao azul) (laranja ao rosa)"
         id="logo">
  </div>

  <nav>
    <ul class="linksServlet">
      <li>
        <a href="${pageContext.request.contextPath}/HomeServlet">
          <div class="text">
            Home
          </div>
        </a>
      </li>

      <%if (tipoUsuario.equals("administrador")) { %>
      <li>
        <a href="${pageContext.request.contextPath}/ListarAdminsServlet">
          <div class="text" id="atual">
            Admins
          </div>
        </a>
      </li>
      <%}%>

      <li>
        <a href="${pageContext.request.contextPath}/ListarPlanosServlet">
          <div class="text">
            Planos
          </div>
        </a>
      </li>

      <li>
        <a href="${pageContext.request.contextPath}/ListarAssinaturasServlet">
          <div class="text">
            Assinaturas
          </div>
        </a>
      </li>

      <li>
        <a href="${pageContext.request.contextPath}/ListarEmpresasServlet">
          <div class="text">
            <%if (tipoUsuario == "administrador") {%>
              Empresas
            <%} else {%>
              Empresa
            <%}%>
          </div>
        </a>
      </li>

      <li>
        <a href="${pageContext.request.contextPath}/ListarUnidadesServlet">
          <div class="text">
            Unidades
          </div>
        </a>
      </li>

      <li>
        <a href="${pageContext.request.contextPath}/ListarSetoresServlet">
          <div class="text">
            Setores
          </div>
        </a>
      </li>

      <li>
        <a href="${pageContext.request.contextPath}/ListarFuncionariosServlet">
          <div class="text func">
            Funcionarios
          </div>
        </a>
      </li>
    </ul>
  </nav>

  <div>
    <a class="botaoPrimario" href="${pageContext.request.contextPath}/SairServlet">Sair</a>
  </div>
</aside>
<main>
  <p id="title">Adicionar administrador</p>

  <form action="${pageContext.request.contextPath}/AdicionarAdminServlet" method="post">
    <label for="name">Nome:</label>
    <input type="text" name="nomeCompleto" id="name" required>
    <p><%= request.getAttribute("erroNome")%></p>

    <label for="email">Email:</label>
    <input type="email" name="email" id="email" required>
    <p><%= request.getAttribute("erroEmail")%></p>

    <label for="senha">Senha:</label>
    <input type="text" name="senha" id="senha" required>
    <p><%= request.getAttribute("erroSenha")%></p>

    <label for="confirmarSenha">Confirmar senha:</label>
    <input type="password" name="confirmarSenha" id="confirmarSenha" required>
    <p><%= request.getAttribute("erroConfirmarSenha")%></p>

    <div>
      <button type="submit" class="botaoPrimario">Confirmar</button>
      <a class="botaoSecundario" href="${pageContext.request.contextPath}/ListarAdminsServlet">Voltar</a>
    </div>
  </form>
</main>
</body>

</html>