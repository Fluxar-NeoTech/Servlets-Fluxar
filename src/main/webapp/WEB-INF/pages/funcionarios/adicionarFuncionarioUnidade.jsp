<%@ page import="com.example.servletfluxar.model.Administrador" %>
<%@ page import="com.example.servletfluxar.model.Empresa" %>
<%@ page import="com.example.servletfluxar.model.Setor" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.servletfluxar.model.Unidade" %>
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
  List<Unidade> unidades = (List<Unidade>) request.getAttribute("unidades");
%>
<header>
  <div id="nome">
    <p>
      <%=((Empresa) session.getAttribute("empresa")).getNome()%>
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

  <form action="${pageContext.request.contextPath}/AdicionarFuncionarioUnidadeServlet" method="post">
    <label for="name">Nome:</label>
    <input type="text" name="nomeCompleto" id="name" required>
    <p><%= request.getAttribute("erroNome")%></p>

    <label for="email">Email:</label>
    <input type="email" name="email" id="email" required>
    <p><%= request.getAttribute("erroEmail")%></p>

    <div>
      <input type="radio" id="analista" name="cargo" value="Analista" required>
      <label for="analista">Analista</label>
      <input type="radio" id="gestor" name="cargo" value="Gestor" required>
      <label for="gestor">Gestor</label>
    </div>
    <p><%= request.getAttribute("erroCargo")%></p>

    <select name = "idUnidade" required>
      <option selected hidden value="">Unidade</option>
      <%for (Unidade unidade: unidades) {%>
      <option value="<%=unidade.getId()%>"><%=unidade.getNome()%></option>
      <%}%>
    </select>
    <p><%= request.getAttribute("erroIdUnidade")%></p>

    <div>
      <button type="submit" class="botaoPrimario">Confirmar</button>
      <a class="botaoSecundario" href="${pageContext.request.contextPath}/ListarAdminsServlet">Voltar</a>
    </div>
  </form>
</main>
</body>

</html>