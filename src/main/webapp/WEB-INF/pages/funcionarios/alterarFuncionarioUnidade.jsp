<%@ page import="java.util.List" %>
<%@ page import="com.example.servletfluxar.model.*" %>
<%@ page import="com.example.servletfluxar.util.FormatoOutput" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Alterar funcionário</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/Assets/Icons/XFAVICOM%201.png">
</head>

<body>
<%
    Funcionario funcionario = (Funcionario) request.getAttribute("funcionario");
    Setor setor = (Setor) request.getAttribute("setor");
    List<Unidade> unidades = (List<Unidade>) request.getAttribute("unidades");
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
                        Assinaturas
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
    <div id="center">
        <p id="title">Alterar funcionário</p>

        <form action="${pageContext.request.contextPath}/AlterarFuncionarioUnidadeServlet" method="post">
            <label for="name">Nome:</label>
            <input type="text" name="nomeCompleto" id="name"
                value="<%=FormatoOutput.nome(funcionario.getNome(), funcionario.getSobrenome())%>">
            <p><%= request.getAttribute("erroNome")%>
            </p>

            <label for="email">Email:</label>
            <input type="email" name="email" id="email" value="<%=funcionario.getEmail()%>">
            <p><%= request.getAttribute("erroEmail")%>
            </p>

            <%if (!unidades.isEmpty()){%>
            <select name="idUnidade">
                <%for (Unidade unidade : unidades) {%>
                <%if (unidade.getId() != setor.getIdUnidade()) {%>
                <option value="<%=unidade.getId()%>"><%=unidade.getNome()%>
                </option>
                <%} else {%>
                <option value="<%=unidade.getId()%>" selected><%=unidade.getNome()%>
                </option>
                <%}%>
                <%}%>
            </select>
            <p><%= request.getAttribute("erroIdUnidade")%>
            </p>
            <%}%>

            <%if (request.getAttribute("erro") != null) {%>
            <p class="erro-request"><%=request.getAttribute("erro")%>
            </p>
            <%}%>

            <input type="hidden" name="id" value="<%=funcionario.getId()%>" required>

            <div>
                <button type="submit" class="botaoPrimario">Confirmar</button>
                <a class="botaoSecundario" href="${pageContext.request.contextPath}/ListarAdminsServlet">Voltar</a>
            </div>
        </form>
    </div>
</main>
</body>

</html>