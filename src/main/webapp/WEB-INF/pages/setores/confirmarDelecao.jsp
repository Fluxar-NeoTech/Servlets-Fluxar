<%@ page import="com.example.servletfluxar.util.FormatoOutput" %>
<%@ page import="com.example.servletfluxar.model.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Deletar setor</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Assets/CSS/style.css">
</head>

<body>
<%
  Setor setor = (Setor) request.getAttribute("setor");
  Unidade unidade = (Unidade) request.getAttribute("unidade");
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
          <div class="text">
            Unidades
          </div>
        </a>
      </li>

      <li>
        <a href="${pageContext.request.contextPath}/ListarSetoresServlet">
          <div class="text" id="atual">
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
  <p id="title">Deletar setor</p>

  <form id="form" action="${pageContext.request.contextPath}/RemoverSetorServlet" method="post">
    <table class="confirmarDelecao" style="border-radius: 20px">
      <thead>
        <tr>
          <th colspan="2">Setor</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>Id</td>
          <td style="border-left: solid 1px"><%=setor.getId()%></td>
        </tr>
        <tr>
          <td>Nome</td>
          <td style="border-left: solid 1px"><%=setor.getNome()%></td>
        </tr>
        <tr>
          <td>Descrição</td>
          <td style="border-left: solid 1px"><%=setor.getDescricao()%></td>
        </tr>
        <tr>
          <td>Nome unidade</td>
          <td style="border-left: solid 1px"><%=unidade.getNome()%></td>
        </tr>
        <tr>
          <td>Email unidade</td>
          <td style="border-left: solid 1px"><%=unidade.getEmail()%></td>
        </tr>
      </tbody>
    </table>
    <input type="hidden" name="id" value="<%=setor.getId()%>">
    <div>
      <button type="submit">Confirmar</button>

      <a id="add" href="${pageContext.request.contextPath}/ListarSetoresServlet">Cancelar</a>
    </div>
  </form>
</main>
</body>
</html>