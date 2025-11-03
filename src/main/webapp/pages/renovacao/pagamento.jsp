<%@ page import="com.example.servletfluxar.util.FormatoOutput" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html lang="pt-BR">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Formas de Pagamento</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/login.css">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/Assets/Icons/XFAVICOM%201.png">
    </head>

    <body>
        <%
            double preco = (Double) request.getAttribute("preco");
        %>
        <header>
            <img src="${pageContext.request.contextPath}/Assets/Icons/FluxarLogoBRANCA.png" alt="Logo do aplicativo fluxar branco">
        </header>

        <main>
            <article style="padding: 16px">
                <div>
                    <a href="${pageContext.request.contextPath}/index.jsp" class="btn-voltar">← Cancelar</a>
                    <h1 id="loginTitle" class="caixa__title">Pagamento</h1>
                    <div class="caixa__sub">Escolha a forma de pagamento</div>
                </div>

                <form method="get" action="${pageContext.request.contextPath}/ConfirmarRenovacaoPagamentoServlet">
                    <div class="payment-section">
                        <div class="payment-options">
                            <label class="payment-option">
                                <input type="radio" name="formaPagamento" value="Credito" required>
                                <div class="payment-card">
                                    <div class="payment-icon">💳</div>
                                    <div class="payment-info">
                                        <div class="payment-name">Cartão de Crédito</div>
                                    </div>
                                    <div class="radio-indicator"></div>
                                </div>
                            </label>

                            <label class="payment-option">
                                <input type="radio" name="formaPagamento" value="Debito" required>
                                <div class="payment-card">
                                    <div class="payment-icon">💰</div>
                                    <div class="payment-info">
                                        <div class="payment-name">Cartão de Débito</div>
                                    </div>
                                    <div class="radio-indicator"></div>
                                </div>
                            </label>

                            <label class="payment-option">
                                <input type="radio" name="formaPagamento" value="Pix" required>
                                <div class="payment-card">
                                    <div class="payment-icon">📱</div>
                                    <div class="payment-info">
                                        <div class="payment-name">PIX</div>
                                    </div>
                                    <div class="radio-indicator"></div>
                                </div>
                            </label>

                            <label class="payment-option">
                                <input type="radio" name="formaPagamento" value="Boleto" required>
                                <div class="payment-card">
                                    <div class="payment-icon">📄</div>
                                    <div class="payment-info">
                                        <div class="payment-name">Boleto Bancário</div>
                                    </div>
                                    <div class="radio-indicator"></div>
                                </div>
                            </label>

                            <label class="payment-option">
                                <input type="radio" name="formaPagamento" value="Transferencia" required>
                                <div class="payment-card">
                                    <div class="payment-icon">🏦</div>
                                    <div class="payment-info">
                                        <div class="payment-name">Transferência Bancária</div>
                                    </div>
                                    <div class="radio-indicator"></div>
                                </div>
                            </label>
                        </div>


                        <div class="total-amount">
                            <div class="total-label">Valor Total</div>
                            <div class="total-value" id="totalAmount"><%=FormatoOutput.preco(preco)%></div>
                        </div>

                        <input type="submit" class="botaoPrimario" value="Confirmar Pagamento">
                    </div>
                </form>
            </article>
        </main>
    </body>

    </html>