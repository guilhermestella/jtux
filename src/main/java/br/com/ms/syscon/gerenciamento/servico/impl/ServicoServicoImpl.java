/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.servico.impl;

import br.com.ms.syscon.gerenciamento.dominio.Servico;
import br.com.ms.syscon.gerenciamento.negocio.ServicoNegocio;
import br.com.ms.syscon.gerenciamento.servico.ServicoServico;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ServicoServicoImpl implements ServicoServico {

    @Inject private ServicoNegocio servicoNegocio;
    
    
    @Override
    public void cadastrar(Servico entity) {
        servicoNegocio.cadastrar(entity);
    }

    @Override
    public void alterar(Servico entity) {
        servicoNegocio.alterar(entity);
    }

    @Override
    public void excluir(Servico entity) {
        servicoNegocio.excluir(entity);
    }

    @Override
    public Servico pesquisar(Long Key) {
        return servicoNegocio.pesquisar(Key);
    }

    @Override
    public List<Servico> listar() {
        return servicoNegocio.listar();
    }

}
