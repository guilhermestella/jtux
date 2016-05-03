/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.bean;

import br.com.ms.syscon.seguranca.dominio.Perfil;
import br.com.ms.syscon.seguranca.dominio.Usuario;
import br.com.ms.syscon.seguranca.servico.PerfilServico;
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
@Named("associarUsuarioPerfilBean")
@ViewScoped
public class AssociarUsuarioPerfilBean implements AssociarBean<Perfil>{

    @Inject private UsuarioServico usuarioServico;
    @Inject private PerfilServico perfilServico;
    
    private MessageView ms;
    private Usuario usuario;
    private List<Usuario>listUsuario;
    private Perfil perfil;
    private DualListModel<Perfil>dual;
    
    
    @Override
    @PostConstruct
    public void init() {
        ms = new MessageView();
        usuario = new Usuario();
        listUsuario = usuarioServico.listar();
        perfil = new Perfil();
        dual = new DualListModel();
    }
    
    @Override
    public void btnAssociar() {
        try{
            List<Perfil>targets = getDual().getTarget();
            usuario.setListPerfil(targets);
            usuarioServico.alterar(usuario);
            ms.mensagemSucesso();
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }

    @Override
    public void gerarAssociacao(List<Perfil> targets) {
        try{
            List<Perfil>sources = perfilServico.listar();
            List<Perfil>temp = new ArrayList<>();

            for ( Perfil source : sources ){
                for ( Perfil target : targets ){
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
    public void btnAtualizarLista() {
        listUsuario = usuarioServico.listar();
    }
    
    //Getters and Setters

    public MessageView getMs() {
        return ms;
    }

    public void setMs(MessageView ms) {
        this.ms = ms;
    }

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

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public DualListModel<Perfil> getDual() {
        return dual;
    }

    public void setDual(DualListModel<Perfil> dual) {
        this.dual = dual;
    }



}
