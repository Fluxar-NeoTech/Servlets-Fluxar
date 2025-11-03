<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/login.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/Assets/Icons/XFAVICOM%201.png">
    <title>Escolha do plano</title>
</head>

<body>
<header>
    <header class="laptop">
        <img src="${pageContext.request.contextPath}/Assets/Icons/FluxarLogoBRANCA.png"
             alt="Logo do aplicativo fluxar branco">
    </header>
</header>
<main>
    <article id="container-plano">
        <div>
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn-voltar">← Voltar</a>
            <h1 id="loginTitle" class="caixa__title">Renovação de assinatura</h1>
            <div class="caixa__sub">Selecione o plano que mais se adéqua as necessidades da sua empresa</div>
        </div>
        <form action="${pageContext.request.contextPath}/RenovarEscolhaPlanoServlet" method="get">
            <!-- Plano Essential -->
            <label for="essential" class="plano">
                <input type="radio" name="plano" id="essential" value="Essential" required hidden>
                <div class="plano-details">
                    <div class="nome">ESSENTIAL</div>
                    <div class="valores">
                        <p>Mensal: R$599,99/mês</p>
                        <p>Anual: R$499,99/mês</p>
                    </div>
                </div>
                <div class="botaoSecundario">Escolher</div>
            </label>

            <!-- Plano Profissional -->
            <label for="profissional" class="plano">
                <input type="radio" name="plano" id="profissional" value="Profissional" required hidden>
                <div class="plano-details">
                    <div class="nome">PROFISSIONAL</div>
                    <div class="valores">
                        <p>Mensal: R$899,99/mês</p>
                        <p>Anual: R$799,99/mês</p>
                    </div>
                </div>
                <div class="botaoSecundario">Escolher</div>
            </label>

            <!-- Plano Enterprise -->
            <label for="enterprise" class="plano">
                <input type="radio" name="plano" id="enterprise" value="Enterprise" required hidden>
                <div class="plano-details">
                    <div class="nome">ENTERPRISE</div>
                    <div class="valores">
                        <p>Mensal: R$1.599,99/mês</p>
                        <p>Anual: R$1.499,99/mês</p>
                    </div>
                </div>
                <div class="botaoSecundario">Escolher</div>
            </label>

            <%if (request.getAttribute("erro") != null) {%>
                <p class="erro-request"><%=request.getAttribute("erro")%></p>
            <%}%>

            <button type="submit" class="botaoPrimario">Continuar</button>
        </form>
    </article>
</main>
</body>

</html>
