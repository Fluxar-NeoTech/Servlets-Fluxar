<%@ page import="com.example.servletfluxar.model.Plano" %>
<%@ page import="com.example.servletfluxar.model.Administrador" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Alterar plano</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/Assets/Icons/XFAVICOM%201.png">
</head>

<body>
<%
    Plano plano = (Plano) request.getAttribute("plano");
    String tipoUsuario = (String) request.getAttribute("tipoUsuario");
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
                    <div class="text" id="atual">
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
    <div id="center">
        <p id="title">Alterar Plano</p>

        <form id="form" action="${pageContext.request.contextPath}/AlterarPlanoServlet" method="post">
            <label for="nome">Nome:</label>
            <input type="text" name="nome" id="name" value="<%=plano.getNome()%>" required>

            <div>
                <div style="display: flex;">
                    <input type="radio" id="anual" name="tempo" value="12" <%=plano.getTempo()==12? "checked": ""%> required>
                    <label for="anual">Anual</label>
                </div>
                <div style="display: flex;">
                    <input type="radio" id="mensal" name="tempo" value="1" <%=plano.getTempo()==1? "checked": ""%> required>
                    <label for="mensal">Mensal</label>
                </div>
            </div>

            <label for="preco">Preço:</label>
            <input type="text" name="preco" id="preco" value="<%=plano.getPreco()%>" required>

            <input type="hidden" name="id" value="<%=plano.getId()%>">

            <%if (request.getAttribute("erro") != null) {%>
            <p class="erro-request"><%=request.getAttribute("erro")%>
            </p>
            <%}%>

            <div>
                <button type="submit" class="botaoPrimario">Confirmar</button>
                <a class="botaoSecundario" href="${pageContext.request.contextPath}/ListarPlanosServlet">Voltar</a>
            </div>
        </form>
    </div>
</main>
</body>

</html>