<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="pt-br">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cadastro.css">
        <title>Cadastro</title>
    </head>

    <body>
        <header>
            <a href="../../fazerLogin/paginaLogin/login.jsp" class="icon-back">
            </a>
            <h1>Cadastro</h1>
        </header>
        <main>
            <article>
                <h2>Cadastrar empresa:</h2>
                <form action="${pageContext.request.contextPath}/TriagemCadastroServlet" method="get">
                    <% if (request.getAttribute("erroNome") !=null) { %>
                        <div class="floating-label-erro">
                            <input type="text" class="inputs-erro nome" id="nome" name="nome" placeholder=" " required>
                            <label id="label1" for="nome">Digite o nome da sua empresa aqui</label>
                            <p class="erro">
                                <%= request.getAttribute("erroNome") %>
                            </p>
                        </div>
                        <% } else { %>
                            <div class="floating-label">
                                <input type="text" class="inputs nome" id="nome2" name="nome" placeholder=" " required>
                                <label id="label2" for="nome">Digite o nome da sua empresa aqui</label>
                            </div>
                            <% } %>

                                <% if (request.getAttribute("erroCNPJ") !=null) { %>
                                    <div class="floating-label-erro">
                                        <input type="text" class="inputs-erro CNPJ" name="CNPJ" id="userCNPJ"
                                            placeholder=" " required>
                                        <label id="label3" for="CNPJ">Digite o CNPJ aqui</label>
                                        <p class="erro">
                                            <%= request.getAttribute("erroCNPJ") %>
                                        </p>
                                    </div>
                                    <% } else { %>
                                        <div class="floating-label">
                                            <input type="text" class="inputs CNPJ" id="CNPJ" name="CNPJ" placeholder=" "
                                                required>
                                            <label id="label4" for="CNPJ">Digite o CNPJ aqui</label>
                                        </div>
                                        <%}%>
                    <button type="submit" class="botaoPrimario">CONTINUAR</button>
                </form>
            </article>
        </main>
    </body>

    </html>