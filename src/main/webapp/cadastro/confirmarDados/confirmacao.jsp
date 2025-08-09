<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <% String nome=(String) session.getAttribute("nomeEmpresa"); 
   String cnpjBruto = (String) session.getAttribute("cnpjEmpresa");

    // Verifica se tem 14 dígitos e aplica a máscara
    String cnpjFormatado = "";
    if (cnpjBruto != null && cnpjBruto.matches("\\d{14}")) {
        cnpjFormatado = cnpjBruto.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
    } else {
        cnpjFormatado = cnpjBruto; // mostra como está, se não for válido
    }
    
    Boolean statusObj=(Boolean) session.getAttribute("status"); 
    boolean status=(statusObj !=null) ? statusObj : false; 
    int plano= (int) session.getAttribute("plano");
    String planoString="" ;
    String duracaoString="";

    if(plano % 2 == 0){
        duracaoString = "Anual";
    }else{
        duracaoString = "Mensal";
    }

    if(plano == 1){
        planoString = "Essential - R$599,99";
    } else if (plano == 2) {
        planoString = "Essential - R$6.899,99";
    } else if (plano == 3) {
        planoString = "Profissional - R$899,99";
    } else if (plano == 4) {
        planoString = "Profissional - R$10.299,99";
    } else if (plano == 5) {
        planoString = "Enterprise - R$1.599,99";
    } else if (plano == 6) {
        planoString = "Enterprise - R$17.999,99";
    }
    %>
        <!DOCTYPE html>
        <html lang="pt-br">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link rel="stylesheet" href="../../css/confirmacao.css?v=<%= System.currentTimeMillis() %>">
            <title>Confirmar informações</title>
        </head>

        <body>
            <header>
                <%if(plano==1 || plano==2){%>
                    <a href="../plano/confirmar/confirmarEssential.html" class="icon-back"></a>
                <%} else if (plano==3 || plano==4) {%>
                    <a href="../plano/confirmar/confirmarProfissional.html" class="icon-back"></a>
                <%} else if (plano==5 || plano==6){%>
                    <a href="../plano/confirmar/confirmarEnterprise.html" class="icon-back"></a>
                <%} else {%>
                    <a href="../plano/contato/escolherPlano.html" class="icon-back"></a>
                <%}%>
                <h1>Confirmar informações</h1>
            </header>
            <main>
                <article>
                    <section>
                        <h2>Confirme as informações dadas no cadastro:</h2>
                    </section>
                    <section>
                        <div>
                            <p class="titulo">Nome da empresa:</p>
                            <p><%= nome%></p>
                        </div>
                        <div>
                            <p class="titulo">CNPJ da empresa:</p>
                            <p><%= cnpjFormatado%></p>
                        </div>
                        <div>
                            <p class="titulo">Plano de serviço:</p>
                            <p><%= planoString%></p>
                        </div>
                        <div>
                            <p class="titulo">Duração:</p>
                            <p><%= duracaoString%></p>
                        </div>
                    </section>
                </article>
                <aside>
                    <ul>
                        <li class="botaoSecundariob"><a class="botaoSecundariob" href="../cnpjNomeEmpresa/cadastro.jsp">ALTERAR DADOS</a>
                        </li>
                        <li class="botaoPrimariob"><a class="botaoPrimariob" href="../Admin/escolherAdmin.jsp">CONFIRMAR</a></li>
                    </ul>
                </aside>
            </main>
        </body>

        </html>