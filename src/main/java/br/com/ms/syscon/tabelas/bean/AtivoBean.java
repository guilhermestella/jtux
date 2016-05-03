/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.tabelas.bean;

import br.com.ms.syscon.tabelas.domain.Ativo;
import br.com.ms.syscon.tabelas.servico.AtivoServico;
import br.com.ms.syscon.util.bean.CrudBean;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author guilherme.stella
 */
@Named("ativoBean")
@RequestScoped
public class AtivoBean implements CrudBean<Ativo, Long> {

    @Inject private AtivoServico ativoServico;
    
    private List<Ativo>listAtivo;
    
    @Override
    @PostConstruct
    public void init() {
        listAtivo = ativoServico.listar();
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
    public void btnAlterar(Ativo entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void btnExcluir(Ativo entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Ativo btnPesquisar(Long key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Ativo> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    //Getters and Setters
    
    public List<Ativo> getListAtivo() {
        return listAtivo;
    }

    public void setListAtivo(List<Ativo> listAtivo) {
        this.listAtivo = listAtivo;
    }


    
    
    
    
    
    
}
