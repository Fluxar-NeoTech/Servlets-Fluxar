<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String nomeEmpresa = (String) session.getAttribute("nomeEmpresa");%>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/login.css">
    <title>Cadastro Administrador</title>
</head>

<body>
<header>
    <a href="../duracao/duracao.jsp" class="icon-back">
    </a>
    <h1>Cadastrando administrador</h1>
</header>
<main>
    <article>
        <h2>Cadastre o email do Administrador:</h2>
        <p>É necessário ter uma conta de administrador para gerir as informações da empresa, digite o email
            do administrador da empresa <%= nomeEmpresa %>, iremos enviar um email para ele com um código
        </p>
        <form action="<%= request.getContextPath() %>/EnviarCodigoServlet" method="post">
            <% if (request.getAttribute("erroEmail") != null) { %>
            <div class="floating-label-erro">
                <input type="email" class="inputs-erro adminEmail" name="emailAdmin" id="adminEmail"
                       placeholder=" " required>
                <label id="label1" for="adminEmail">Digite o email do admin</label>
                <p class="erro">
                    <%= request.getAttribute("erroEmail") %>
                </p>
            </div>
            <% } else { %>
            <div class="floating-label">
                <input type="email" class="inputs adminEmail" name="emailAdmin" id="adminEmai"
                       placeholder=" " required>
                <label id="label2" for="adminEmai">Digite o email de admin</label>
            </div>
            <% } %>
            <button type="submit" class="botaoPrimario">CONTINUAR</button>
        </form>
    </article>
</main>
</body>

</html>