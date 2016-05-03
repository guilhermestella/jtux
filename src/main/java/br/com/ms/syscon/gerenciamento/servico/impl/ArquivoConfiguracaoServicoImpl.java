/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.servico.impl;

import br.com.ms.syscon.gerenciamento.dominio.ArquivoConfiguracao;
import br.com.ms.syscon.gerenciamento.negocio.ArquivoConfiguracaoNegocio;
import br.com.ms.syscon.gerenciamento.servico.ArquivoConfiguracaoServico;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ArquivoConfiguracaoServicoImpl implements ArquivoConfiguracaoServico {

    @Inject private ArquivoConfiguracaoNegocio arquivoConfiguracaoNegocio;
    
    @Override
    public void cadastrar(ArquivoConfiguracao entity) {
        arquivoConfiguracaoNegocio.cadastrar(entity);
    }

    @Override
    public void alterar(ArquivoConfiguracao entity) {
        arquivoConfiguracaoNegocio.alterar(entity);
    }

    @Override
    public void excluir(ArquivoConfiguracao entity) {
        arquivoConfiguracaoNegocio.excluir(entity);
    }

    @Override
    public ArquivoConfiguracao pesquisar(Long Key) {
        return arquivoConfiguracaoNegocio.pesquisar(Key);
    }

    @Override
    public List<ArquivoConfiguracao> listar() {
        return arquivoConfiguracaoNegocio.listar();
    }
    
}
