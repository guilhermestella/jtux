/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.negocio.impl;

import br.com.ms.syscon.gerenciamento.dao.ServicoDao;
import br.com.ms.syscon.gerenciamento.dominio.ArquivoConfiguracao;
import br.com.ms.syscon.gerenciamento.dominio.Servico;
import br.com.ms.syscon.gerenciamento.dominio.Servidor;
import br.com.ms.syscon.gerenciamento.negocio.ArquivoConfiguracaoNegocio;
import br.com.ms.syscon.gerenciamento.negocio.ServicoNegocio;
import br.com.ms.syscon.util.dominio.Arquivo;
import br.com.ms.syscon.util.validation.NegocioException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ServicoNegocioImpl implements ServicoNegocio {

    @Inject private ServicoDao servicoDao;
    
    @Inject private ArquivoConfiguracaoNegocio arquivoConfiguracaoNegocio;
    
    @Override
    public void cadastrar(Servico entity) {
        servicoDao.add(entity);
    }

    @Override
    public void alterar(Servico entity) {
        servicoDao.update(entity);
    }

    @Override
    public void excluir(Servico entity) {
        servicoDao.remove(entity);
    }

    @Override
    public Servico pesquisar(Long Key) {
        return servicoDao.find(Key);
    }

    @Override
    public List<Servico> listar() {
        return servicoDao.list();
    }

    @Override
    public List<Arquivo>montarArquivosConfiguracao(Servico servico, Servidor servidor){
        
        /* Serviço de Squid */
        if ( servico.getId() == 1 )
            return montarArquviosConfiguracaoSquid(servico, servidor);
        /* Serviço de DHCP */
        if ( servico.getId() == 2 )
            return montarArquviosConfiguracaoDHCP(servico, servidor);
        /* Serviço de Firewall */
        if ( servico.getId() == 3 )
            return montarArquviosConfiguracaoFirewall(servico, servidor);
        /* Serviço de Rede */
        if ( servico.getId() == 4 )
            return montarArquviosConfiguracaoRede(servico, servidor);
        /* Serviço de Apt */
        if ( servico.getId() == 5 )
            return montarArquviosConfiguracaoApt(servico, servidor);
        /* Serviço de Apache */
        if ( servico.getId() == 6 )
            return montarArquviosConfiguracaoApache(servico, servidor);
        /* Serviço de Sarg */
        if ( servico.getId() == 7 )
            return montarArquviosConfiguracaoSarg(servico, servidor);        
        else
            throw new NegocioException("Problema ao Montar Arquivo de Configuração");
    }

    @Override
    public List<Arquivo> montarArquviosConfiguracaoSquid(Servico servico, Servidor servidor) {
        System.out.println("Configurar Squid");
        /* É montado uma lista de arquivos que receberão os arquivos de configuração */
        List<Arquivo>arquivos = new ArrayList<>();
        
        /* Arquivos de Configuração do squid */
        for ( ArquivoConfiguracao arquivoConfiguracao : servico.getListArquivoConfiguracao() ){
            System.out.println("Gerar Arquivo: " + arquivoConfiguracao.getNmDescricao());
            
            /* Arquivo: squid.conf */
            if ( arquivoConfiguracao.getId() == 1 )
                arquivos.add(arquivoConfiguracaoNegocio.montarArquivoSquidConf(arquivoConfiguracao, servidor)); 

            /* Arquivo: g_full_access */
            if ( arquivoConfiguracao.getId() == 2 )
                arquivos.add(arquivoConfiguracaoNegocio.montarArquivoGFullAccess(arquivoConfiguracao, servidor));
            
            /* Arquivo: g_mid_access */
            if ( arquivoConfiguracao.getId() == 3 )
                arquivos.add(arquivoConfiguracaoNegocio.montarArquivoGMidAccess(arquivoConfiguracao, servidor));
            
            /* Arquivo: g_no_access */
            if ( arquivoConfiguracao.getId() == 4 )
                arquivos.add(arquivoConfiguracaoNegocio.montarArquivoGNoAccess(arquivoConfiguracao, servidor));
            
            /* Arquivo: url_liberado */
            if ( arquivoConfiguracao.getId() == 12 )
                arquivos.add(arquivoConfiguracaoNegocio.montarArquivoUrlLiberado(arquivoConfiguracao, servidor));
            
            /* Arquivo: url_bloqueado */
            if ( arquivoConfiguracao.getId() == 13 )
                arquivos.add(arquivoConfiguracaoNegocio.montarArquivoUrlBloqueado(arquivoConfiguracao, servidor));
            
        }

        return arquivos;
    }

    @Override
    public List<Arquivo> montarArquviosConfiguracaoDHCP(Servico servico, Servidor servidor) {
        System.out.println("Configurar DHCP");
        /* É montado uma lista de arquivos que receberão os arquivos de configuração */
        List<Arquivo>arquivos = new ArrayList<>();
        
        /* Arquivos de Configuração do DHCP */
        for ( ArquivoConfiguracao arquivoConfiguracao : servico.getListArquivoConfiguracao() ){
            System.out.println("Gerar Arquivo: " + arquivoConfiguracao.getNmDescricao());
            
            /* Arquivo: squid.conf */
            if ( arquivoConfiguracao.getId() == 5 )
                arquivos.add(arquivoConfiguracaoNegocio.montarArquivoDHCPConf(arquivoConfiguracao, servidor)); 
            
        }

        return arquivos;
    }

    @Override
    public List<Arquivo> montarArquviosConfiguracaoFirewall(Servico servico, Servidor servidor) {
        System.out.println("Configurar Firewall");
        /* É montado uma lista de arquivos que receberão os arquivos de configuração */
        List<Arquivo>arquivos = new ArrayList<>();
        
        /* Arquivos de Configuração do Firewall */
        for ( ArquivoConfiguracao arquivoConfiguracao : servico.getListArquivoConfiguracao() ){
            System.out.println("Gerar Arquivo: " + arquivoConfiguracao.getNmDescricao());
            
            /* Arquivo: firewall.sh */
            if ( arquivoConfiguracao.getId() == 6 )
                arquivos.add(arquivoConfiguracaoNegocio.montarArquivoFirewallSh(arquivoConfiguracao, servidor)); 
            
        }

        return arquivos;
    }

    @Override
    public List<Arquivo> montarArquviosConfiguracaoRede(Servico servico, Servidor servidor) {
        System.out.println("Configurar Rede");
        /* É montado uma lista de arquivos que receberão os arquivos de configuração */
        List<Arquivo>arquivos = new ArrayList<>();
        
        /* Arquivos de Configuração da Rede */
        for ( ArquivoConfiguracao arquivoConfiguracao : servico.getListArquivoConfiguracao() ){
            System.out.println("Gerar Arquivo: " + arquivoConfiguracao.getNmDescricao());
            
            /* Arquivo: interfaces */
            if ( arquivoConfiguracao.getId() == 7 )
                arquivos.add(arquivoConfiguracaoNegocio.montarArquivoInterfaces(arquivoConfiguracao, servidor)); 
            
        }

        return arquivos;
    }

    @Override
    public List<Arquivo> montarArquviosConfiguracaoApt(Servico servico, Servidor servidor) {
        System.out.println("Configurar Apt");
        /* É montado uma lista de arquivos que receberão os arquivos de configuração */
        List<Arquivo>arquivos = new ArrayList<>();
        
        /* Arquivos de Configuração da Rede */
        for ( ArquivoConfiguracao arquivoConfiguracao : servico.getListArquivoConfiguracao() ){
            System.out.println("Gerar Arquivo: " + arquivoConfiguracao.getNmDescricao());
            
            /* Arquivo: sources.list */
            if ( arquivoConfiguracao.getId() == 8 )
                arquivos.add(arquivoConfiguracaoNegocio.montarArquivoSourcesList(arquivoConfiguracao, servidor)); 
            
        }

        return arquivos;
    }

    @Override
    public List<Arquivo> montarArquviosConfiguracaoApache(Servico servico, Servidor servidor) {
        System.out.println("Configurar Apache");
        /* É montado uma lista de arquivos que receberão os arquivos de configuração */
        List<Arquivo>arquivos = new ArrayList<>();
        
        /* Arquivos de Configuração do Apache */
        for ( ArquivoConfiguracao arquivoConfiguracao : servico.getListArquivoConfiguracao() ){
            System.out.println("Gerar Arquivo: " + arquivoConfiguracao.getNmDescricao());
            
            /* Arquivo: apache2.conf */
            if ( arquivoConfiguracao.getId() == 9 )
                arquivos.add(arquivoConfiguracaoNegocio.montarArquivoApacheConf(arquivoConfiguracao, servidor)); 
            
            /* Arquivo: ports.conf */
            if ( arquivoConfiguracao.getId() == 10 )
                arquivos.add(arquivoConfiguracaoNegocio.montarArquivoPortsConf(arquivoConfiguracao, servidor)); 
            
        }

        return arquivos;
    }

    @Override
    public List<Arquivo> montarArquviosConfiguracaoSarg(Servico servico, Servidor servidor) {
        System.out.println("Configurar Sarg");
        /* É montado uma lista de arquivos que receberão os arquivos de configuração */
        List<Arquivo>arquivos = new ArrayList<>();
        
        /* Arquivos de Configuração do Sarg */
        for ( ArquivoConfiguracao arquivoConfiguracao : servico.getListArquivoConfiguracao() ){
            System.out.println("Gerar Arquivo: " + arquivoConfiguracao.getNmDescricao());
            
            /* Arquivo: crontab */
            if ( arquivoConfiguracao.getId() == 11 )
                arquivos.add(arquivoConfiguracaoNegocio.montarArquivoCrontab(arquivoConfiguracao, servidor)); 
            
        }

        return arquivos;
    }

    
}
