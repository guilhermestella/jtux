/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.servico.impl;

import br.com.ms.syscon.gerenciamento.dominio.Servidor;
import br.com.ms.syscon.gerenciamento.negocio.ServidorNegocio;
import br.com.ms.syscon.gerenciamento.servico.ServidorServico;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ServidorServicoImpl implements ServidorServico {

    @Inject private ServidorNegocio servidorNegocio;

    @Override
    public void cadastrar(Servidor entity) {
        servidorNegocio.cadastrar(entity);
        servidorNegocio.adicionarArquivosConfiguracaoServidor(entity);
    }

    @Override
    public void alterar(Servidor entity) {
        servidorNegocio.alterar(entity);
    }

    @Override
    public void excluir(Servidor entity) {
        servidorNegocio.excluir(entity);
    }

    @Override
    public Servidor pesquisar(Long Key) {
        return servidorNegocio.pesquisar(Key);
    }

    @Override
    public List<Servidor> listar() {
        return servidorNegocio.listar();
    }

}
