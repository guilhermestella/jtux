/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.bean;

import br.com.ms.syscon.gerenciamento.dominio.Servico;
import br.com.ms.syscon.gerenciamento.servico.ServicoServico;
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
@Named("servicoBean")
@RequestScoped
public class ServicoBean implements CrudBean<Servico, Long>{

    @Inject private ServicoServico servicoServico;
    
    private List<Servico>listServico;
    private MessageView ms;
    
    
    @Override
    @PostConstruct
    public void init() {
        listServico = servicoServico.listar();
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
    public void btnAlterar(Servico entity) {
        try{
            servicoServico.alterar(entity);
            listServico = servicoServico.listar();
            ms.mensagemSucesso();
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }

    @Override
    public void btnExcluir(Servico entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Servico btnPesquisar(Long key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Servico> listar() {
        return servicoServico.listar();
    }
    
    
    
    
    //Gettes and Setters

    public List<Servico> getListServico() {
        return listServico;
    }

    public void setListServico(List<Servico> listServico) {
        this.listServico = listServico;
    }

    public MessageView getMs() {
        return ms;
    }

    public void setMs(MessageView ms) {
        this.ms = ms;
    }
    
    
    
}
