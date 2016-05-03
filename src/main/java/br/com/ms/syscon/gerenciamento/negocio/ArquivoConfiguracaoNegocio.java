/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.negocio;

import br.com.ms.syscon.gerenciamento.dominio.ArquivoConfiguracao;
import br.com.ms.syscon.gerenciamento.dominio.Servidor;
import br.com.ms.syscon.util.dominio.Arquivo;
import br.com.ms.syscon.util.negocio.GenericNegocio;
import com.jcraft.jsch.Session;
import java.nio.file.Path;

/**
 *
 * @author guilherme.stella
 */
public interface ArquivoConfiguracaoNegocio extends GenericNegocio<ArquivoConfiguracao, Long>{
    
    
    /**
     * Abre um Canal SFTP com a conexão SSH e Envia um Arquivo no local específico.
     * @param session
     * @param nmLocalArquivo
     * @param arquivo 
     */
    public void enviarArquivoConfiguracao(Session session, String nmLocalArquivo, Arquivo arquivo);
    
    /**
     * Faz Backup de um Arquivo para outro diretório
     * @param arquivoOriginal
     * @param arquivoBackup 
     */
    public void montarBackupArquivo(Path arquivoOriginal, Path arquivoBackup);
     
    public Arquivo montarArquivoSquidConf(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor);
    
    public Arquivo montarArquivoGFullAccess(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor);
    
    public Arquivo montarArquivoGMidAccess(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor);
    
    public Arquivo montarArquivoGNoAccess(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor);

    public Arquivo montarArquivoDHCPConf(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor);
    
    public Arquivo montarArquivoFirewallSh(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor);

    public Arquivo montarArquivoInterfaces(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor);
    
    public Arquivo montarArquivoSourcesList(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor);
    
    public Arquivo montarArquivoApacheConf(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor);
    
    public Arquivo montarArquivoPortsConf(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor);
    
    public Arquivo montarArquivoCrontab(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor);
    
    public Arquivo montarArquivoUrlLiberado(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor);
    
    public Arquivo montarArquivoUrlBloqueado(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor);
    
}
