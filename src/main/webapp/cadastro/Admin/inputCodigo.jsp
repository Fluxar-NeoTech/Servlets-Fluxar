<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/codigo.css">
  <title>Código</title>
</head>

<body>
<header>
  <a href="escolherAdmin.jsp" class="icon-back">
  </a>
  <h1>Recuperação de senha</h1>
</header>
<main>
  <article>
    <h2>Código:</h2>
    <p>Digite seu email para enviarmos um código para ele para a recuperação da sua senha</p>
    <form action="<%= request.getContextPath() %>/EscreverCodigoServlet" method="post">
      <% if (request.getAttribute("erroCodigo") !=null) { %>
      <div class="floating-label-erro">
        <input type="text" class="inputs-erro codigo" id="codigo1" name="codigo"
               placeholder=" " required>
        <label id="label1" for="codigo1">Digite o código aqui</label>
        <p class="erro">
          <%= request.getAttribute("erroCodigo") %>
        </p>
      </div>
      <% } else { %>
      <div class="floating-label">
        <input type="text" class="inputs codigo" id="codigo2" name="codigo"
               placeholder=" " required>
        <label id="label2" for="codigo2">Digite o código aqui</label>
      </div>
      <% } %>

      <button type="submit" class="botaoPrimario">CONTINUAR</button>
    </form>
  </article>
</main>
</body>

</html>