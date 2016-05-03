/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.bean;

import br.com.ms.syscon.gerenciamento.dominio.Servico;
import br.com.ms.syscon.gerenciamento.dominio.Servidor;
import br.com.ms.syscon.gerenciamento.servico.ServicoServico;
import br.com.ms.syscon.gerenciamento.servico.ServidorServico;
import br.com.ms.syscon.util.bean.AssociarBean;
import br.com.ms.syscon.util.bean.primefaces.MessageView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DualListModel;

/**
 *
 * @author guilherme.stella
 */
@Named("associarServidorServicoBean")
@ViewScoped
public class AssociarServidorServicoBean implements AssociarBean<Servico> {

    @Inject private ServidorServico servidorServico;
    @Inject private ServicoServico servicoServico;
    
    private MessageView ms;
    private Servidor servidor;
    private List<Servidor>listServidor;
    private Servico servico;
    private DualListModel<Servico>dual;
    
    
    
    @Override
    @PostConstruct
    public void init() {
        ms = new MessageView();
        servidor = new Servidor();
        listServidor = servidorServico.listar();
        servico = new Servico();
        dual = new DualListModel();
    }

    @Override
    public void btnAssociar() {
        try{
            List<Servico>targets = getDual().getTarget();
            servidor.setListServico(targets);
            servidorServico.alterar(servidor);
            ms.mensagemSucesso();
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }

    @Override
    public void gerarAssociacao(List<Servico> targets) {
        
        
        /* Código referente a duplicidade de servidores causado
           pois N servidores possuí N Arquivos... causa duplicidade */
        Set<Servico>targetsAux = new HashSet<>();
        targetsAux.addAll(targets);
        targets.clear();
        targets.addAll(targetsAux);

        
        try{
            List<Servico>sources = servicoServico.listar();
            List<Servico>temp = new ArrayList<>();

            for ( Servico source : sources ){
                for ( Servico target : targets ){
                    if ( target.getId().equals(source.getId()) ){
                        temp.add(source);
                    }
                }
            }
            sources.removeAll(temp);
            setDual(new DualListModel<>(sources,targets));
        }catch(RuntimeException e){
            ms.mensagemErro(e);
            ms.mensagemAlerta("Nenhum Servidor Selecioando!");
        }
    }
    
    
    @Override
    public void btnAtualizarLista() {
        listServidor = servidorServico.listar();
    }
    
    
    
    
    //Getters and Setters

    public MessageView getMs() {
        return ms;
    }

    public void setMs(MessageView ms) {
        this.ms = ms;
    }

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

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public DualListModel<Servico> getDual() {
        return dual;
    }

    public void setDual(DualListModel<Servico> dual) {
        this.dual = dual;
    }


    
    
    
    
}
