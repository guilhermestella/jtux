/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.bean;

import br.com.ms.syscon.gerenciamento.dominio.Servidor;
import br.com.ms.syscon.gerenciamento.dominio.SistemaOperacional;
import br.com.ms.syscon.gerenciamento.servico.ServidorServico;
import br.com.ms.syscon.gerenciamento.servico.SistemaOperacionalServico;
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
@Named("servidorBean")
@ViewScoped
public class ServidorBean implements CrudBean<Servidor, Long>{

    @Inject private ServidorServico servidorServico;
    @Inject private SistemaOperacionalServico sistemaOperacionalServico;
    
    private Servidor servidor;
    private List<Servidor>listServidor;
    private List<SistemaOperacional>listSistemaOperacional;
    private MessageView ms;
    
    @Override
    @PostConstruct
    public void init() {
        servidor = new Servidor();
        listServidor = servidorServico.listar();
        listSistemaOperacional = sistemaOperacionalServico.listar();
        ms = new MessageView();
    }

    @Override
    public void atualizaListaCadastro() {
        listSistemaOperacional = sistemaOperacionalServico.listar();
    }
    
    @Override
    public void btnGravar() {
        try{
            servidorServico.cadastrar(servidor);
            setServidor(null);
            listServidor = servidorServico.listar();
            ms.mensagemSucesso();
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }

    @Override
    public void btnAlterar(Servidor entity) {
        try{
            servidorServico.alterar(entity);
            listServidor = servidorServico.listar();
            ms.mensagemSucesso();
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }

    @Override
    public void btnExcluir(Servidor entity) {
        try{
            servidorServico.excluir(entity);
            listServidor = servidorServico.listar();
            ms.mensagemSucesso();
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }

    @Override
    public Servidor btnPesquisar(Long key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Servidor> listar() {
        return servidorServico.listar();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    //Getters and Setters

    public Servidor getServidor() {
        return servidor;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    public List<Servidor> getListServidor() {
        return listServidor;
    }

    public void setListServidor(List<Servidor> listServidor) {
        this.listServidor = listServidor;
    }

    public List<SistemaOperacional> getListSistemaOperacional() {
        return listSistemaOperacional;
    }

    public void setListSistemaOperacional(List<SistemaOperacional> listSistemaOperacional) {
        this.listSistemaOperacional = listSistemaOperacional;
    }
    
    
}
