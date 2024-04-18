package com.web.av1unidade.Filtro;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/exibirProdutos.html", "/listaProdutos.html", "/listaProdutosCliente?"})
public class FilterAuth implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse response = ((HttpServletResponse) servletResponse);
        HttpServletRequest request = ((HttpServletRequest) servletRequest);

        HttpSession session = request.getSession(false);

        if (session == null){
            response.sendRedirect("index.html?msg=ErroLoginNãoRealizado");
        }else{
            Boolean logado = (Boolean) session.getAttribute("logado");
            if (!logado || logado == null){
                response.sendRedirect("index.html?msg=ErroLoginNãoRealizado");
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}