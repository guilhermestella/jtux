/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.bean;

import br.com.ms.syscon.gerenciamento.dominio.ArquivoConfiguracao;
import br.com.ms.syscon.gerenciamento.dominio.Servico;
import br.com.ms.syscon.gerenciamento.dominio.Servidor;
import br.com.ms.syscon.gerenciamento.dominio.VariavelArquivo;
import br.com.ms.syscon.gerenciamento.negocio.VariavelArquivoNegocio;
import br.com.ms.syscon.gerenciamento.servico.ComunicacaoServico;
import br.com.ms.syscon.gerenciamento.servico.ServidorServico;
import br.com.ms.syscon.util.bean.GenericBean;
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
@Named("monitorarServidorBean")
@RequestScoped
public class MonitorarServidorBean implements GenericBean {

    @Inject private ServidorServico servidorServico;
    @Inject private VariavelArquivoNegocio variavelArquivoNegocio;
    @Inject private ComunicacaoServico comunicacaoServico;

    private Servidor servidor;
    private MessageView ms;
    private String comandoExecutar;

    @Override
    @PostConstruct
    public void init() {
        servidor = new Servidor();
        ms = new MessageView();
    }

    public void btnTestarConexao(Servidor servidor){
        try{
            comunicacaoServico.testarConexaoSsh(servidor);
            ms.mensagemSucesso("Conectado com Sucesso!");
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
        
    }
    
    public void btnExecutarComandoConsole(Servidor servidor, String comando){
        try{
            comunicacaoServico.executarComandoSsh(servidor, comando);
            setComandoExecutar("");
            ms.mensagemSucesso("Comando Executado!");
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
        
    }
    
    public void btnSalvarAlteracaoArquivoConfiguracao(ArquivoConfiguracao arquivoConfiguracao){
        try {
            for ( VariavelArquivo var : arquivoConfiguracao.getListVariavelArquivo() ){
                System.out.println(var.getDsCampo());
                variavelArquivoNegocio.alterar(var);
            }
            ms.mensagemSucesso("Alterações Feitas!");
       }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
        
    }

    public void btnInstalarServico(Servidor servidor, Servico servico){
        try{
            servico.getListServidor().clear();
            servico.getListServidor().add(servidor);
            comunicacaoServico.obterComandoInstalarServico(servidor, servico);
            ms.mensagemSucesso("Serviço Instalado!");
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
        
    }
    
    public void btnEnviarArquivosConfiguracao(Servidor servidor, Servico servico){
        try{
            servico.getListServidor().clear();
            servico.getListServidor().add(servidor);
            comunicacaoServico.configurarServico(servidor, servico);
            ms.mensagemSucesso("Arquivos Enviados!");
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }
    
    public void btnGerarArquivosTeste(Servidor servidor, Servico servico){
        try{
            servico.getListServidor().clear();
            servico.getListServidor().add(servidor);
            comunicacaoServico.configurarServicoTeste(servidor, servico);
            ms.mensagemSucesso("Arquivos de Testes Enviados!");
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }
    
    public void btnStartServico(Servidor servidor, Servico servico){
        try{
            servico.getListServidor().clear();
            servico.getListServidor().add(servidor);
            comunicacaoServico.obterComandoServicoStart(servidor, servico);
            ms.mensagemSucesso("Serviço Iniciado!");
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }
    
    public void btnStopServico(Servidor servidor, Servico servico){
        try{
            servico.getListServidor().clear();
            servico.getListServidor().add(servidor);
            comunicacaoServico.obterComandoServicoStop(servidor, servico);
            ms.mensagemSucesso("Serviço Iniciado!");
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }
    
    public void btnRestartServico(Servidor servidor, Servico servico){
        try{
            servico.getListServidor().clear();
            servico.getListServidor().add(servidor);
            comunicacaoServico.obterComandoServicoRestart(servidor, servico);
            ms.mensagemSucesso("Serviço Iniciado!");
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }
    
    public void btnReloadServico(Servidor servidor, Servico servico){
        try{
            servico.getListServidor().clear();
            servico.getListServidor().add(servidor);
            comunicacaoServico.obterComandoServicoReload(servidor, servico);
            ms.mensagemSucesso("Serviço Iniciado!");
        }catch(RuntimeException e){
            ms.mensagemErro(e);
        }
    }
    
    public List<VariavelArquivo>obterListVariavelArquivo(Servidor servidor){
        return servidor.getListVariavelArquivo();
    }
    
    
    
    
    
    
    
    
    //Getters and Setters

    public ServidorServico getServidorServico() {
        return servidorServico;
    }

    public void setServidorServico(ServidorServico servidorServico) {
        this.servidorServico = servidorServico;
    }

    public Servidor getServidor() {
        return servidor;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    public String getComandoExecutar() {
        return comandoExecutar;
    }

    public void setComandoExecutar(String comandoExecutar) {
        this.comandoExecutar = comandoExecutar;
    }

}
