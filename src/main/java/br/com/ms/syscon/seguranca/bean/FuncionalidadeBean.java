/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.bean;

import br.com.ms.syscon.seguranca.dominio.Funcionalidade;
import br.com.ms.syscon.seguranca.dominio.GrupoFuncionalidade;
import br.com.ms.syscon.seguranca.servico.FuncionalidadeServico;
import br.com.ms.syscon.seguranca.servico.GrupoFuncionalidadeServico;
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
@Named("funcionalidadeBean")
@ViewScoped
public class FuncionalidadeBean implements CrudBean<Funcionalidade, Long>{

    @Inject private FuncionalidadeServico funcionalidadeServico;
    @Inject private GrupoFuncionalidadeServico grupoFuncionalidadeServico;
    
    private Funcionalidade funcionalidade;
    private List<Funcionalidade>listFuncionalidade;
    private List<GrupoFuncionalidade>listGrupoFuncionalidade;
    private MessageView ms;
    
    
    @Override
    @PostConstruct
    public void init() {
        funcionalidade = new Funcionalidade();
        listFuncionalidade = funcionalidadeServico.listar();
        listGrupoFuncionalidade = grupoFuncionalidadeServico.listar();
        ms = new MessageView();
    }
    
    @Override
    public void atualizaListaCadastro() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void btnGravar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void btnAlterar(Funcionalidade entity) {
        try{
            funcionalidadeServico.alterar(entity);
            listFuncionalidade = funcionalidadeServico.listar();
            ms.mensagemSucesso();
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }

    @Override
    public void btnExcluir(Funcionalidade entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Funcionalidade btnPesquisar(Long key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Funcionalidade> listar() {
        return funcionalidadeServico.listar();
    }
    
    
    
    
    
    //Getters and Setters

    public Funcionalidade getFuncionalidade() {
        return funcionalidade;
    }

    public void setFuncionalidade(Funcionalidade funcionalidade) {
        this.funcionalidade = funcionalidade;
    }

    public List<Funcionalidade> getListFuncionalidade() {
        return listFuncionalidade;
    }

    public void setListFuncionalidade(List<Funcionalidade> listFuncionalidade) {
        this.listFuncionalidade = listFuncionalidade;
    }

    public List<GrupoFuncionalidade> getListGrupoFuncionalidade() {
        return listGrupoFuncionalidade;
    }

    public void setListGrupoFuncionalidade(List<GrupoFuncionalidade> listGrupoFuncionalidade) {
        this.listGrupoFuncionalidade = listGrupoFuncionalidade;
    }

    public MessageView getMs() {
        return ms;
    }

    public void setMs(MessageView ms) {
        this.ms = ms;
    }
    
    
    
    
}
