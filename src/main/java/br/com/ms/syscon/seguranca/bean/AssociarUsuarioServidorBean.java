/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.bean;

import br.com.ms.syscon.gerenciamento.dominio.Servidor;
import br.com.ms.syscon.gerenciamento.servico.ServidorServico;
import br.com.ms.syscon.seguranca.dominio.Usuario;
import br.com.ms.syscon.seguranca.servico.UsuarioServico;
import br.com.ms.syscon.util.bean.AssociarBean;
import br.com.ms.syscon.util.bean.primefaces.MessageView;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DualListModel;

/**
 *
 * @author guilherme.stella
 */
@Named("associarUsuarioServidorBean")
@ViewScoped
public class AssociarUsuarioServidorBean implements AssociarBean<Servidor>{

    @Inject private UsuarioServico usuarioServico;
    @Inject private ServidorServico servidorServico;
    
    private Usuario usuario;
    private List<Usuario>listUsuario;
    private MessageView ms;
    private DualListModel<Servidor>dual;
    
    @PostConstruct
    @Override
    public void init() {
        usuario = new Usuario();
        listUsuario = usuarioServico.listar();
        ms = new MessageView();
        dual = new DualListModel();
    }
    
    @Override
    public void btnAssociar() {
        try{
            List<Servidor>targets = getDual().getTarget();
            usuario.setListServidor(targets);
            usuarioServico.alterar(usuario);
            ms.mensagemSucesso();
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }

    @Override
    public void gerarAssociacao(List<Servidor> targets) {
        try{
            List<Servidor>sources = servidorServico.listar();
            List<Servidor>temp = new ArrayList<>();

            for ( Servidor source : sources ){
                for ( Servidor target : targets ){
                    if ( target.getId().equals(source.getId()) ){
                        temp.add(source);
                    }
                }
            }
            sources.removeAll(temp);
            setDual(new DualListModel<>(sources,targets));
        }catch(RuntimeException e){
            ms.mensagemErro(e);
            ms.mensagemAlerta("Nenhum Usuário Selecioando!");
        }
    }

    @Override
    public void btnAtualizarLista(){
        listUsuario = usuarioServico.listar();
    }
    
    
    
    //Getters and Setters

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public MessageView getMs() {
        return ms;
    }

    public void setMs(MessageView ms) {
        this.ms = ms;
    }

    public DualListModel<Servidor> getDual() {
        return dual;
    }

    public void setDual(DualListModel<Servidor> dual) {
        this.dual = dual;
    }

    public List<Usuario> getListUsuario() {
        return listUsuario;
    }

    public void setListUsuario(List<Usuario> listUsuario) {
        this.listUsuario = listUsuario;
    }
    
    
    
}
