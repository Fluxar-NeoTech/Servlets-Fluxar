<%@ page import="com.example.servletfluxar.model.*" %>
<%@ page import="com.example.servletfluxar.util.FormatoOutput" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Deletar funcionário</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/Assets/Icons/XFAVICOM%201.png">
</head>

<body>
<%
    Empresa empresa = (Empresa) request.getAttribute("empresa");
    Setor setor = (Setor) request.getAttribute("setor");
    Unidade unidade = (Unidade) request.getAttribute("unidade");
    Funcionario funcionario = (Funcionario) request.getAttribute("funcionario");
%>
<header>
    <div id="nome">
        <p>
            <%=((Empresa) session.getAttribute("empresa")).getNome()%>
        </p>
    </div>
</header>
<aside>
    <div class="maior">
        <img src="${pageContext.request.contextPath}/Assets/Images/logo.png"
             alt="Logo do aplicativo. Palavra Fluxar com letras roxas, porém o X tem um gradiente em cada linha (roxo ao azul) (laranja ao rosa)"
             id="logo">
    </div>

    <nav>
        <ul class="linksServlet">
            <li>
                <a href="${pageContext.request.contextPath}/HomeServlet">
                    <div class="text">
                        Home
                    </div>
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/ListarPlanosServlet">
                    <div class="text">
                        Planos
                    </div>
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/ListarAssinaturasServlet">
                    <div class="text">
                        Assinatura
                    </div>
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/ListarEmpresasServlet">
                    <div class="text">
                        Empresa
                    </div>
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/ListarUnidadesServlet">
                    <div class="text">
                        Unidades
                    </div>
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/ListarSetoresServlet">
                    <div class="text">
                        Setores
                    </div>
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/ListarFuncionariosServlet">
                    <div class="text func" id="atual">
                        Funcionarios
                    </div>
                </a>
            </li>
        </ul>
    </nav>

    <div>
        <a class="botaoPrimario" href="${pageContext.request.contextPath}/SairServlet">Sair</a>
    </div>
</aside>
<main>
    <p id="title">Deletar funcionário</p>

    <form id="form" action="${pageContext.request.contextPath}/RemoverFuncionarioServlet" method="post">
        <table class="confirmarDelecao" style="border-radius: 20px">
            <thead>
            <tr>
                <th colspan="2">Funcionário</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>Nome da empresa</td>
                <td style="border-left: solid 1px"><%=empresa.getNome()%>
                </td>
            </tr>
            <tr>
                <td>Nome da unidade</td>
                <td style="border-left: solid 1px"><%=unidade.getNome()%>
                </td>
            </tr>
            <tr>
                <td>Nome do setor</td>
                <td style="border-left: solid 1px"><%=setor.getNome()%>
                </td>
            </tr>
            <tr>
                <td>Nome</td>
                <td style="border-left: solid 1px"><%=FormatoOutput.nome(funcionario.getNome(), funcionario.getSobrenome())%>
                </td>
            </tr>
            <tr>
                <td>Email</td>
                <td style="border-left: solid 1px"><%=funcionario.getEmail()%>
                </td>
            </tr>
            <tr>
                <td>Cargo</td>
                <td style="border-left: solid 1px"><%=funcionario.getCargo()%>
                </td>
            </tr>
            </tbody>
        </table>

        <%if (request.getAttribute("erro") != null) {%>
        <p class="erro-request"><%=request.getAttribute("erro")%>
        </p>
        <%}%>

        <input type="hidden" name="id" value="<%=funcionario.getId()%>" required>
        <div id="center">
            <button type="submit" class="botaoPrimario">Confirmar</button>

            <a class="botaoSecundario" href="${pageContext.request.contextPath}/ListarFuncionariosServlet">Cancelar</a>
        </div>
    </form>
</main>
</body>
</html>