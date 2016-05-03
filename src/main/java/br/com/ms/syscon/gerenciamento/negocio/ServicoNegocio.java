/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.negocio;

import br.com.ms.syscon.gerenciamento.dominio.Servico;
import br.com.ms.syscon.gerenciamento.dominio.Servidor;
import br.com.ms.syscon.util.dominio.Arquivo;
import br.com.ms.syscon.util.negocio.GenericNegocio;
import java.util.List;

/**
 *
 * @author guilherme.stella
 */
public interface ServicoNegocio extends GenericNegocio<Servico, Long>{
    
    /**
     * Monta arquivos de Configuração do Serviço de Squid
     * @param servico
     * @param servidor
     * @return 
     */
    public List<Arquivo> montarArquviosConfiguracaoSquid(Servico servico, Servidor servidor);

    
    /**
     * Monta Arquivos de Configuração do Servico de DHCP
     * @param servico
     * @param servidor
     * @return 
     */
    public List<Arquivo> montarArquviosConfiguracaoDHCP(Servico servico, Servidor servidor);

    /**
     * Montar Arquivos de Configuracao do Servico de Firewall
     * @param servico
     * @param servidor
     * @return 
     */
    public List<Arquivo> montarArquviosConfiguracaoFirewall(Servico servico, Servidor servidor);
    
    /**
     * Montar Arquivos de Configuracao do Servico de Rede
     * @param servico
     * @param servidor
     * @return 
     */
    public List<Arquivo> montarArquviosConfiguracaoRede(Servico servico, Servidor servidor);
    
    /**
     * Montar Arquivos de Configuracao do Servico Apt
     * @param servico
     * @param servidor
     * @return 
     */
    public List<Arquivo> montarArquviosConfiguracaoApt(Servico servico, Servidor servidor);
    
    public List<Arquivo> montarArquviosConfiguracaoApache(Servico servico, Servidor servidor);
    
    public List<Arquivo> montarArquviosConfiguracaoSarg(Servico servico, Servidor servidor);
    
    public List<Arquivo> montarArquivosConfiguracao(Servico servico, Servidor servidor);

}
