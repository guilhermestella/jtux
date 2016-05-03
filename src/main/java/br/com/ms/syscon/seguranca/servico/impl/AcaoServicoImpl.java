/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.servico.impl;

import br.com.ms.syscon.seguranca.dominio.Acao;
import br.com.ms.syscon.seguranca.negocio.AcaoNegocio;
import br.com.ms.syscon.seguranca.servico.AcaoServico;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AcaoServicoImpl implements AcaoServico {

    @Inject private AcaoNegocio acaoNegocio;
    
    @Override
    public void cadastrar(Acao entity) {
        acaoNegocio.cadastrar(entity);
    }

    @Override
    public void alterar(Acao entity) {
        acaoNegocio.alterar(entity);
    }

    @Override
    public void excluir(Acao entity) {
        acaoNegocio.excluir(entity);
    }

    @Override
    public Acao pesquisar(Long Key) {
        return acaoNegocio.pesquisar(Key);
    }

    @Override
    public List<Acao> listar() {
        return acaoNegocio.listar();
    }
    
}
