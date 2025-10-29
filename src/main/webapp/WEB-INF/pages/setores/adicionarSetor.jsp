<%@ page import="com.example.servletfluxar.model.Empresa" %>
<%@ page import="com.example.servletfluxar.model.Unidade" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Adicionar setor</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Assets/CSS/style.css">
</head>

<body>
<%
  String tipoUsuario = (String) request.getAttribute("tipoUsuario");
  List<Unidade> unidades = (List<Unidade>) request.getAttribute("unidades");
%>
<header>
  <div id="nome">
    <a href="${pageContext.request.contextPath}/MeuPerfilServlet?idUsuario=<%=
                    ((Empresa) session.getAttribute("empresa")).getId()%>">
      <%=((Empresa) session.getAttribute("empresa")).getNome()%></a>
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
          <div class="text">
              Empresas
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
  <p id="title">Adicionar setor</p>

  <form action="${pageContext.request.contextPath}/AdicionarSetorServlet" method="post">
    <label for="name">Nome:</label>
    <input type="text" name="nome" id="name">
    <p><%=request.getAttribute("erroNome")%></p>

    <select name = "idUnidade">
        <option selected hidden>Unidade</option>
        <%for (Unidade unidade: unidades) {%>
          <option value="<%=unidade.getId()%>"><%=unidade.getNome()%></option>
        <%}%>
    </select>

    <label for="descricao">Descrição:</label>
    <textarea id="descricao" name="descricao" rows="4" cols="50" placeholder="Digite a descricao"></textarea>
    <p><%=request.getAttribute("erroDescricao")%></p>

    <div>
      <button type="submit">Confirmar</button>
      <a id="add" href="${pageContext.request.contextPath}/ListarUnidadesServlet">Voltar</a>
    </div>
  </form>
</main>
</body>

</html>