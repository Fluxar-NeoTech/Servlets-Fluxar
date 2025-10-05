<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% int plano = (int) session.getAttribute("plano");
    String planoString = "";
    String valorString;
    double valor;
    String texto;

    if (plano % 2 == 0) {
        if (plano == 2) {
            planoString = "Essential";
            valorString = "R$6.899,99";
            valor = 6899.99;
        } else if (plano == 4) {
            planoString = "Profissional - R$10.299,99";
            valorString = "R$10299,99";
            valor = 899.99;
        } else {
            planoString = "Enterprise";
            valorString = "R$17.999,99";
            valor = 17999.99;
        }
        texto = "Tem certeza que pagará " + valorString + " no débito ou no crédito para usufruir do plano " + planoString + " do Fluxar no período de um ano?";
    } else {
        if (plano == 1) {
            planoString = "Essential";
            valorString = "R$599,99";
            valor = 599.99;
        } else if (plano == 3) {
            planoString = "Profissional - R$899,99";
            valorString = "R$899,99";
            valor = 899.99;
        } else {
            planoString = "Enterprise";
            valorString = "R$1.599,99";
            valor = 1599.99;
        }

        texto = "Tem certeza que pagará " + valorString + " no débito ou no crédito para usufruir do plano " + planoString + " do Fluxar no período de um mês?";
    }
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../../css/confirmarFormaPag.css">
    <title>Pagar com cartão</title>
</head>

<body>
<header>
    <a href="../formaPagamento/pagamento.html" class="icon-back">
    </a>
    <h1>Pagar por crédito/débito</h1>
</header>
<main>
    <article>
        <h2>Confirmação:</h2>
        <p><%=texto%>
        </p>
        <form action="../../../CadastroFormaPagamentoServlet" method="get">
            <input name="formaPagamento" type="text" value="credito/debito">
            <button>CONFIRMAR</button>
        </form>
    </article>
</main>
</body>

</html>