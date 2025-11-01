<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/login.css">
    <title>Entrar</title>
</head>

<body>
<header class="laptop">
    <img src="${pageContext.request.contextPath}/Assets/Icons/FluxarLogoBRANCA.png" alt="Logo do aplicativo fluxar branco">
</header>
<main>
    <article>
        <div>
            <h1 id="loginTitle" class="caixa__title">Entrar no Fluxar</h1>
            <div class="caixa__sub">Gerencie seu estoque com mais segurança e rapidez</div>
        </div>
        <nav class="nav-login">
            <ul>
                <li>Não tem uma conta? <a href="">Crie agora!</a></li>
            </ul>
        </nav>
        <form action="LoginServlet" method="post">

            <% if (request.getAttribute("erroEmail") != null) { %>
            <div class="floating-label-erro">
                <input type="text" class="inputs-erro userEmail" id="userEmail" name="emailUsuario"
                       placeholder=" " required>
                <label id="label1" for="userEmail">Digite seu email aqui</label>
                <p class="erro">
                    <%= request.getAttribute("erroEmail") %>
                </p>
            </div>
            <% } else { %>
            <div class="floating-label">
                <input type="text" class="inputs userEmail" id="emailUsuario" name="emailUsuario"
                       placeholder=" " required>
                <label id="label2" for="emailUsuario">Digite seu email aqui</label>
            </div>
            <% } %>

            <div id="senha">
                <% if (request.getAttribute("erroSenha") != null) { %>
                <div class="floating-label-erro">
                    <input type="password" class="inputs-erro userPassword" name="senhaUsuario"
                           id="userSenha" placeholder=" " required>
                    <label id="label3" for="userSenha">Digite sua senha aqui</label>
                    <p class="erro">
                        <%= request.getAttribute("erroSenha") %>
                    </p>
                </div>
                <% } else {%>
                <div class="floating-label">
                    <input type="password" class="inputs userPassword" name="senhaUsuario"
                           id="userPassword" placeholder=" " required>
                    <label id="label4" for="userPassword">Digite sua senha aqui</label>
                </div>
                <%}%>
                <nav class="nav-login">
                    <ul>
                        <li>Esqueceu sua senha? <a href="">Clique aqui!</Esqueceu></a></li>
                    </ul>
                </nav>
            </div>

            <button type="submit" class="botaoPrimario">Entrar</button>
        </form>
        <nav class="nav-login" id="analista">
            <ul>
                <li><a href="">Entrar como analista</a></li>
            </ul>
        </nav>
    </article>
</main>

</body>

</html>