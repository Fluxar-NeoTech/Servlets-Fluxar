<%@ page import="com.example.servletfluxar.model.Empresa" %>
<%@ page import="com.example.servletfluxar.model.Setor" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Adicionar funcionário</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>
<%
  List<Setor> setores = (List<Setor>) request.getAttribute("setores");
%>
<header>
  <div id="nome">
    <a href="${pageContext.request.contextPath}/MeuPerfilServlet?idUsuario=<%=
                    ((Empresa) session.getAttribute("empresa")).getId()%>">
      <%=((Empresa) session.getAttribute("empresa")).getNome()%>
    </a>
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

      <li>
        <a href="${pageContext.request.contextPath}/ListarAdminsServlet">
          <div class="text">
            Admins
          </div>
        </a>
      </li>

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
            Assinatura
          </div>
        </a>
      </li>

      <li>
        <a href="${pageContext.request.contextPath}/ListarEmpresasServlet">
          <div class="text">
            Empresa
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
          <div class="text func" id="atual">
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
  <p id="title">Adicionar funcionário</p>

  <form action="${pageContext.request.contextPath}/AdicionarFuncionarioSetorServlet" method="post">
    <select name = "idSetor">
      <option selected hidden>Setor</option>
      <%for (Setor setor: setores) {%>
        <option value="<%=setor.getId()%>"><%=setor.getNome()%></option>
      <%}%>
    </select>
    <p><%= request.getAttribute("erroNome")%></p>

    <label for="senha">Senha:</label>
    <input type="text" name="senha" id="senha">
    <p><%= request.getAttribute("erroSenha")%></p>

    <label for="confirmarSenha">Confirmar senha:</label>
    <input type="password" name="confirmarSenha" id="confirmarSenha">
    <p><%= request.getAttribute("erroConfimarSenha")%></p>

    <div>
      <button type="submit" class="botaoPrimario">Confirmar</button>
      <a class="botaoSecundario" href="${pageContext.request.contextPath}/ListarAdminsServlet">Voltar</a>
    </div>
  </form>
</main>
</body>

</html>