<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/login.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/Assets/Icons/XFAVICOM%201.png">
    <title>Recuperação de senha</title>
</head>

<body>
<header class="laptop">
    <img src="${pageContext.request.contextPath}/Assets/Icons/FluxarLogoBRANCA.png" alt="Logo do aplicativo fluxar branco">
</header>
<main>
    <article>
        <div>
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn-voltar">← Cancelar</a>
            <h1 id="loginTitle" class="caixa__title">Recuperar senha</h1>
            <div class="caixa__sub">Digite seu email para enviarmos um código de vericação para ele, a fim de verificar a autenticidade da operação</div>
        </div>
        <form action="<%= request.getContextPath() %>/EsqueciSenhaEnviarCodigoServlet" method="post">
            <div class="<%=request.getAttribute("erroEmail") != null?"floating-label-erro":"floating-label"%>">
                <input type="email" class="<%=request.getAttribute("erroEmail") != null ? "inputs-erro": "inputs"%> userEmail" name="emailUsuario"
                       id="userEmail" placeholder=" " required>
                <label id="label" for="userEmail">Digite seu email aqui</label>
                <% if (request.getAttribute("erroEmail") != null) { %>
                <p class="erro">
                    <%= request.getAttribute("erroEmail") %>
                </p>
                <%}%>
            </div>

            <%if (request.getAttribute("erro") != null) {%>
            <p class="erro-request"><%=request.getAttribute("erro")%>
            </p>
            <%}%>

           <button type="submit" class="botaoPrimario">CONTINUAR</button>
        </form>
    </article>
</main>
</body>

</html>