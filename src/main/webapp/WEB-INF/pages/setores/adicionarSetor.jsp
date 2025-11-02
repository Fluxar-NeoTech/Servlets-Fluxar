<%@ page import="com.example.servletfluxar.model.Empresa" %>
<%@ page import="com.example.servletfluxar.model.Unidade" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Adicionar setor</title>
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
          <div class="text" id="atual">
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
  <p id="title">Adicionar setor</p>

  <form action="${pageContext.request.contextPath}/AdicionarSetorServlet" method="post">
    <label for="name">Nome:</label>
    <input type="text" name="nome" id="name" required>
    <p><%=request.getAttribute("erroNome")%></p>

    <select name = "idUnidade" required>
        <option selected hidden>Unidade</option>
        <%for (Unidade unidade: unidades) {%>
          <option value="<%=unidade.getId()%>"><%=unidade.getNome()%></option>
        <%}%>
    </select>

    <label for="descricao">Descrição:</label>
    <textarea id="descricao" name="descricao" rows="4" cols="50" placeholder="Digite a descricao"></textarea>
    <p><%=request.getAttribute("erroDescricao")%></p>

    <div>
      <button type="submit" class="botaoPrimario">Confirmar</button>
      <a class="botaoSecundario" href="${pageContext.request.contextPath}/ListarUnidadesServlet">Voltar</a>
    </div>
  </form>
</main>
</body>

</html>