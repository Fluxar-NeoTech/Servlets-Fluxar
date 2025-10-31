<%@ page import="com.example.servletfluxar.model.Plano" %>
<%@ page import="com.example.servletfluxar.model.Empresa" %>
<%@ page import="com.example.servletfluxar.model.Administrador" %>
<%@ page import="com.example.servletfluxar.util.FormatoOutput" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Deletar plano</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
</head>

<body>
<%
  Plano plano = (Plano) request.getAttribute("plano");
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
          <div class="text" id="atual">
            Planos
          </div>
        </a>
      </li>

      <li>
        <a href="${pageContext.request.contextPath}/ListarAssinaturasServlet">
          <div class="text">
            Assinaturas
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
  <p id="title">Deletar plano</p>

  <form id="form" action="${pageContext.request.contextPath}/RemoverPlanoServlet" method="post">
    <table class="confirmarDelecao" style="border-radius: 20px">
      <thead>
        <tr>
          <th colspan="2">Plano</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>Id</td>
          <td style="border-left: solid 1px"><%=plano.getId()%></td>
        </tr>
        <tr>
          <td>Nome</td>
          <td style="border-left: solid 1px"><%=plano.getNome()%></td>
        </tr>
        <tr>
          <td>Duracao</td>
          <td style="border-left: solid 1px"><%=FormatoOutput.duracao(plano.getTempo())%></td>
        </tr>
        <tr>
          <td>Id</td>
          <td style="border-left: solid 1px"><%=FormatoOutput.preco(plano.getPreco())%></td>
        </tr>
      </tbody>
    </table>
    <input type="hidden" name="id" value="<%= plano.getId() %>">
    <div>
      <button type="submit" class="botaoPrimario">Confirmar</button>

      <a class="botaoSecundario" href="${pageContext.request.contextPath}/ListarPlanosServlet">Cancelar</a>
    </div>
  </form>
</main>
</body>

</html>