<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cadastrar senha</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/login.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/Assets/Icons/XFAVICOM%201.png">
</head>
<body>
<header class="laptop">
    <img src="${pageContext.request.contextPath}/Assets/Icons/FluxarLogoBRANCA.png" alt="Logo do aplicativo fluxar branco">
</header>
<main>
    <article>
        <div>
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn-voltar">← Cancelar cadastro</a>
            <h1 id="loginTitle" class="caixa__title">Senha</h1>
            <div class="caixa__sub">Escolha sua senha para acessar nosso site</div>
        </div>
        <form action="${pageContext.request.contextPath}/CadastroValidarSenhaServlet" method="post">
            <div class="<%=request.getAttribute("erroSenha") != null?"floating-label-erro":"floating-label"%>">
                <input type="text" class="<%=request.getAttribute("erroSenha") != null ? "inputs-erro": "inputs"%>" name="senha"
                       id="userSenha" placeholder=" " required>
                <label for="userSenha">Digite sua senha aqui</label>
                <% if (request.getAttribute("erroSenha") != null) { %>
                <p class="erro">
                    <%= request.getAttribute("erroSenha") %>
                </p>
                <%}%>
            </div>
            <div class="<%=request.getAttribute("erroConfSenha") != null?"floating-label-erro":"floating-label"%>" style="width: 100%;">
                <input type="password" class="<%=request.getAttribute("erroConfSenha") != null ? "inputs-erro": "inputs"%> userPassword" name="senhaConfirmada"
                       id="userConfSenha" placeholder=" " required>
                <label for="userConfSenha">Confirme sua senha aqui</label>
                <button type="button" class="icon-toggle" aria-label="Mostrar senha" id="togglePwd">👁️</button>
                <% if (request.getAttribute("erroConfSenha") != null) { %>
                <p class="erro">
                    <%= request.getAttribute("erroConfSenha") %>
                </p>
                <%}%>
            </div>

            <button type="submit" class="botaoPrimario">CONFIRMAR</button>
        </form>
    </article>
</main>
<script>
    // Toggle de visibilidade da senha
    const toggle = document.getElementById('togglePwd');
    const pwd = document.getElementById('userConfSenha');

    if (toggle && pwd) {
        toggle.addEventListener('click', () => {
            const show = pwd.getAttribute('type') === 'password';
            pwd.setAttribute('type', show ? 'text' : 'password');
            toggle.textContent = show ? '🙈' : '👁️';
            toggle.setAttribute('aria-pressed', show ? 'true' : 'false');
        });
    }

    // Animação de label para inputs já preenchidos
    document.querySelectorAll('.userPassword').forEach(input => {
        if (input.value.trim() !== '') {
            input.dispatchEvent(new Event('input', { bubbles: true }));
        }

        input.addEventListener('input', () => {
            // força o navegador a aplicar :placeholder-shown fallback se necessário
            // aqui você pode adicionar animação CSS se quiser
        });
    });
</script>
</body>
</html>
