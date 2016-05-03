/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.servico.impl;

import br.com.ms.syscon.gerenciamento.dominio.Servico;
import br.com.ms.syscon.gerenciamento.dominio.Servidor;
import br.com.ms.syscon.gerenciamento.negocio.ArquivoConfiguracaoNegocio;
import br.com.ms.syscon.gerenciamento.negocio.ServicoNegocio;
import br.com.ms.syscon.gerenciamento.negocio.ServidorNegocio;
import br.com.ms.syscon.gerenciamento.servico.ComunicacaoServico;
import br.com.ms.syscon.util.dominio.Arquivo;
import br.com.ms.syscon.util.validation.NegocioException;
import com.jcraft.jsch.Session;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ComunicacaoServicoImpl implements ComunicacaoServico {

    @Inject private ServidorNegocio servidorNegocio;
    
    @Inject private ServicoNegocio servicoNegocio;
    
    @Inject private ArquivoConfiguracaoNegocio arquivoConfiguracaoNegocio;
    
    /**
     * Faz um teste de abertura e fechamento de Conexão SSH com o Servidor.
     * @param servidor 
     */
    @Override
    public void testarConexaoSsh(Servidor servidor) {
        Session session = servidorNegocio.abrirConexaoSsh(servidor);
        servidorNegocio.fecharConexaoSsh(session);
    }

    
    /**
     * É aberto uma conexão com o servidor via SSH, montado os arquivos de configuração do serviço
     * informado depois feito um backup e enviado ao servidor de destino via SFTP
     * @param servidor
     * @param servico 
     */
    @Override
    public void configurarServico(Servidor servidor, Servico servico) {
        /* Essa lista receberá todos os arquivos de configuração */
        List<Arquivo>arquivos;
        
        /* Aberto a sessão SSH */
        Session session = servidorNegocio.abrirConexaoSsh(servidor);

        /* É obtido os arquivos de configuração */
        arquivos = servicoNegocio.montarArquivosConfiguracao(servico, servidor);
        
        /* Aqui colocamos a Data e Hora no arquivo de Backup */
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date date = new Date();
        

        /* Para cada arquivo, é feito backup e depois enviado ao servidor de destino */
        for ( Arquivo arquivo : arquivos ){
            try{
                arquivoConfiguracaoNegocio.montarBackupArquivo(arquivo.toPath(), Paths.get(servidor.getNmRepositorioArquivos() + "\\" + arquivo + dateFormat.format(date)));
                arquivoConfiguracaoNegocio.enviarArquivoConfiguracao(session, arquivo.getNmLocalArquivoRemoto(), arquivo);
            }catch(RuntimeException e){
                throw new NegocioException("Problema no Backup. Verifique o Endereçamento do Caminho de Backup ou do Arquivo!");
            }
        }
        
        /* Fechado a conexão SSH */
        servidorNegocio.fecharConexaoSsh(session);
    }
    
    
    /**
     * Tem o mesmo funcionamento do Serviço ConfigurarServico, porém não envia os arquivos ao servidor
     * @param servidor
     * @param servico 
     */
    @Override
    public void configurarServicoTeste(Servidor servidor, Servico servico) {
        /* Essa lista receberá todos os arquivos de configuração */
        List<Arquivo>arquivos;

        /* É obtido os arquivos de configuração */
        arquivos = servicoNegocio.montarArquivosConfiguracao(servico, servidor);
        
        /* Aqui colocamos a Data e Hora no arquivo de Backup */
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date date = new Date();
        
        /* Para cada arquivo, é feito backup e depois enviado ao servidor de destino */
        for ( Arquivo arquivo : arquivos ){
            try{
                arquivoConfiguracaoNegocio.montarBackupArquivo(arquivo.toPath(), Paths.get(servidor.getNmRepositorioArquivos() + "\\" + arquivo + dateFormat.format(date)));
            }catch(RuntimeException e){
                throw new NegocioException("Problema no Backup. Verifique o Endereçamento do Caminho de Backup ou do Arquivo!");
            }
        }
    }

    /**
     * Executa um Comando em SSH na Sessão
     * @param servidor
     * @param comando 
     */
    @Override
    public void executarComandoSsh(Servidor servidor, String comando) {
        Session session = servidorNegocio.abrirConexaoSsh(servidor);
        Arquivo arquivo = servidorNegocio.executarComandoSsh(session, comando);
        arquivoConfiguracaoNegocio.montarBackupArquivo(arquivo.toPath(), Paths.get(servidor.getNmRepositorioArquivos() + "\\" + arquivo));
        servidorNegocio.fecharConexaoSsh(session);
        
    }

    @Override
    public void obterComandoInstalarServico(Servidor servidor, Servico servico) {
        Session session = servidorNegocio.abrirConexaoSsh(servidor);
        Arquivo arquivo = servidorNegocio.executarComandoSsh(session, servico.getNmMetodoInstalar());
        arquivoConfiguracaoNegocio.montarBackupArquivo(arquivo.toPath(), Paths.get(servidor.getNmRepositorioArquivos() + "\\" + arquivo));
        servidorNegocio.fecharConexaoSsh(session);
    }
    
    @Override
    public void obterComandoServicoStart(Servidor servidor, Servico servico) {
        Session session = servidorNegocio.abrirConexaoSsh(servidor);
        Arquivo arquivo = servidorNegocio.executarComandoSsh(session, servico.getNmMetodoStart());
        arquivoConfiguracaoNegocio.montarBackupArquivo(arquivo.toPath(), Paths.get(servidor.getNmRepositorioArquivos() + "\\" + arquivo));
        servidorNegocio.fecharConexaoSsh(session);
    }

    @Override
    public void obterComandoServicoStop(Servidor servidor, Servico servico) {
        Session session = servidorNegocio.abrirConexaoSsh(servidor);
        Arquivo arquivo = servidorNegocio.executarComandoSsh(session, servico.getNmMetodoStop());
        arquivoConfiguracaoNegocio.montarBackupArquivo(arquivo.toPath(), Paths.get(servidor.getNmRepositorioArquivos() + "\\" + arquivo));
        servidorNegocio.fecharConexaoSsh(session);
    }

    @Override
    public void obterComandoServicoRestart(Servidor servidor, Servico servico) {
        Session session = servidorNegocio.abrirConexaoSsh(servidor);
        Arquivo arquivo = servidorNegocio.executarComandoSsh(session, servico.getNmMetodoRestart());
        arquivoConfiguracaoNegocio.montarBackupArquivo(arquivo.toPath(), Paths.get(servidor.getNmRepositorioArquivos() + "\\" + arquivo));
        servidorNegocio.fecharConexaoSsh(session);
    }

    @Override
    public void obterComandoServicoReload(Servidor servidor, Servico servico) {
        Session session = servidorNegocio.abrirConexaoSsh(servidor);
        Arquivo arquivo = servidorNegocio.executarComandoSsh(session, servico.getNmMetodoReload());
        arquivoConfiguracaoNegocio.montarBackupArquivo(arquivo.toPath(), Paths.get(servidor.getNmRepositorioArquivos() + "\\" + arquivo));
        servidorNegocio.fecharConexaoSsh(session);
    }







    
}
