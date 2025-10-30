<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="pt-br">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/login.css">
        <title>Entrar</title>
    </head>

    <body>
        <header>

        </header>
        <main>
            <article>
                <h2>Fazer login:</h2>
                <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
                    <% if (request.getAttribute("erroEmail") !=null) { %>
                        <div class="floating-label-erro">
                            <input type="text" class="inputs-erro userEmail" id="userEmail" name="emailUsuario"
                                placeholder=" " required>
                            <label id="label1" for="userEmail">Digite seu email aqui</label>
                            <p class="erro">
                                <%= request.getAttribute("erroEmail") %>
                            </p>
                        </div>
                        <% } else { %>
                            <div class="floating-label">
                                <input type="text" class="inputs userEmail" id="emailUsuario" name="emailUsuario"
                                    placeholder=" " required>
                                <label id="label2" for="emailUsuario">Digite seu email aqui</label>
                            </div>
                            <% } %>

                                <% if (request.getAttribute("erroSenha") !=null) { %>
                                    <div class="floating-label-erro">
                                        <input type="password" class="inputs-erro userPassword" name="senhaUsuario"
                                            id="userSenha" placeholder=" " required>
                                        <label id="label3" for="userSenha">Digite sua senha aqui</label>
                                        <p class="erro">
                                            <%= request.getAttribute("erroSenha") %>
                                        </p>
                                    </div>
                                    <% }else{%>
                                        <div class="floating-label">
                                            <input type="password" class="inputs userPassword" name="senhaUsuario"
                                                id="userPassword" placeholder=" " required>
                                            <label id="label4" for="userPassword">Digite sua senha aqui</label>
                                        </div>
                                        <%}%>

                                            <button type="submit" class="botaoPrimario">Entrar</button>
                </form>
            </article>
            <nav class="nav-login">
                <ul>
                    <li class="botaoSecundariob"><a class="botaoSecundariob" href="${pageContext.request.contextPath}/pages/esqueciSenha/inputEmail/recuperarSenha.jsp">ESQUECI MINHA SENHA</a></li>
                </ul>
            </nav>
        </main>
    </body>

    </html>