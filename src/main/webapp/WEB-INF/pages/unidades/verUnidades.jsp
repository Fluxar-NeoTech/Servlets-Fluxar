<%@ page import="com.example.servletfluxar.model.Plano" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.servletfluxar.model.Unidade" %>
<%@ page import="com.example.servletfluxar.model.Administrador" %>
<%@ page import="com.example.servletfluxar.model.Empresa" %>
<%@ page import="com.example.servletfluxar.util.FormatoOutput" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listar Unidades</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
</head>

<body>
    <%
        Integer paginaObjeto = (Integer) request.getAttribute("pagina");
        int pagina = (paginaObjeto!=null) ? paginaObjeto: 1;
        List<Unidade> unidades = (List<Unidade>) request.getAttribute("unidades");
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
                                ((Administrador) session.getAttribute("administrador")).getSobrenome()%></a>
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
        <p id="title">Unidades</p>
        <section id="topo">

            <form action="" id="filtro">
                <details>
                    <summary>Filtros</summary>
                    <button class="filtro">Id</button>
                    <button class="filtro">Preço</button>
                    <button class="filtro" id="filtroBottom">Duração</button>
                </details>
                
                <input type="text" id="search" name="valorFiltro" placeholder="Valor do filtro...">

                <button type="submit" class="botaoPrimario">Buscar</button>
            </form>

            <a href="${pageContext.request.contextPath}/ListarUnidadesServlet" class="botaoSecundario">Ver todas</a>
        </section>


        <%if (!unidades.isEmpty()){%>
        <table style=<%=tipoUsuario.equals("administrador") ? "--cols:6;" : "--cols:7;"%>>
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Nome</th>
                    <th>Cnpj</th>
                    <th>Email</th>
                    <th>Cep</th>
                    <th>Número</th>
                    <%
                        if (tipoUsuario.equals("empresa")){
                    %>
                        <th>Ações</th>
                    <%}%>
                </tr>
            </thead>
                <tbody>
                    <%for(Unidade unidade: unidades){%>
                <tr>
                    <td><%=unidade.getId()%></td>
                    <td><%=unidade.getNome()%></td>
                    <td><%=FormatoOutput.cnpj(unidade.getCnpj())%></td>
                    <td><%=unidade.getEmail()%></td>
                    <td><%=FormatoOutput.cep(unidade.getCep())%></td>
                    <td><%=unidade.getNumero()%></td>
                    <%
                        if (tipoUsuario.equals("empresa")){
                    %>
                        <td>
                            <div id="juntos">
                                <a href="${pageContext.request.contextPath}/AlterarUnidadeServlet?id=<%=unidade.getId()%>">
                                    <svg class= "alterar" viewBox="0 0 34 30" fill="none"
                                         xmlns="http://www.w3.org/2000/svg">
                                        <path
                                                d="M23.7646 4.87009L29.0889 10.1563C29.3132 10.379 29.3132 10.7423 29.0889 10.965L16.1972 23.7644L10.7194 24.368C9.9875 24.4501 9.36771 23.8347 9.45035 23.108L10.0583 17.6695L22.95 4.87009C23.1743 4.64739 23.5403 4.64739 23.7646 4.87009ZM33.3271 3.52803L30.4465 0.668099C29.5493 -0.2227 28.0913 -0.2227 27.1882 0.668099L25.0986 2.74272C24.8743 2.96542 24.8743 3.32878 25.0986 3.55148L30.4229 8.83766C30.6472 9.06036 31.0132 9.06036 31.2375 8.83766L33.3271 6.76304C34.2243 5.86638 34.2243 4.41883 33.3271 3.52803ZM22.6667 20.2833V26.2493H3.77778V7.4956H17.3424C17.5312 7.4956 17.7083 7.41942 17.8441 7.29049L20.2052 4.94628C20.6538 4.50088 20.3351 3.74487 19.7035 3.74487H2.83333C1.2691 3.74487 0 5.00488 0 6.55792V27.187C0 28.74 1.2691 30 2.83333 30H23.6111C25.1753 30 26.4444 28.74 26.4444 27.187V17.9391C26.4444 17.312 25.683 17.0014 25.2344 17.4409L22.8733 19.7851C22.7434 19.9199 22.6667 20.0957 22.6667 20.2833Z"
                                                fill="#1A8E00" />
                                    </svg>
                                </a>
                                <a href="${pageContext.request.contextPath}/RemoverUnidadeServlet?id=<%=unidade.getId()%>">
                                    <svg class="alterar" viewBox="0 0 26 30" fill="none" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M25.0714 1.87501H18.1071L17.5616 0.779307C17.446 0.545057 17.268 0.348012 17.0476 0.210337C16.8272 0.0726631 16.573 -0.00017742 16.3138 1.02731e-05H9.68036C9.42174 -0.000993469 9.16807 0.0715755 8.94841 0.209403C8.72876 0.34723 8.552 0.544741 8.43839 0.779307L7.89286 1.87501H0.928571C0.682299 1.87501 0.446113 1.97378 0.271972 2.1496C0.0978314 2.32541 0 2.56387 0 2.81251L0 4.68751C0 4.93615 0.0978314 5.17461 0.271972 5.35042C0.446113 5.52624 0.682299 5.62501 0.928571 5.62501H25.0714C25.3177 5.62501 25.5539 5.52624 25.728 5.35042C25.9022 5.17461 26 4.93615 26 4.68751V2.81251C26 2.56387 25.9022 2.32541 25.728 2.1496C25.5539 1.97378 25.3177 1.87501 25.0714 1.87501ZM3.0875 27.3633C3.13179 28.0773 3.44393 28.7475 3.96039 29.2373C4.47685 29.7272 5.15879 29.9999 5.86741 30H20.1326C20.8412 29.9999 21.5232 29.7272 22.0396 29.2373C22.5561 28.7475 22.8682 28.0773 22.9125 27.3633L24.1429 7.50001H1.85714L3.0875 27.3633Z" fill="#A90003"/>
                                    </svg>
                                </a>
                            </div>
                        </td>
                    <%}%>
                </tr>
                    <%}%>
            </tbody>
        </table>
        <%} else {%>
        <p style="color: white">Não há nenhuma unidade cadastrada</p>
        <%}%>

        <section id="footer">
            <%if (tipoUsuario.equals("empresa")) {%>
                <a class="botaoSecundario" href="${pageContext.request.contextPath}/AdicionarUnidadeServlet">Adicionar</a>
            <%}%>

            <div id="pages">
                <a href="${pageContext.request.contextPath}/ListarUnidadesServlet?pagina=<%=pagina - 1%>">
                    <svg width="10" height="17" viewBox="0 0 10 17" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path
                            d="M9.6176 0.374196C9.86245 0.613866 10 0.938885 10 1.27778C10 1.61667 9.86245 1.94169 9.6176 2.18136L3.15257 8.50772L9.6176 14.8341C9.85551 15.0751 9.98716 15.398 9.98418 15.7331C9.9812 16.0682 9.84385 16.3887 9.60169 16.6257C9.35954 16.8626 9.03196 16.997 8.68951 17C8.34706 17.0029 8.01715 16.874 7.77082 16.6412L0.382399 9.4113C0.137548 9.17163 0 8.84661 0 8.50772C0 8.16882 0.137548 7.8438 0.382399 7.60413L7.77082 0.374196C8.01575 0.134599 8.34789 0 8.69421 0C9.04053 0 9.37268 0.134599 9.6176 0.374196Z"
                            fill="#8200A8" />
                    </svg>
                </a>

                <div id="num">
                    <p id="text"><%=pagina%></p>
                </div>

                <a href="${pageContext.request.contextPath}/ListarUnidadesServlet?pagina=<%= pagina + 1%>">
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