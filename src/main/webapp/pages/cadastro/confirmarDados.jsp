<%@ page import="com.example.servletfluxar.model.Empresa" %>
<%@ page import="com.example.servletfluxar.util.FormatoOutput" %><%--
  Created by IntelliJ IDEA.
  User: caiomaciel-ieg
  Date: 02/11/2025
  Time: 08:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/login.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/Assets/Icons/XFAVICOM%201.png">
    <title>Cadastro</title>
</head>

<%
    Empresa empresa = (Empresa) request.getAttribute("empresa");
    String plano = (String) request.getAttribute("plano");
    double precoMensal = (Double) request.getAttribute("precoMensal");
    double precoAnual = (Double) request.getAttribute("precoAnual");
%>

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
            <a href="${pageContext.request.contextPath}/pages/cadastro/escolhaPlano.jsp" class="btn-voltar">← Voltar</a>
            <h1 id="loginTitle" class="caixa__title">Confirmar dados</h1>
            <div class="caixa__sub">Confirme os dados informados e informe a duração do plano</div>
        </div>

        <form action="${pageContext.request.contextPath}/ConfirmarDadosCadastroServlet" method="get">
            <p>Nome da empresa: <%=empresa.getNome()%></p>
            <p>CNPJ da empresa: <%=FormatoOutput.cnpj(empresa.getCnpj())%></p>
            <p>Email da empresa: <%=empresa.getEmail()%></p>
            <p>Plano: <%=plano%></p>

            <div style="width: 100%">
                <div style="display: flex">
                    <input type="radio" id="anual" name="tempo" value="12" width="20px" height="20px" required>
                    <label for="anual" style="white-space: nowrap;">Anual : <%=FormatoOutput.preco(precoAnual)%>/mês</label>
                </div>
                <div style="display: flex; width: fit-content; margin-left: 0">
                    <input type="radio" id="mensal" name="tempo" value="1" width="20px" height="20px" required>
                    <label for="mensal" style="white-space: nowrap;">Mensal: <%=FormatoOutput.preco(precoMensal)%>/mês</label>
                </div>
            </div>

            <%if (request.getAttribute("erro") != null) {%>
                <p class="erro-request"><%=request.getAttribute("erro")%>
                </p>
            <%}%>

            <button type="submit" class="botaoPrimario">Continuar</button>
        </form>
        <nav class="nav-login" id="analista">
            <ul>
                <li>Informação errada? <a href="${pageContext.request.contextPath}/pages/cadastro/inicioCadastro.jsp">Clique aqui!</a></li>
            </ul>
        </nav>
    </article>
</main>
</body>
</html>
