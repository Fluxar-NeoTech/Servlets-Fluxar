<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="pt-br">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/escolhaTabela.css">
        <title>Seleção da tabela</title>
    </head>

    <body>
        <header>
            <a href="${pageContext.request.contextPath}/paginasIniciais/PIAdmin/PIadmin.jsp" class="icon-back">
            </a>
            <h1>Seleção da tabela</h1>
        </header>
        <main>
            <article>
                <h2>Selecione a tabela:</h2>
                <form action="${pageContext.request.contextPath}/TabelaAdminServlet" method="get">
                    <button type="submit" name="tabela" value="empresa">EMPRESA</button>
                    <button type="submit" name="tabela" value="unidade">UNIDADES</button>
                    <button type="submit" name="tabela" value="setor">SETORES</button>
                    <button type="submit" name="tabela" value="insumos">INSUMOS</button>
                    <button type="submit" name="tabela" value="usuario">USUÁRIOS</button>
                    <button type="submit" name="tabela" value="administradores">ADMINISTRADORES</button>
                </form>
            </article>
        </main>
    </body>

    </html>