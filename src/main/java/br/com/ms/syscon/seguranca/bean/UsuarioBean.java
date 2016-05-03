/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.bean;

import br.com.ms.syscon.seguranca.dominio.Usuario;
import br.com.ms.syscon.seguranca.servico.UsuarioServico;
import br.com.ms.syscon.util.bean.CrudBean;
import br.com.ms.syscon.util.bean.primefaces.MessageView;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author guilherme.stella
 */
@Named("usuarioBean")
@RequestScoped
public class UsuarioBean implements CrudBean<Usuario, Long>{
    
    @Inject private UsuarioServico usuarioServico;
    
    private Usuario usuario;
    private List<Usuario>listUsuario;
    private MessageView ms;
    
    @PostConstruct
    @Override
    public void init(){
        usuario = new Usuario();
        listUsuario = usuarioServico.listar();
        ms = new MessageView();
    }

    @Override
    public void atualizaListaCadastro() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void btnGravar() {
        try{
            usuarioServico.cadastrar(usuario);
            setUsuario(null);
            listUsuario = usuarioServico.listar();
            ms.mensagemSucesso();
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }

    @Override
    public void btnAlterar(Usuario entity) {
        try{
            usuarioServico.alterar(entity);
            listUsuario = usuarioServico.listar();
            ms.mensagemSucesso();
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }

    @Override
    public void btnExcluir(Usuario entity) {
        usuarioServico.excluir(entity);
        listUsuario = usuarioServico.listar();
    }

    @Override
    public Usuario btnPesquisar(Long key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Usuario> listar() {
        return usuarioServico.listar();
    }

    
    
    
    //Getters and Setters
    
    
    
    
    
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getListUsuario() {
        return listUsuario;
    }

    public void setListUsuario(List<Usuario> listUsuario) {
        this.listUsuario = listUsuario;
    }

    public MessageView getMs() {
        return ms;
    }

    public void setMs(MessageView ms) {
        this.ms = ms;
    }

}
