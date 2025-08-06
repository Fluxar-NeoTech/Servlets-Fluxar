<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <% Integer plano=(Integer) session.getAttribute("plano"); %>
    <!DOCTYPE html>
    <html lang="pt-br">

    <head>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" href="../../css/duracao.css">
      <title>Escolher duração</title>
    </head>

    <body>
      <header>
        <% if (plano==1) {%>
          <a href="../plano/confirmar/confirmarEssential.html" class="icon-back">
          </a>
          <%}else if(plano==2){%>
            <a href="../plano/confirmar/confirmarProfissional.html" class="icon-back">
            </a>
            <%}else{%>
              <a href="../plano/confirmar/confirmarEnterprise.html" class="icon-back">
              </a>
              <%};%>
                <h1>Selecionando duração do plano</h1>
      </header>
      <main>
        <article>
          <h2>Escolha a duração do plano:</h2>
          <form action="../../DuracaoPlanoServlet" method="get">
            <div class="inputs">
              <% if (plano==1) { %>
                <div>
                  <input type="radio" id="mensal" name="duracao" value="mensal" required>
                  <label for="mensal">Mensal - R$599,99</label><br>
                </div>

                <div>
                  <input type="radio" id="anual" name="duracao" value="anual">
                  <label for="anual">Anual - R$6.899,99</label><br>
                </div>

                <% } else if (plano==2) { %>
                  <div>
                    <input type="radio" id="mensal1" name="duracao" value="mensal" required>
                    <label for="mensal">Mensal - R$899,99</label><br>
                  </div>
                  <div>
                    <input type="radio" id="anual1" name="duracao" value="anual">
                    <label for="anual">Anual - R$10.299,99</label><br>
                  </div>
                  <% } else { %>
                    <div>
                      <input type="radio" id="mensal2" name="duracao" value="mensal" required>
                      <label for="mensal">Mensal - R$1.599,99</label><br>
                    </div>
                    <div>
                      <input type="radio" id="anual2" name="duracao" value="anual">
                      <label for="anual">Anual - R$17.999,99</label><br>
                    </div>
                    <% } %>
            </div>
            <button type="submit" class="botaoPrimario">Confirmar Duração</button>
          </form>
        </article>
      </main>
    </body>

    </html>