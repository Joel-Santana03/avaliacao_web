package com.web.av1unidade.Controllers;

import com.web.av1unidade.DAO.ProdutoDAO;
import com.web.av1unidade.Models.Produto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class ProdutoController {

    @GetMapping("/listarProdutos")
    public void listarProdutos(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ProdutoDAO dao = new ProdutoDAO();
        var writer = response.getWriter();
        String browser = request.getHeader("tela");
        var listarProdutos = dao.listaProdutos();

        response.setContentType("text/html");

        writer.println("<html>" + "<head>" + "<title>Produtos</title>"+"</head>" +
                "<body style='display: flex; flex-direction: column; align-items: center; justify-content: center; background-color: transparent; height: 100vh; margin: 0'>");
        if (browser != null) {
            writer.println(browser);
        }

        writer.println("<h1>Controle de Produtos</h1>");
        writer.println("<table border='1' style='background-color: white'>" +
                "<tr>" +
                "<th>Nome</th>" +
                "<th>Descrição</th>" +
                "<th>Preço</th>" +
                "<th>Estoque</th>" +
                "</tr>");

        for (var t1 : listarProdutos){
            writer.println("<tr>" +
                    "<td>" + t1.getNome() + "</td>" +
                    "<td>" + t1.getDescricao() + "</td>" +
                    "<td>" + t1.getPreco() + "</td>" +
                    "<td>" + t1.getEstoque() + "</td>" +
                    "</tr>");
        }
        writer.println("</table>" + "</br>" +
                "<button>" + "<a href='exibirProdutos.html' style='text-decoration: none; color: black'>Voltar</a>"
        );
        writer.println("</body>" + "</html>");
    }

    @GetMapping("/redirectCadastro")
    public void redirectCadastro(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.sendRedirect("cadastroProdutos.html");
    }

    @PostMapping("/cadastrarProduto")
    public void cadastrar(HttpServletRequest request, HttpServletResponse response) throws IOException{
        var nome = request.getParameter("nome");
        var descricao = request.getParameter("descricao");
        var preco = Integer.parseInt(request.getParameter("preco"));
        var estoque = Integer.parseInt(request.getParameter("estoque"));

        Produto p = new Produto();
        ProdutoDAO pd = new ProdutoDAO();
        p.setNome(nome);
        p.setDescricao(descricao);
        p.setPreco(preco);
        p.setEstoque(estoque);
        pd.cadastrar(p);

        HttpSession session = request.getSession(false);
        if(session != null){
            response.sendRedirect("exibirProdutos.html");
        }else{
            response.sendRedirect("index.html");
        }
    }
}