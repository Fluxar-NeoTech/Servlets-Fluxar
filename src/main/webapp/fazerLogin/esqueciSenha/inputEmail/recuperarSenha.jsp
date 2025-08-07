<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/recuperarSenha.css">
    <title>Recuperação de senha</title>
</head>

<body>
<header>
    <a href="<%= request.getContextPath() %>paginaLogin/login.jsp" class="icon-back">
    </a>
    <h1>Recuperação de senha</h1>
</header>
<main>
    <article>
        <h2>Recuperar senha:</h2>
        <p>Digite seu email para enviarmos um código para ele para a recuperação da sua senha</p>
        <form action="<%= request.getContextPath() %>/EnviarCodigoServlet" method="post">
            <% if (request.getAttribute("erroEmail") !=null) { %>
            <div class="floating-label-erro">
                <input type="email" class="inputs-erro userEmail" id="userEmail" name="emailUsuario"
                       placeholder=" " required>
                <label id="label1" for="userEmail">Digite seu email aqui</label>
                <p class="erro">
                    <%= request.getAttribute("erroEmail") %>
                </p>
            </div>
            <% } else { %>
            <div class="floating-label">
                <input type="email" class="inputs userEmail" id="emailUsuario" name="emailUsuario"
                       placeholder=" " required>
                <label id="label2" for="emailUsuario">Digite seu email aqui</label>
            </div>
            <% } %>

           <button type="submit" class="botaoPrimario">CONTINUAR</button>
        </form>
    </article>
</main>
</body>

</html>