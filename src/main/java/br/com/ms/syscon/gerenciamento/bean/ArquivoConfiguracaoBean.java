/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.bean;

import br.com.ms.syscon.gerenciamento.dominio.ArquivoConfiguracao;
import br.com.ms.syscon.gerenciamento.servico.ArquivoConfiguracaoServico;
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
@Named("arquivoConfiguracaoBean")
@RequestScoped
public class ArquivoConfiguracaoBean implements CrudBean<ArquivoConfiguracao, Long>{

    @Inject private ArquivoConfiguracaoServico arquivoConfiguracaoServico;

    private List<ArquivoConfiguracao>listArquivoConfiguracao;
    private MessageView ms;
    
    @Override
    @PostConstruct
    public void init() {
        listArquivoConfiguracao = arquivoConfiguracaoServico.listar();
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
    public void btnAlterar(ArquivoConfiguracao entity) {
        try{
            arquivoConfiguracaoServico.alterar(entity);
            listArquivoConfiguracao = arquivoConfiguracaoServico.listar();
            ms.mensagemSucesso();
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }

    @Override
    public void btnExcluir(ArquivoConfiguracao entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArquivoConfiguracao btnPesquisar(Long key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ArquivoConfiguracao> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    //Getters and Setters

    public List<ArquivoConfiguracao> getListArquivoConfiguracao() {
        return listArquivoConfiguracao;
    }

    public void setListArquivoConfiguracao(List<ArquivoConfiguracao> listArquivoConfiguracao) {
        this.listArquivoConfiguracao = listArquivoConfiguracao;
    }


    
}
