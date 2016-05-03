/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.bean;

import br.com.ms.syscon.seguranca.dominio.GrupoFuncionalidade;
import br.com.ms.syscon.seguranca.dominio.Modulo;
import br.com.ms.syscon.util.bean.CrudBean;
import br.com.ms.syscon.seguranca.servico.GrupoFuncionalidadeServico;
import br.com.ms.syscon.seguranca.servico.ModuloServico;
import br.com.ms.syscon.util.bean.primefaces.MessageView;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("grupoFuncionalidadeBean")
@ViewScoped
public class GrupoFuncionalidadeBean implements CrudBean<GrupoFuncionalidade, Long> {

    @Inject private GrupoFuncionalidadeServico grupoFuncionalidadeServico;
    @Inject private ModuloServico moduloServico;
    
    private MessageView ms;
    private GrupoFuncionalidade grupoFuncionalidade;
    private List<GrupoFuncionalidade>listGrupoFuncionalidade;
    private Modulo modulo;
    private List<Modulo>listModulo;

    @Override
    @PostConstruct
    public void init(){
        ms = new MessageView();
        grupoFuncionalidade = new GrupoFuncionalidade();
        listGrupoFuncionalidade = grupoFuncionalidadeServico.listar();
        modulo = new Modulo();
        listModulo = moduloServico.listar();
    }
    
    @Override
    public void atualizaListaCadastro() {
        listGrupoFuncionalidade = grupoFuncionalidadeServico.listar();
    }
    
    
    @Override
    public void btnGravar() {
        try{
            grupoFuncionalidadeServico.cadastrar(grupoFuncionalidade);
            setGrupoFuncionalidade(null);
            listGrupoFuncionalidade = grupoFuncionalidadeServico.listar();
            ms.mensagemSucesso();
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }


    @Override
    public void btnAlterar(GrupoFuncionalidade entity){
        try{
            grupoFuncionalidadeServico.alterar(entity);
            listGrupoFuncionalidade = grupoFuncionalidadeServico.listar();
            ms.mensagemSucesso();
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }
    
    @Override
    public void btnExcluir(GrupoFuncionalidade entity) {
        try{
            grupoFuncionalidadeServico.excluir(entity);
            listGrupoFuncionalidade = grupoFuncionalidadeServico.listar();
            ms.mensagemSucesso();
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }

    @Override
    public GrupoFuncionalidade btnPesquisar(Long key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<GrupoFuncionalidade> listar() {
        return grupoFuncionalidadeServico.listar();
    }

    
    
    
    
    
    
    
    
    
    
    public MessageView getMs() {
        return ms;
    }

    public void setMs(MessageView ms) {
        this.ms = ms;
    }

    public GrupoFuncionalidade getGrupoFuncionalidade() {
        return grupoFuncionalidade;
    }

    public void setGrupoFuncionalidade(GrupoFuncionalidade grupoFuncionalidade) {
        this.grupoFuncionalidade = grupoFuncionalidade;
    }

    public List<GrupoFuncionalidade> getListGrupoFuncionalidade() {
        return listGrupoFuncionalidade;
    }

    public void setListGrupoFuncionalidade(List<GrupoFuncionalidade> listGrupoFuncionalidade) {
        this.listGrupoFuncionalidade = listGrupoFuncionalidade;
    }

    public List<Modulo> getListModulo() {
        return listModulo;
    }

    public void setListModulo(List<Modulo> listModulo) {
        this.listModulo = listModulo;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

}
