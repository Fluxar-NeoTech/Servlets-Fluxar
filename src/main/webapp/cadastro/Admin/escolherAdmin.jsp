<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <% String nomeEmpresa=(String) session.getAttribute("nomeEmpresa");%>
        <!DOCTYPE html>
        <html lang="pt-br">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link rel="stylesheet" href="<%= request.getContextPath() %>/css/login.css">
            <title>Cadastro Administrador</title>
        </head>

        <body>
            <header>
                <h1>Cadastrando administrador</h1>
            </header>
            <main>
                <article>
                    <h2>Cadastre o email do Administrador:</h2>
                    <p>É necessário ter uma conta de administrador para gerir as informações da empresa, digite o email
                        do administrador da empresa <%= nomeEmpresa %>
                    </p>
                    <form action="<%= request.getContextPath() %>/CriarAdminServlet" method="post">
                        <% if (request.getAttribute("erroEmail") !=null) { %>
                            <div class="floating-label-erro">
                                <input type="email" class="inputs-erro adminEmail" name="emailAdmin" id="adminEmail"
                                    placeholder=" " required>
                                <label id="label1" for="adminEmail">Digite o email do admin</label>
                                <p class="erro">
                                    <%= request.getAttribute("erroEmail") %>
                                </p>
                            </div>
                            <% } else { %>
                                <div class="floating-label">
                                    <input type="email" class="inputs adminEmail" name="emailAdmin" id="adminEmai"
                                        placeholder=" " required>
                                    <label id="label2" for="adminEmai">Digite o email de admin</label>
                                </div>
                                <% } %>
                                    <% if (request.getAttribute("erroSenha") !=null) { %>
                                        <div class="floating-label-erro">
                                            <input type="password" class="inputs-erro adminSenha"
                                                name="senhaAdmin" id="senhaAdmin" placeholder=" " required>
                                            <label id="label3" for="senhaAdmin">Digite uma senha temporária</label>
                                            <p class="erro">
                                                <%= request.getAttribute("erroSenha") %>
                                            </p>
                                        </div>
                                        <% } else { %>
                                            <div class="floating-label">
                                                <input type="password" class="inputs adminSenha" 
                                                    name="senhaAdmin" id="senhaAdmi"placeholder=" " required>
                                                <label id="label4" for="senhaAdmi">Digite uma senha temporária</label>
                                            </div>
                                            <% } %>
                                    <% if (request.getAttribute("erroConfSenha") !=null) { %>
                                        <div class="floating-label-erro">
                                            <input type="password" class="inputs-erro adminSenha"
                                                name="confirmarSenha" id="confirmarSenha"placeholder=" " required>
                                            <label id="label5" for="confirmarSenha">Confirme a senha aqui</label>
                                            <p class="erro">
                                                <%= request.getAttribute("erroConfSenha") %>
                                            </p>
                                        </div>
                                        <% } else { %>
                                            <div class="floating-label">
                                                <input type="password" class="inputs userSenha" 
                                                    name="confirmarSenha" id="confirmarSenh" placeholder=" " required>
                                                <label id="label6" for="confirmarSenh">Confirme a senha aqui</label>
                                            </div>
                                            <% } %>
                                                <button type="submit" class="botaoPrimario">CADASTRAR</button>
                    </form>
                </article>
            </main>
        </body>

        </html>