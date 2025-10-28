<%@ page import="com.example.servletfluxar.model.Administrador" %>
<%@ page import="com.example.servletfluxar.util.FormatoOutput" %>
<%@ page import="com.example.servletfluxar.model.Unidade" %>
<%@ page import="com.example.servletfluxar.model.Empresa" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Deletar empresa</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Assets/CSS/style.css">
</head>

<body>
<%
  Empresa empresa = (Empresa) request.getAttribute("empresa");
  Unidade unidade = (Unidade) request.getAttribute("unidade");
  request.setAttribute("ativo", true);
  String tipoUsuario = (String) request.getAttribute("tipoUsuario");
%>
<header>
  <div id="nome">
    <a href="${pageContext.request.contextPath}/MeuPerfilServlet?idUsuario=<%=
    ((Empresa) session.getAttribute("empresa")).getId()%>"></a>
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
            Assinaturas
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

  <div class="maior" id="sair">
    <a id="sairB" href="${pageContext.request.contextPath}/SairServlet">Sair</a>
  </div>
</aside>
<main>
  <p id="title">Deletar empresa</p>

  <form action="${pageContext.request.contextPath}/RemoverUnidadeServlet" method="post">
    <table style="--cols: 2;">
      <thead>
        <tr>
          <th colspan="2">Unidade</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>Id</td>
          <td style="border-left: solid 1px"><%=unidade.getId()%></td>
        </tr>
        <tr>
          <td>Nome</td>
          <td style="border-left: solid 1px"><%=unidade.getNome()%></td>
        </tr>
        <tr>
          <td>Cnpj</td>
          <td style="border-left: solid 1px"><%=FormatoOutput.cnpj(unidade.getCnpj())%></td>
        </tr>
        <tr>
          <td>Email</td>
          <td style="border-left: solid 1px"><%=unidade.getEmail()%></td>
        </tr>
        <tr>
          <td>CEP</td>
          <td style="border-left: solid 1px"><%=FormatoOutput.cep(unidade.getCep())%></td>
        </tr>
        <tr>
          <td>Número</td>
          <td style="border-left: solid 1px"><%=unidade.getNumero()%></td>
        </tr>
        <tr>
          <td>Nome empresa</td>
          <td style="border-left: solid 1px"><%=empresa.getNome()%></td>
        </tr>
      </tbody>
    </table>
    <input type="hidden" name="id" value="<%=unidade.getId()%>">
    <div>
      <button type="submit">Confirmar</button>

      <a id="add" href="${pageContext.request.contextPath}/ListarUnidadesServlet">Cancelar</a>
    </div>
  </form>
</main>
</body>

</html>