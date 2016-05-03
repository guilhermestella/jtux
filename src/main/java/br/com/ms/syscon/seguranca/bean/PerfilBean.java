/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.bean;

import br.com.ms.syscon.seguranca.dominio.Perfil;
import br.com.ms.syscon.seguranca.servico.PerfilServico;
import br.com.ms.syscon.util.bean.CrudBean;
import br.com.ms.syscon.util.bean.primefaces.MessageView;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author guilherme.stella
 */
@Named("perfilBean")
@ViewScoped
public class PerfilBean implements CrudBean<Perfil, Long>{

    @Inject private PerfilServico perfilServico;
    
    private Perfil perfil;
    private List<Perfil>listPerfil;
    private MessageView ms;
    
    
    @Override
    @PostConstruct
    public void init() {
        perfil = new Perfil();
        listPerfil = perfilServico.listar();
        ms = new MessageView();
    }
    
    @Override
    public void atualizaListaCadastro() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void btnGravar() {
        try{
            perfilServico.cadastrar(perfil);
            setPerfil(null);
            listPerfil = perfilServico.listar();
            ms.mensagemSucesso();
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }

    @Override
    public void btnAlterar(Perfil entity) {
        try{
            perfilServico.alterar(entity);
            setPerfil(null);
            listPerfil = perfilServico.listar();
            ms.mensagemSucesso();
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }

    @Override
    public void btnExcluir(Perfil entity) {
        try{
            perfilServico.excluir(entity);
            listPerfil = perfilServico.listar();
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }

    @Override
    public Perfil btnPesquisar(Long key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Perfil> listar() {
        return perfilServico.listar();
    }

    
    
    
    
    
    
    
    
    
    //Getters and Setters

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public List<Perfil> getListPerfil() {
        return listPerfil;
    }

    public void setListPerfil(List<Perfil> listPerfil) {
        this.listPerfil = listPerfil;
    }

    public MessageView getMs() {
        return ms;
    }

    public void setMs(MessageView ms) {
        this.ms = ms;
    }
}
