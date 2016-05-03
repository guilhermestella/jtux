/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.bean;

import br.com.ms.syscon.gerenciamento.dominio.SistemaOperacional;
import br.com.ms.syscon.gerenciamento.servico.SistemaOperacionalServico;
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
@Named("sistemaOperacionalBean")
@RequestScoped
public class SistemaOperacionalBean implements CrudBean<SistemaOperacional, Long>{

    @Inject SistemaOperacionalServico sistemaOperacionalServico;

    private SistemaOperacional sistemaOperacional;
    private List<SistemaOperacional>listSistemaOperacional;
    private MessageView ms;
    
    
    @Override
    @PostConstruct
    public void init() {
        sistemaOperacional = new SistemaOperacional();
        listSistemaOperacional = sistemaOperacionalServico.listar();
        ms = new MessageView();
    }
    
    @Override
    public void atualizaListaCadastro() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void btnGravar() {
        try{
            sistemaOperacionalServico.cadastrar(sistemaOperacional);
            setSistemaOperacional(null);
            listSistemaOperacional = sistemaOperacionalServico.listar();
            ms.mensagemSucesso();
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }

    @Override
    public void btnAlterar(SistemaOperacional entity) {
        try{
            sistemaOperacionalServico.alterar(entity);
            listSistemaOperacional = sistemaOperacionalServico.listar();
            ms.mensagemSucesso();
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }

    @Override
    public void btnExcluir(SistemaOperacional entity) {
        try{
            sistemaOperacionalServico.excluir(entity);
            listSistemaOperacional = sistemaOperacionalServico.listar();
            ms.mensagemSucesso();
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }

    @Override
    public SistemaOperacional btnPesquisar(Long key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SistemaOperacional> listar() {
        return sistemaOperacionalServico.listar();
    }
    
    
    
    
    
    
    //Getters and Setters

    public SistemaOperacional getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(SistemaOperacional sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public List<SistemaOperacional> getListSistemaOperacional() {
        return listSistemaOperacional;
    }

    public void setListSistemaOperacional(List<SistemaOperacional> listSistemaOperacional) {
        this.listSistemaOperacional = listSistemaOperacional;
    }

    public MessageView getMs() {
        return ms;
    }

    public void setMs(MessageView ms) {
        this.ms = ms;
    }
    
    
    
    
    
}
