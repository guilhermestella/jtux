/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.negocio;

import br.com.ms.syscon.gerenciamento.dominio.Servidor;
import br.com.ms.syscon.util.dominio.Arquivo;
import br.com.ms.syscon.util.negocio.GenericNegocio;
import com.jcraft.jsch.Session;

/**
 *
 * @author guilherme.stella
 */

public interface ServidorNegocio extends GenericNegocio<Servidor, Long>{
    
    public Session abrirConexaoSsh(Servidor servidor);
    
    public void fecharConexaoSsh(Session session);
    
    public Arquivo executarComandoSsh(Session session, String comando);
    
    public void adicionarArquivosConfiguracaoServidor(Servidor servidor);
    
}
