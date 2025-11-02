<%@ page import="com.example.servletfluxar.model.Administrador" %>
<%@ page import="com.example.servletfluxar.model.Empresa" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Adicionar unidade</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/Assets/Icons/XFAVICOM%201.png">
</head>

<body>
<%
    String tipoUsuario = (String) request.getAttribute("tipoUsuario");
%>
<header>
    <div id="nome">
        <p>
            <%= tipoUsuario == "empresa" ?
                    ((Empresa) session.getAttribute("empresa")).getNome() :
                    ((Administrador) session.getAttribute("administrador")).getNome() + " " +
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

            <%if (tipoUsuario.equals("administrador")) { %>
            <li>
                <a href="${pageContext.request.contextPath}/ListarAdminsServlet">
                    <div class="text">
                        Admins
                    </div>
                </a>
            </li>
            <%}%>

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
                        Empresas
                    </div>
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/ListarUnidadesServlet">
                    <div class="text" id="atual">
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
    <p id="title">Adicionar unidade</p>

    <form action="${pageContext.request.contextPath}/AdicionarUnidadeServlet" method="post">
        <label for="name">Nome:</label>
        <input type="text" name="nome" id="name" required>
        <p><%=request.getAttribute("erroNome")%>
        </p>

        <label for="cnpj">CNPJ:</label>
        <input type="text" name="cnpj" id="cnpj" required>
        <p><%=request.getAttribute("erroCnpj")%>
        </p>

        <label for="email">Email:</label>
        <input type="email" name="email" id="email" required>
        <p><%=request.getAttribute("erroEmail")%>
        </p>

        <label for="cep">CEP:</label>
        <input type="text" name="cep" id="cep">
        <p><%=request.getAttribute("erroCep")%>
        </p>

        <label for="numero">Número:</label>
        <input type="text" name="numero" id="numero" required>
        <p><%=request.getAttribute("erroNumero")%>
        </p>

        <label for="complemento">Complemento:</label>
        <textarea id="complemento" name="complemento" rows="4" cols="50" placeholder="Digite o complemento"></textarea>

        <%if (request.getAttribute("erro") != null) {%>
        <p class="erro-request"><%=request.getAttribute("erro")%>
        </p>
        <%}%>

        <div>
            <button type="submit" class="botaoPrimario">Confirmar</button>
            <a class="botaoSecundario" href="${pageContext.request.contextPath}/ListarUnidadesServlet">Voltar</a>
        </div>
    </form>
</main>
</body>

</html>