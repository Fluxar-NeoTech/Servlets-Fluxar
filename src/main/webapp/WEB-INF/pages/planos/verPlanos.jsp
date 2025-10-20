<%@ page import="com.example.servletfluxar.model.Plano" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listar planos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Assets/CSS/style.css">
</head>

<body>
    <%
        Integer paginaObjeto = (Integer) request.getAttribute("pagina");
        int pagina = (paginaObjeto!=null) ? paginaObjeto: 1;
        Map<Integer, Plano> planos = (Map<Integer, Plano>) request.getAttribute("planos");
    %>
    <header>
        <div id="nome">
            <p>Caio Marcos</p>
        </div>
    </header>
    <aside>
        <div class="maior">
            <img src="${pageContext.request.contextPath}/Assets/Images/logo.png"
                alt="Logo do aplicativo. Palavra Fluxar com letras roxas, porém o X tem um gradiente em cada linha (roxo ao azul) (laranja ao rosa)"
                id="logo">
        </div>

        <a href="">
            <div class="text">
                Home
            </div>
        </a>

        <a href="">
            <div class="text">
                Admin
            </div>
        </a>

        <a href="${pageContext.request.contextPath}/ListarPlanosServlet">
            <div class="text" id="atual">
                Planos
            </div>
        </a>

        <a href="">
            <div class="text">
                Assinaturas
            </div>
        </a>

        <a href="">
            <div class="text">
                Empresas
            </div>
        </a>

        <a href="">
            <div class="text">
                Unidades
            </div>
        </a>

        <a href="">
            <div class="text">
                Setores
            </div>
        </a>

        <a href="">
            <div class="text" id="func">
                Funcionarios
            </div>
        </a>

        <div class="maior" id="sair">
            <a id="sairB" href="${pageContext.request.contextPath}/index.html">Sair</a>
        </div>
    </aside>
    <main>
        <p id="title">Plano</p>
        <section id="topo">

            <form action="">
                <details>
                    <summary>Filtros</summary>
                    <button class="filtro">Id</button>
                    <button class="filtro">Preço</button>
                    <button class="filtro" id="filtroBottom">Duração</button>
                </details>
                
                <input type="text" id="search" name="vaorFiltro" placeholder="Valor do filtro...">

                <button type="submit" id="buscar">Buscar</button>
            </form>

            <a href="/ListarPlanosServlet?pagina=1" id="ver">Ver todos</a>
        </section>

        <table>
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Nome</th>
                    <th>Preço</th>
                    <th>Tempo</th>
                    <%
                        if (request.getAttribute("tipoUsuario").equals("administrador")){
                    %>
                        <th>Ações</th>
                    <%}%>
                </tr>
            </thead>
            <tbody>
                <%for(Plano plano: planos.values()){%>
                <tr>
                    <td><%=plano.getId()%></td>
                    <td><%=plano.getNome()%></td>
                    <td><%=plano.getPreco()%></td>
                    <td><%=plano.getTempo()%></td>
                    <%
                        if (request.getAttribute("tipoUsuario").equals("administrador")){
                    %>
                        <td>
                            <a>Editar</a>
                        </td>
                    <%}%>
                </tr>
                <%}%>
            </tbody>
        </table>

        <section id="footer">
            <%if (request.getAttribute("tipoUsuario").equals("administrador")) {%>
                <a id="add" href="${pageContext.request.contextPath}/AdicionarPlanoServlet">Adicionar</a>
            <%}%>

            <div id="pages">
                <a href="${pageContext.request.contextPath}/ListarPlanosServlet?pagina=<%=pagina - 1%>">
                    <svg width="10" height="17" viewBox="0 0 10 17" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path
                            d="M9.6176 0.374196C9.86245 0.613866 10 0.938885 10 1.27778C10 1.61667 9.86245 1.94169 9.6176 2.18136L3.15257 8.50772L9.6176 14.8341C9.85551 15.0751 9.98716 15.398 9.98418 15.7331C9.9812 16.0682 9.84385 16.3887 9.60169 16.6257C9.35954 16.8626 9.03196 16.997 8.68951 17C8.34706 17.0029 8.01715 16.874 7.77082 16.6412L0.382399 9.4113C0.137548 9.17163 0 8.84661 0 8.50772C0 8.16882 0.137548 7.8438 0.382399 7.60413L7.77082 0.374196C8.01575 0.134599 8.34789 0 8.69421 0C9.04053 0 9.37268 0.134599 9.6176 0.374196Z"
                            fill="#8200A8" />
                    </svg>
                </a>

                <div id="num">
                    <p id="text"><%=pagina%></p>
                </div>

                <a href="${pageContext.request.contextPath}/ListarPlanosServlet?pagina=<%= pagina + 1%>">
                    <svg width="10" height="17" viewBox="0 0 10 17" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path
                            d="M0.382399 0.374196C0.137549 0.613866 0 0.938885 0 1.27778C0 1.61667 0.137549 1.94169 0.382399 2.18136L6.84743 8.50772L0.382399 14.8341C0.144488 15.0751 0.0128435 15.398 0.0158193 15.7331C0.018795 16.0682 0.156153 16.3887 0.398309 16.6257C0.640464 16.8626 0.968042 16.997 1.31049 17C1.65294 17.0029 1.98285 16.874 2.22918 16.6412L9.6176 9.4113C9.86245 9.17163 10 8.84661 10 8.50772C10 8.16882 9.86245 7.8438 9.6176 7.60413L2.22918 0.374196C1.98425 0.134599 1.65211 0 1.30579 0C0.959466 0 0.627323 0.134599 0.382399 0.374196Z"
                            fill="#8200A8" />
                    </svg>
                </a>
            </div>
        </section>
    </main>
</body>

</html>