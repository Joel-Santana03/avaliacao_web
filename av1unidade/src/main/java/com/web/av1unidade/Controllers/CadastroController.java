package com.web.av1unidade.Controllers;

import com.web.av1unidade.DAO.ClienteDAO;
import com.web.av1unidade.Models.Cliente;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;

@Controller
public class CadastroController {

    @PostMapping("/doCadastro")
    public void doCadastro(@RequestParam("nome") String nome,
                           @RequestParam("email") String email,
                           @RequestParam("senha") String senha,
                           HttpServletResponse response) throws IOException {
        ClienteDAO cDAO = new ClienteDAO();
        Cliente c = new Cliente(nome, email, senha);

        try {
            cDAO.cadastrar(c);
            response.sendRedirect("cadastro.html?msg=ClienteCadastrado");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendRedirect("cadastro.html?msg=Erro");
        }
    }
}
