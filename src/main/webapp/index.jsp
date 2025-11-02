<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/login.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/Assets/Icons/XFAVICOM%201.png">
    <title>Entrar</title>
</head>

<body>
<header class="laptop">
    <img src="${pageContext.request.contextPath}/Assets/Icons/FluxarLogoBRANCA.png" alt="Logo do aplicativo fluxar branco">
</header>
<main>
    <article>
        <div>
            <a href="https://neotech-oj1r.onrender.com/" class="btn-voltar">← Voltar</a>
            <h1 id="loginTitle" class="caixa__title">Entrar no Fluxar</h1>
            <div class="caixa__sub">Gerencie seu estoque com mais segurança e rapidez</div>
        </div>
        <nav class="nav-login">
            <ul>
                <li>Não tem uma conta? <a href="${pageContext.request.contextPath}/pages/cadastro/inicioCadastro.jsp">Crie agora!</a></li>
            </ul>
        </nav>
        <form action="LoginServlet" method="post">
            <div class="<%=request.getAttribute("erroEmail") != null?"floating-label-erro":"floating-label"%>" style="width: 100%">
                <input type="email" class="<%=request.getAttribute("erroEmail") != null ? "inputs-erro": "inputs"%> userEmail" name="emailUsuario"
                       id="userEmail" placeholder=" " required>
                <label id="label" for="userEmail">Digite seu email aqui</label>
                <% if (request.getAttribute("erroEmail") != null) { %>
                <p class="erro">
                    <%= request.getAttribute("erroEmail") %>
                </p>
                <%}%>
            </div>

            <div id="senha">
                <div class="<%=request.getAttribute("erroSenha") != null?"floating-label-erro":"floating-label"%>">
                    <input type="password" class="<%=request.getAttribute("erroSenha") != null ? "inputs-erro": "inputs"%> userPassword" name="senhaUsuario"
                           id="userSenha" placeholder=" " required>
                    <label id="label2" for="userSenha">Digite sua senha aqui</label>
                    <button type="button" class="icon-toggle" aria-label="Mostrar senha" id="togglePwd">👁️</button>
                    <% if (request.getAttribute("erroSenha") != null) { %>
                        <p class="erro">
                            <%= request.getAttribute("erroSenha") %>
                        </p>
                    <%}%>
                </div>

                <nav class="nav-login">
                    <ul>
                        <li>Esqueceu sua senha? <a href="${pageContext.request.contextPath}/pages/esqueciSenha/digitarEmail.jsp">Clique aqui!</a></li>
                    </ul>
                </nav>
            </div>

            <%if (request.getAttribute("erro") != null) {%>
            <p class="erro-request"><%=request.getAttribute("erro")%>
            </p>
            <%}%>

            <button type="submit" class="botaoPrimario">Entrar</button>
        </form>
        <nav class="nav-login" id="analista">
            <ul>
                <li><a href="https://web-site-fluxar.vercel.app/" target="_blank">Entrar como analista</a></li>
            </ul>
        </nav>
    </article>
</main>
<script>
// Toggle de visibilidade da senha
const toggle = document.getElementById('togglePwd');
const pwd = document.getElementById('userSenha');

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