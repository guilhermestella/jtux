/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.filtro;

import br.com.ms.syscon.seguranca.bean.SegurancaBean;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author guilherme.stella
 */
@WebFilter( filterName = "SegurancaFiltro", urlPatterns = "/*")
public class SegurancaFiltro implements Filter{

    private ServletContext context;
    private String sessao;
    private String uri;
    private String dadosAgente;
    @Inject private SegurancaBean segurancaBean;
    
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
    }

    /**
     * O filtro trará as informações do Usuário instanciado. Caso não haja usuário em instância a partir
     * do diretório /pages/* será feito um redirecionamento para a página de login.
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        /* Algumas requisições do PrimeFaces precisam ter este Encoding UTF-8 
           http://stackoverflow.com/questions/9634230/unicode-input-retrieved-via-primefaces-input-components-become-corrupted */
        request.setCharacterEncoding("UTF-8");

        //Pega o contextPath que no caso é o nome do projeto /syscon/
        String contextPath = ((HttpServletRequest) request).getContextPath();
        //Pega o URI solicitante. URI é o valor na frente do contextPath, ou seja, na frente de /syscon/
        uri = ((HttpServletRequest)request).getRequestURI();
        //Pega dados do Usuário (Navegador, etc)
        dadosAgente = ((HttpServletRequest) request).getHeader("User-Agent");
        //Pega ID da Sessão
        sessao = ((HttpServletRequest) request).getRequestedSessionId();

        if ( uri.contains("/pages/") ){
            if ( segurancaBean == null || segurancaBean.getUsuario() == null || segurancaBean.getUsuario().getId() == null ){
                ((HttpServletResponse) response).sendRedirect(contextPath + "/login.xhtml");
            }
        }
        
        System.out.println("--------Informações do Filtro Informações Filtro--------");
//        System.out.println("ID Usuário: " + segurancaBean.getUsuario().getId());
//        System.out.println("Nome Usuário: "+ segurancaBean.getUsuario().getNmUsuario());
        System.out.println("Dados Navegador: "+ dadosAgente);
        System.out.println("Session ID: " + sessao);
        chain.doFilter(request, response);
    }

    /**
     * 
     */
    @Override
    public void destroy() {
    }

    public String getSessao() {
        return sessao;
    }

    public void setSessao(String sessao) {
        this.sessao = sessao;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDadosAgente() {
        return dadosAgente;
    }

    public void setDadosAgente(String dadosAgente) {
        this.dadosAgente = dadosAgente;
    }
    
    
    
}
