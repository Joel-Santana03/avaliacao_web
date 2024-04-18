package com.web.av1unidade.Controllers;

import com.web.av1unidade.DAO.ProdutoDAO;
import com.web.av1unidade.Models.Produto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Controller
public class VerCarrinhoController {

    private final HttpServletRequest httpServletRequest;

    public VerCarrinhoController(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @GetMapping("/verCarrinho")
    public void verCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int total = 0;
        boolean d = true;

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        HttpSession session = request.getSession();
        Integer clienteId = (Integer) session.getAttribute("clienteId");

        CarrinhoController carrinhoController = new CarrinhoController();

        Map<Integer, Integer> carrinho = carrinhoController.getCarrinhoFromCookies(clienteId, request);
        System.out.println("Carrinho do cliente: " + carrinho);

        writer.println("<html>" +
                "<head>"+
                "<title>Carrinho</title>"+
                "<style>"+
                "body {display: flex; flex-direction: column; align-items: center; justify-content: center; background-color: transparent; height: 100vh; margin: 0;}"+
                "h1 {text-align: center;}"+
                "table {border: 1px solid black; background: white;}"+
                "</style>"+
                "</head>"+
                "<body>"+
                "<h1>Carrinho de Compras</h1>"+
                "<table>"+
                "<tr>"+
                "<th>Nome</th>"+
                "<th>Preço</th>"+
                "<th>Quantidade</th>"+
                "<th>Remover</th>"+
                "</tr>"
        );
        if (carrinho != null && !carrinho.isEmpty()) {

            for (Map.Entry<Integer, Integer> entry : carrinho.entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();

                Produto produto = ProdutoDAO.getProdutoById(productId);

                if(!ProdutoDAO.estoqueMaiorOuIgualQueQuantidade(quantity, productId)){
                    d = false;
                }

                writer.println("<tr>");
                writer.println("<td>" + produto.getNome() + "</td>");
                writer.println("<td>" + produto.getPreco() + "</td>");
                writer.println("<td>" + quantity + "</td>");
                writer.println("<td>" + "<a href='/addCarrinho?produtoId=" + produto.getId() + "&comando=remove'>Remover</a>");
                writer.println("</tr>");
                total = total + (quantity * produto.getPreco());
            }
            System.out.println("total: " + total);
        }else{
            response.sendRedirect("/listaProdutosCliente");
        }
        if(d){
            writer.println("</table>" + "<br>" + "<table border='1' style='background-color: white; margin-right: 160px; margin-top: -20px'>" + "<tr>"
                    + "<th style='width: 85px'>Total</th>" + "<td>" + total + "</td>" + "</tr>" + "</table>" + "<button style='margin-top: -27; margin-left: 140px; height: 28px; width: 150px' >" +
                    "<a href='/finalizarCompra' style='text-decoration: none; color: black'>Finalizar compra</a>" + "</button>" + "</br>"
            );
        }else{
            writer.println("</table>" + "<br>" + "<table border='1' style='background-color: white; margin-right: 160px; margin-top: -20px'>" + "<tr>"
                    + "<th style='width: 85px'>Total</th>" + "<td>" + total + "</td>" + "</tr>" + "</table>" +
                    "<button disabled style='margin-top: -27; margin-left: 140px; height: 28px; width: 150px'>Finalizar compra</button>" + "</br>"
            );
        }
        writer.println("<button>" +
                "<a href='/listaProdutosCliente' style='text-decoration: none; color: black '>Voltar</a> "
                + " </button>"
        );
        writer.println("</body></html>");
    }
}
