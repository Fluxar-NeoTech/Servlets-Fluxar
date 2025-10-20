<%@ page import="com.example.servletfluxar.model.Plano" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Listar planos</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Assets/CSS/style.css">
</head>

<body>
<%
  Plano plano = (Plano) request.getAttribute("plano");
  request.setAttribute("ativo", true);
%>
<header>
  <div id="nome">
    <p>Caio Marcos</p>
  </div>
</header>
<aside>
  <div class="maior">
    <img src="${pageContext.request.contextPath}/Assets/Images/logo.png"
         alt="Logo do aplicativo. Palavra Fluxar com letras roxas, porém o X tem um gradiente em cada linha (roxo ao azul) (laranja ao rosa)"
         id="logo">
  </div>

  <a href="">
    <div class="text">
      Home
    </div>
  </a>

  <a href="">
    <div class="text">
      Admin
    </div>
  </a>

  <a href="${pageContext.request.contextPath}/ListarPlanosServlet">
    <div class="text" id="atual">
      Planos
    </div>
  </a>

  <a href="">
    <div class="text">
      Assinaturas
    </div>
  </a>

  <a href="${pageContext.request.contextPath}/ListarEmpresasServlet">
    <div class="text">
      Empresas
    </div>
  </a>

  <a href="">
    <div class="text">
      Unidades
    </div>
  </a>

  <a href="">
    <div class="text">
      Setores
    </div>
  </a>

  <a href="">
    <div class="text" id="func">
      Funcionarios
    </div>
  </a>

  <div class="maior" id="sair">
    <a id="sairB" href="${pageContext.request.contextPath}%>/index.html">Sair</a>
  </div>
</aside>
<main>
  <p id="title">Alterar Plano</p>

  <form action="${pageContext.request.contextPath}/AdicionarPlanoServlet" method="post">
    <label for="nome">Nome:</label>
    <input type="text" name="nome" id="name" value="<%= plano.getNome()%>">

    <div>
      <input type="radio" id="anual" name="tempo" value="12" <%= plano.getTempo()==12? "checked": ""%>>
      <label for="anual">Anual</label>
      <input type="radio" id="mensal" name="tempo" value="1" <%= plano.getTempo()==1? "checked": ""%>>
      <label for="mensal">Mensal</label>
    </div>

    <label for="preco">Preço:</label>
    <input type="text" name="preco" id="preco" value="<%= plano.getPreco()%>">

    <div>
      <button type="submit">Confirmar</button>
      <a id="add" href="${pageContext.request.contextPath}/ListarPlanosServlet">Voltar</a>
    </div>
  </form>
</main>
</body>

</html>