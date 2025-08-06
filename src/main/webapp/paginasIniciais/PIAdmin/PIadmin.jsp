<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="pt-br">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/PIadmin.css">
        <title>Página inicial Administrador</title>
    </head>

    <body>
        <header>
            <a href="${pageContext.request.contextPath}/fazerLogin/paginaLogin/login.jsp">SAIR</a>
            <h1>Página inicial administrador</h1>
        </header>
        <main>
            <article>
                <h2>O que deseja fazer?</h2>
                <form action="${pageContext.request.contextPath}/AcaoAdminServlet" method="get">
                    <button type="submit" name="acao" value="ver">VER INFORMAÇÕES</button>
                    <button type="submit" name="acao" value="adicionar">ADICIONAR DADO</button>
                    <button type="submit" name="acao" value="remover">REMOVER DADO</button>
                    <button type="submit" name="acao" value="atualizar">ATUALIZAR DADO</button>
                </form>
            </article>
        </main>
    </body>

    </html>