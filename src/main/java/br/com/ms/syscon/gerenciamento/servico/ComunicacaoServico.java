/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.servico;

import br.com.ms.syscon.gerenciamento.dominio.Servico;
import br.com.ms.syscon.gerenciamento.dominio.Servidor;


/**
 *
 * @author guilherme.stella
 */
public interface ComunicacaoServico {
    
    public void testarConexaoSsh(Servidor servidor);

    public void configurarServico(Servidor servidor, Servico servico);
    
    public void configurarServicoTeste(Servidor servidor, Servico servico);
    
    public void executarComandoSsh(Servidor servidor, String comando);
    
    public void obterComandoInstalarServico(Servidor servidor, Servico servico);
    
    public void obterComandoServicoStart(Servidor servidor, Servico servico);
    
    public void obterComandoServicoStop(Servidor servidor, Servico servico);
    
    public void obterComandoServicoRestart(Servidor servidor, Servico servico);
    
    public void obterComandoServicoReload(Servidor servidor, Servico servico);
}
