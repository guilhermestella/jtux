/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.bean;

import br.com.ms.syscon.seguranca.dominio.Acao;
import br.com.ms.syscon.seguranca.dominio.Perfil;
import br.com.ms.syscon.seguranca.servico.AcaoServico;
import br.com.ms.syscon.seguranca.servico.PerfilServico;
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
@Named("associarPerfilAcaoBean")
@ViewScoped
public class AssociarPerfilAcaoBean implements AssociarBean<Acao>{

    @Inject private PerfilServico perfilServico;
    @Inject private AcaoServico acaoServico;
    
    
    private MessageView ms;
    private Perfil perfil;
    private List<Perfil>listPerfil;
    private Acao acao;
    private DualListModel<Acao>dual;
    
    
    @Override
    @PostConstruct
    public void init() {
        ms = new MessageView();
        perfil = new Perfil();
        listPerfil = perfilServico.listar();
        acao = new Acao();
        dual = new DualListModel();
    }

    @Override
    public void btnAssociar() {
        try{
            List<Acao>targets = getDual().getTarget();
            perfil.setListAcao(targets);
            perfilServico.alterar(perfil);
            ms.mensagemSucesso();
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }

    @Override
    public void gerarAssociacao(List<Acao> targets) {
        try{
            List<Acao>sources = acaoServico.listar();
            List<Acao>temp = new ArrayList<>();

            for ( Acao source : sources ){
                for ( Acao target : targets ){
                    if ( target.getId().equals(source.getId()) ){
                        temp.add(source);
                    }
                }
            }
            sources.removeAll(temp);
            setDual(new DualListModel<>(sources,targets));
        }catch(RuntimeException e){
            ms.mensagemErro(e);
            ms.mensagemAlerta("Nenhum Perfil Selecioando!");
        }
    }
    
    @Override
    public void btnAtualizarLista() {
        listPerfil = perfilServico.listar();
    }
    
    
    
    
    
    
    //Getters and Setters

    public MessageView getMs() {
        return ms;
    }

    public void setMs(MessageView ms) {
        this.ms = ms;
    }

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

    public Acao getAcao() {
        return acao;
    }

    public void setAcao(Acao acao) {
        this.acao = acao;
    }

    public DualListModel<Acao> getDual() {
        return dual;
    }

    public void setDual(DualListModel<Acao> dual) {
        this.dual = dual;
    }


    
    
    
    
    
    
    
    
    
}
