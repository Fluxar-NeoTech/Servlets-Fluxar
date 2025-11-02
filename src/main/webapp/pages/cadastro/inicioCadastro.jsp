<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/login.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/Assets/Icons/XFAVICOM%201.png">
    <title>Cadastro</title>
</head>

<body>
<header>
    <header class="laptop">
        <img src="${pageContext.request.contextPath}/Assets/Icons/FluxarLogoBRANCA.png"
             alt="Logo do aplicativo fluxar branco">
    </header>
</header>
<main>
    <article>
        <div>
            <a href="https://neotech-oj1r.onrender.com/" class="btn-voltar">← Voltar para home</a>
            <h1 id="loginTitle" class="caixa__title">Cadastrar empresa</h1>
            <div class="caixa__sub">Com alguns cliques sua produtividade disparará</div>
        </div>

        <form action="${pageContext.request.contextPath}/InicioCadastroServlet" method="get">
            <div class="<%=request.getAttribute("erroNome") != null?"floating-label-erro":"floating-label"%>"
                 style="width: 100%">
                <input type="text"
                       class="<%=request.getAttribute("erroNome") != null ? "inputs-erro": "inputs"%> userNome"
                       name="nome"
                       id="userEmail" placeholder=" " required>
                <label id="label" for="userEmail">Digite o nome da empresa</label>
                <% if (request.getAttribute("erroNome") != null) { %>
                <p class="erro">
                    <%= request.getAttribute("erroNome") %>
                </p>
                <%}%>
            </div>

            <div class="<%=request.getAttribute("erroCnpj") != null?"floating-label-erro":"floating-label"%>"
                 style="width: 100%">
                <input type="text"
                       class="<%=request.getAttribute("erroCnpj") != null ? "inputs-erro": "inputs"%> userCnpj"
                       name="cnpj"
                       id="userCnpj" placeholder=" " required>
                <label id="label" for="userCNPJ">Digite o seu cnpj aqui</label>
                <% if (request.getAttribute("erroCnpj") != null) { %>
                <p class="erro">
                    <%= request.getAttribute("erroCnpj") %>
                </p>
                <%}%>
            </div>

            <div class="<%=request.getAttribute("erroEmail") != null?"floating-label-erro":"floating-label"%>"
                 style="width: 100%">
                <input type="email"
                       class="<%=request.getAttribute("erroEmail") != null ? "inputs-erro": "inputs"%> userEmail"
                       name="email"
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

            <button type="submit" class="botaoPrimario">Continuar</button>
        </form>
        <nav class="nav-login" id="analista">
            <ul>
                <li>Já tem conta? <a href="${pageContext.request.contextPath}/index.jsp">Entrar</a></li>
            </ul>
        </nav>
    </article>
</main>
</body>

</html>