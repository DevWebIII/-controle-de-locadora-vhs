package br.edu.ifpr.foz.controle_de_locadora_vhs.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import br.edu.ifpr.foz.controle_de_locadora_vhs.entities.Usuario;

import java.io.IOException;

@Component
public class AutenticacaoFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
          throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession sessao = httpRequest.getSession(false);
        Usuario usuario = (sessao != null) ? (Usuario) sessao.getAttribute("usuarioLogado") : null;

        String uri = httpRequest.getRequestURI();

        boolean acessoLiberado =
            uri.equals("/login")
            || uri.equals("/cadastro");

        if (acessoLiberado || usuario != null) {
            chain.doFilter(httpRequest, httpResponse); 
        } else {
            httpResponse.sendRedirect("/login");
        }
    }
}
