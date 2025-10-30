<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/recuperarSenha.css">
    <title>Alteração de senha</title>
</head>

<body>
<header>
    <a href="../../paginaLogin/login.jsp" class="icon-back">
    </a>
    <h1>Alterando senha</h1>
</header>
<main>
    <article>
        <h2>Nova senha:</h2>
        <p>Escolha sua nova senha para acessar nosso site</p>
        <form action="<%= request.getContextPath() %>/VerificarSenhaServlet" method="post">
            <% if (request.getAttribute("erroSenha") !=null) { %>
            <div class="floating-label-erro">
                <input type="password" class="inputs-erro" name="novaSenha"
                       placeholder=" " required>
                <label>Digite sua nova senha aqui</label>
                <p class="erro">
                    <%= request.getAttribute("erroSenha") %>
                </p>
            </div>
            <% } else { %>
            <div class="floating-label">
                <input type="password" class="inputs" name="novaSenha"
                       placeholder=" " required>
                <label>Digite sua nova senha aqui</label>
            </div>
            <% } %>
            <% if (request.getAttribute("erroConfSenha") !=null) { %>
            <div class="floating-label-erro">
                <input type="password" class="inputs-erro" name="senhaConfirmada"
                       placeholder=" " required>
                <label>Confirme sua nova senha</label>
                <p class="erro">
                    <%= request.getAttribute("erroConfSenha") %>
                </p>
            </div>
            <% } else { %>
            <div class="floating-label">
                <input type="password" class="inputs-erro" name="senhaConfirmada"
                       placeholder=" " required>
                <label>Confirme sua nova senha</label>
            </div>
            <% } %>

           <button type="submit" class="botaoPrimario">CONFIRMAR</button>
        </form>
    </article>
</main>
</body>