/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.bean;

import br.com.ms.syscon.util.bean.CrudBean;
import br.com.ms.syscon.seguranca.dominio.Modulo;
import br.com.ms.syscon.seguranca.servico.ModuloServico;
import br.com.ms.syscon.util.bean.primefaces.MessageView;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("moduloBean")
@RequestScoped
public class ModuloBean implements CrudBean<Modulo, Long> {

    @Inject private ModuloServico moduloServico;
    
    private MessageView ms;
    private Modulo modulo;
    private List<Modulo>listModulo;

    @Override
    @PostConstruct
    public void init(){
        ms = new MessageView();
        modulo = new Modulo();
        listModulo = moduloServico.listar();
    }
    
    @Override
    public void atualizaListaCadastro() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void btnGravar() {
        try{
            moduloServico.cadastrar(modulo);
            setModulo(null);
            listModulo = moduloServico.listar();
            ms.mensagemSucesso();
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }


    @Override
    public void btnAlterar(Modulo entity){
        try{
            moduloServico.alterar(entity);
            setModulo(null);
            listModulo = moduloServico.listar();
            ms.mensagemSucesso();
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }
    
    @Override
    public void btnExcluir(Modulo entity) {
        try{
            moduloServico.excluir(entity);
            listModulo = moduloServico.listar();
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }

    @Override
    public Modulo btnPesquisar(Long key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Modulo> listar() {
        return moduloServico.listar();
    }
    
    
    
    
    
    
    
    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public List<Modulo> getListModulo() {
        return listModulo;
    }

    public void setListModulo(List<Modulo> listModulo) {
        this.listModulo = listModulo;
    }

}
