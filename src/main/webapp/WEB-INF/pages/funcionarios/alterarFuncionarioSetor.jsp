<%@ page import="com.example.servletfluxar.model.Empresa" %>
<%@ page import="com.example.servletfluxar.model.Setor" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.servletfluxar.model.Funcionario" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Alterar funcionário</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>
<%
  List<Setor> setores = (List<Setor>) request.getAttribute("setores");
  Funcionario funcionario = (Funcionario) request.getAttribute("funcionario");
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
  <p id="title">Alterar funcionário</p>

  <form action="${pageContext.request.contextPath}/AlterarFuncionarioSetorServlet" method="post">

    <div>
      <input type="radio" id="analista" name="cargo" value="Analista"
        <%=funcionario.getCargo().equals("Analista")? "checked": ""%> required>
      <label for="analista">Analista</label>
      <input type="radio" id="gestor" name="cargo" value="Gestor"
        <%=funcionario.getCargo().equals("Gestor")? "checked": ""%> required>
      <label for="gestor">Gestor</label>
    </div>
    <p><%= request.getAttribute("erroCargo")%>
    </p>

    <select name = "idSetor" required>
      <%for (Setor setor: setores) {
        if(setor.getId() != funcionario.getIdSetor()){%>
          <option value="<%=setor.getId()%>"><%=setor.getNome()%></option>
        <%} else {%>
          <option value="<%=setor.getId()%>" selected><%=setor.getNome()%></option>
        <%}%>
      <%}%>
    </select>

    <div>
      <button type="submit" class="botaoPrimario">Confirmar</button>
      <a class="botaoSecundario" href="${pageContext.request.contextPath}/AlterarFuncionarioUnidadeServlet?id=<%=funcionario.getId()%>">Voltar</a>
    </div>
  </form>
</main>
</body>

</html>