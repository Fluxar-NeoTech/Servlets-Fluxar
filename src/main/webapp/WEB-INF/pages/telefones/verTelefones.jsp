<%@ page import="java.util.List" %>
<%@ page import="com.example.servletfluxar.util.FormatoOutput" %>
<%@ page import="com.example.servletfluxar.model.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listar telefones</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/Assets/Icons/XFAVICOM%201.png">
</head>

<body>
<%
    Integer paginaObjeto = (Integer) request.getAttribute("pagina");
    int pagina = (paginaObjeto != null) ? paginaObjeto : 1;
    List<Telefone> telefones = (List<Telefone>) request.getAttribute("telefones");
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
        <%if (tipoUsuario.equals("administrador")){%>
        <a href="#" class="botaoPrimario">Área restrita</a>
        <%}%>
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
                    <div class="text" id="atual">
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
    <p id="title">Telefones de <%=((Empresa) request.getAttribute("empresa")).getNome()%>
    </p>

    <%if (!telefones.isEmpty()) {%>
    <table style=<%=tipoUsuario.equals("administrador") ? "--cols:2;" : "--cols:3;"%>>
        <thead>
        <tr>
            <th>Id</th>
            <th>Número</th>
            <%
                if (tipoUsuario.equals("empresa")) {
            %>
            <th>Ações</th>
            <%}%>
        </tr>
        </thead>
        <tbody>
        <%for (Telefone telefone : telefones) {%>
        <tr>
            <td><%=telefone.getId()%>
            </td>
            <td><%=FormatoOutput.telefone(telefone.getNumero())%>
            </td>
            <%
                if (tipoUsuario.equals("empresa")) {
            %>
            <td>
                <div id="juntos">
                    <a href="${pageContext.request.contextPath}/RemoverTelefoneServlet?id=<%=telefone.getId()%>">
                        <svg class="alterar" viewBox="0 0 26 30" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path d="M25.0714 1.87501H18.1071L17.5616 0.779307C17.446 0.545057 17.268 0.348012 17.0476 0.210337C16.8272 0.0726631 16.573 -0.00017742 16.3138 1.02731e-05H9.68036C9.42174 -0.000993469 9.16807 0.0715755 8.94841 0.209403C8.72876 0.34723 8.552 0.544741 8.43839 0.779307L7.89286 1.87501H0.928571C0.682299 1.87501 0.446113 1.97378 0.271972 2.1496C0.0978314 2.32541 0 2.56387 0 2.81251L0 4.68751C0 4.93615 0.0978314 5.17461 0.271972 5.35042C0.446113 5.52624 0.682299 5.62501 0.928571 5.62501H25.0714C25.3177 5.62501 25.5539 5.52624 25.728 5.35042C25.9022 5.17461 26 4.93615 26 4.68751V2.81251C26 2.56387 25.9022 2.32541 25.728 2.1496C25.5539 1.97378 25.3177 1.87501 25.0714 1.87501ZM3.0875 27.3633C3.13179 28.0773 3.44393 28.7475 3.96039 29.2373C4.47685 29.7272 5.15879 29.9999 5.86741 30H20.1326C20.8412 29.9999 21.5232 29.7272 22.0396 29.2373C22.5561 28.7475 22.8682 28.0773 22.9125 27.3633L24.1429 7.50001H1.85714L3.0875 27.3633Z"
                                  fill="#A90003"/>
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
    <%if (request.getAttribute("erro") != null) {%>
    <p class="erro-request" style="color: #ff8181"><%=request.getAttribute("erro")%>
    </p>
    <%} else {%>
    <p style="color: white">Não há nenhum telefone cadastrado</p>
    <%}%>
    <%}%>

    <section id="footer">
        <%if (tipoUsuario == "empresa") {%>
        <a class="botaoSecundario" href="${pageContext.request.contextPath}/AdicionarTelefoneServlet">Adicionar</a>
        <%}%>

        <div id="pages">
            <a href="${pageContext.request.contextPath}/ListarTelefonesServlet?pagina=<%=pagina - 1%>">
                <svg width="10" height="17" viewBox="0 0 10 17" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path
                            d="M9.6176 0.374196C9.86245 0.613866 10 0.938885 10 1.27778C10 1.61667 9.86245 1.94169 9.6176 2.18136L3.15257 8.50772L9.6176 14.8341C9.85551 15.0751 9.98716 15.398 9.98418 15.7331C9.9812 16.0682 9.84385 16.3887 9.60169 16.6257C9.35954 16.8626 9.03196 16.997 8.68951 17C8.34706 17.0029 8.01715 16.874 7.77082 16.6412L0.382399 9.4113C0.137548 9.17163 0 8.84661 0 8.50772C0 8.16882 0.137548 7.8438 0.382399 7.60413L7.77082 0.374196C8.01575 0.134599 8.34789 0 8.69421 0C9.04053 0 9.37268 0.134599 9.6176 0.374196Z"
                            fill="#8200A8"/>
                </svg>
            </a>

            <div id="num">
                <p id="text"><%=pagina%>
                </p>
            </div>

            <a href="${pageContext.request.contextPath}/ListarTelefonesServlet?pagina=<%= pagina + 1%>">
                <svg width="10" height="17" viewBox="0 0 10 17" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path
                            d="M0.382399 0.374196C0.137549 0.613866 0 0.938885 0 1.27778C0 1.61667 0.137549 1.94169 0.382399 2.18136L6.84743 8.50772L0.382399 14.8341C0.144488 15.0751 0.0128435 15.398 0.0158193 15.7331C0.018795 16.0682 0.156153 16.3887 0.398309 16.6257C0.640464 16.8626 0.968042 16.997 1.31049 17C1.65294 17.0029 1.98285 16.874 2.22918 16.6412L9.6176 9.4113C9.86245 9.17163 10 8.84661 10 8.50772C10 8.16882 9.86245 7.8438 9.6176 7.60413L2.22918 0.374196C1.98425 0.134599 1.65211 0 1.30579 0C0.959466 0 0.627323 0.134599 0.382399 0.374196Z"
                            fill="#8200A8"/>
                </svg>
            </a>
        </div>
    </section>
</main>
<script>
    function initDropdown(selectElement) {
        const input = selectElement.querySelector('input[type="text"]');
        const hiddenInput = selectElement.querySelector('input[type="hidden"]');
        const options = selectElement.querySelector('.options');
        const optionItems = selectElement.querySelectorAll('.options span');

        // Toggle dropdown
        input.addEventListener('click', (e) => {
            e.stopPropagation();
            selectElement.classList.toggle('active');
            options.classList.toggle('show');
        });

        // Selecionar opção
        optionItems.forEach(option => {
            option.addEventListener('click', (e) => {
                e.stopPropagation();
                const value = option.getAttribute('data-value');
                const text = option.textContent;

                // Atualiza input visível
                input.value = text;

                // Atualiza input hidden (valor enviado ao servlet)
                if (hiddenInput) {
                    hiddenInput.value = value;
                }

                // Fechar dropdown
                selectElement.classList.remove('active');
                options.classList.remove('show');
            });
        });

        // Fechar ao clicar fora
        document.addEventListener('click', () => {
            selectElement.classList.remove('active');
            options.classList.remove('show');
        });

        options.addEventListener('click', (e) => {
            e.stopPropagation();
        });
    }

    // Inicializar todos os dropdowns na página
    document.querySelectorAll('.input-select').forEach(select => {
        initDropdown(select);
    });
</script>
</body>

</html>
