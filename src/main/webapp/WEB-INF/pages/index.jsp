<%@ page import="com.example.servletfluxar.model.Empresa" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.servletfluxar.model.Administrador" %>
<%@ page import="com.example.servletfluxar.model.Plano" %>
<%@ page import="com.example.servletfluxar.util.FormatoOutput" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
</head>

<body>
<%
    Integer paginaObjeto = (Integer) request.getAttribute("pagina");
    int pagina = (paginaObjeto != null) ? paginaObjeto : 1;
    List<Empresa> empresas = (List<Empresa>) request.getAttribute("empresas");
    String tipoUsuario = (String) request.getAttribute("tipoUsuario");
%>
<header>
    <div id="nome">
        <a href="${pageContext.request.contextPath}/MeuPerfilServlet?idUsuario=<%= tipoUsuario == "empresa" ?
                    ((Empresa) session.getAttribute("empresa")).getId() :
                    ((Administrador) session.getAttribute("administrador")).getId()%>">
            <%= tipoUsuario == "empresa" ?
                    ((Empresa) session.getAttribute("empresa")).getNome() :
                    ((Administrador) session.getAttribute("administrador")).getNome() + " " +
                            ((Administrador) session.getAttribute("administrador")).getSobrenome()%>
        </a>
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
                    <div class="text" id="atual">
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
                        <%if (tipoUsuario == "administrador") {%>
                        Assinaturas
                        <%} else {%>
                        Assinatura
                        <%}%>
                    </div>
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/ListarEmpresasServlet">
                    <div class="text">
                        <%if (tipoUsuario == "administrador") {%>
                        Empresas
                        <%} else {%>
                        Empresa
                        <%}%>
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
    <h1 id="title">Seja bem-vindo!</h1>
    <section id="secao-dados">
        <section class="dados">
            <%if (tipoUsuario.equals("administrador")) {%>
                <h2>Empresas cadastradas</h2>
                <p><%=request.getAttribute("numeroEmpresas")%></p>
            <%} else {%>
                <h2>Unidades</h2>
                <p><%=request.getAttribute("numeroUnidades")%></p>
            <%}%>
        </section>
        <section class="dados">
            <%if (tipoUsuario.equals("administrador")) {%>
                <h2>Empresas ativas</h2>
                <p><%=request.getAttribute("numeroEmpresasAtivas")%></p>
            <%} else {%>
                <h2>Setores</h2>
                <p><%=request.getAttribute("numeroSetores")%></p>
            <%}%>
        </section>
        <section class="dados">
            <%if (tipoUsuario.equals("administrador")) {%>
                <h2>Unidades ativas</h2>
                <p><%=request.getAttribute("numeroUnidadesAtivas")%></p>
            <%} else {%>
                <h2>Funcionários</h2>
                <p><%=request.getAttribute("numeroFuncionarios")%></p>
            <%}%>
        </section>
        <section class="dados">
            <%if (tipoUsuario.equals("administrador")) {%>
                <h2>Setores ativos</h2>
                <p><%=request.getAttribute("numeroSetoresAtivos")%></p>
            <%} else {%>
                <h2>Plano assinado</h2>
                <div>
                    <p><%=((Plano) request.getAttribute("planoAssinado")).getNome()%></p>
                    <p style="font-size: 24px"><%=FormatoOutput.duracao(((Plano) request.getAttribute("planoAssinado")).getTempo())%></p>
                </div>
            <%}%>
        </section>
        <section class="dados">
            <%if (tipoUsuario.equals("administrador")) {%>
                <h2>Funcionários ativos</h2>
                <p><%=request.getAttribute("numeroFuncionariosAtivos")%></p>
            <%} else {%>
                <h2>Data de início</h2>
                <p><%=FormatoOutput.data((LocalDate) request.getAttribute("dataInicio"))%></p>
            <%}%>
        </section>
        <section class="dados">
            <%if (tipoUsuario.equals("administrador")) {%>
                <h2>Plano mais vendido</h2>
                <div>
                    <p><%=((Plano) request.getAttribute("planoMaisAssinado")).getNome()%></p>
                    <p style="font-size: 24px"><%=FormatoOutput.duracao(((Plano) request.getAttribute("planoMaisAssinado")).getTempo())%></p>
                </div>
            <%} else {%>
                <h2>Data de fim</h2>
                <p><%=FormatoOutput.data((LocalDate) request.getAttribute("dataFim"))%></p>
            <%}%>
        </section>
    </section>
</main>
</body>

</html>