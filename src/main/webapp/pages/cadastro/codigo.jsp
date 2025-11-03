<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/login.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/Assets/Icons/XFAVICOM%201.png">
    <title>Código</title>
</head>

<body>
<header class="laptop">
    <img src="${pageContext.request.contextPath}/Assets/Icons/FluxarLogoBRANCA.png" alt="Logo do aplicativo fluxar branco">
</header>
<main>
    <article id="recebeCodigo">
        <div>
            <a href="${pageContext.request.contextPath}/pages/cadastro/confirmarDados.jsp" class="btn-voltar">← Voltar</a>
            <h1 id="loginTitle" class="caixa__title">Código</h1>
            <div class="caixa__sub">Digite o código que enviamos para o seu email</div>
        </div>
        
        <form action="${pageContext.request.contextPath}/CadastroVerificarCodigoServlet" method="post">
            <div class="codigo-inputs <%= request.getAttribute("erroCodigo") != null ? "erroCodigo" : "" %>">
                <input type="text" name="codigo1" maxlength="1" required>
                <input type="text" name="codigo2" maxlength="1" required>
                <input type="text" name="codigo3" maxlength="1" required>
                <input type="text" name="codigo4" maxlength="1" required>
                <input type="text" name="codigo5" maxlength="1" required>
                <input type="text" name="codigo6" maxlength="1" required>
            </div>

            <% if (request.getAttribute("erroCodigo") != null) { %>
                <p class="erro-request"><%= request.getAttribute("erroCodigo") %></p>
            <% } %>

            <button type="submit" class="botaoPrimario">Continuar</button>
        </form>
    </article>
</main>
<script>
    const inputs = document.querySelectorAll('.codigo-inputs input');
    inputs.forEach((input, index) => {
        input.addEventListener('input', () => {
            if (input.value.length === 1 && index < inputs.length - 1) {
                inputs[index + 1].focus();
            }
        });
        input.addEventListener('keydown', (e) => {
            if (e.key === "Backspace" && input.value === "" && index > 0) {
                inputs[index - 1].focus();
            }
        });
    });
</script>
</body>

</html>