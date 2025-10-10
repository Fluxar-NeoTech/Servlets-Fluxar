<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="java.util.*" %>
        <%@ page import="model.Empresa" %>
            <%@ page import="model.Administrador" %>
                <%@ page import="model.Unidade" %>
                    <%@ page import="model.Usuario" %>
                        <%@ page import="model.Setor" %>
                            <%@ page import="model.Insumo" %>
                                <%@ page import="model.SetorInsumo" %>
                                    <% String tabela=(String) session.getAttribute("tabelaAdmin"); List<?> dados = (List
                                        <?>) session.getAttribute("dados");
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Visualização de dados</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mostrarDados.css">
</head>
<body>
    <header>
        <a href="${pageContext.request.contextPath}/WEB-INF/telasAdmin/escolhaTabela/escolhaTabela.jsp" class="icon-back"></a>
        <h1>Dados - <%= tabela %></h1>
    </header>

    <main>
        <article>
            <% if ("empresa".equals(tabela)) { %>
    <table>
        <tr>
            <th>Código</th>
            <th>Nome</th>
            <th>CNPJ</th>
            <th>Telefone</th>
            <th>Site</th>
            <th>Email</th>
            <th>Status</th>
            <th>ID Plano</th>
            <th>Início</th>
            <th>Duração</th>
            <th>Forma Pagamento</th>
        </tr>
        <% for (Empresa e : (List<Empresa>) dados) { %>
            <tr>
                <td><%= e.getCodigo() %></td>
                <td><%= e.getNome() %></td>
                <td><%= e.getCnpj() %></td>
                <td><%= e.getTelefone() %></td>
                <td><%= e.getSite() %></td>
                <td><%= e.getEmail() %></td>
                <td><%= e.getStatus() %></td>
                <td><%= e.getIdPlano() %></td>
                <td><%= e.getDtInicio() %></td>
                <td><%= e.getDuracao() %></td>
                <td><%= e.getFormaPag() %></td>
            </tr>
        <% } %>
    </table>
<% } %>

        </article>
    </main>
</body>
</html>