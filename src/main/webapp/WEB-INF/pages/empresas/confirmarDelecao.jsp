<%@ page import="com.example.servletfluxar.model.Empresa" %>
<%@ page import="com.example.servletfluxar.model.Administrador" %>
<%@ page import="com.example.servletfluxar.util.FormatoOutput" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Deletar empresa</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/Assets/Icons/XFAVICOM%201.png">
</head>

<body>
<%
    Empresa empresa = (Empresa) request.getAttribute("empresa");
%>
<header>
    <div id="nome">
        <p>
            <%=((Administrador) session.getAttribute("administrador")).getNome() + " " +
                    ((Administrador) session.getAttribute("administrador")).getSobrenome()%>
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
                <a href="${pageContext.request.contextPath}/ListarAdminsServlet">
                    <div class="text">
                        Admins
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
                        Assinaturas
                    </div>
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/ListarEmpresasServlet">
                    <div class="text" id="atual">
                        Empresas
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
                    <div class="text func">
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
    <p id="title">Deletar empresa</p>

    <form id="form" action="${pageContext.request.contextPath}/RemoverEmpresaServlet" method="post">
        <table class="confirmarDelecao" style="border-radius: 20px">
            <thead>
            <tr>
                <th colspan="2">Empresa</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>Id</td>
                <td style="border-left: solid 1px"><%=empresa.getId()%>
                </td>
            </tr>
            <tr>
                <td>Nome</td>
                <td style="border-left: solid 1px"><%=empresa.getNome()%>
                </td>
            </tr>
            <tr>
                <td>Cnpj</td>
                <td style="border-left: solid 1px"><%=FormatoOutput.cnpj(empresa.getCnpj())%>
                </td>
            </tr>
            <tr>
                <td>Email</td>
                <td style="border-left: solid 1px"><%=empresa.getEmail()%>
                </td>
            </tr>
            <tr>
                <td>Data cadastro</td>
                <td style="border-left: solid 1px"><%=FormatoOutput.data(empresa.getDtCadastro())%>
                </td>
            </tr>
            </tbody>
        </table>

        <%if (request.getAttribute("erro") != null) {%>
        <p class="erro-request"><%=request.getAttribute("erro")%>
        </p>
        <%}%>

        <input type="hidden" name="id" value="<%=empresa.getId()%>" required>
        <div id="center">
            <button type="submit" class="botaoPrimario">Confirmar</button>

            <a class="botaoSecundario" href="${pageContext.request.contextPath}/ListarEmpresasServlet">Cancelar</a>
        </div>
    </form>
</main>
</body>

</html>