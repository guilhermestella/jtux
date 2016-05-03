/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.servico.impl;

import br.com.ms.syscon.seguranca.dominio.TipoAcao;
import br.com.ms.syscon.seguranca.negocio.TipoAcaoNegocio;
import br.com.ms.syscon.seguranca.servico.TipoAcaoServico;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TipoAcaoServicoImpl implements TipoAcaoServico {

    @Inject TipoAcaoNegocio tipoAcaoNegocio;
    
    @Override
    public void cadastrar(TipoAcao entity) {
        tipoAcaoNegocio.cadastrar(entity);
    }

    @Override
    public void alterar(TipoAcao entity) {
        tipoAcaoNegocio.alterar(entity);
    }

    @Override
    public void excluir(TipoAcao entity) {
        tipoAcaoNegocio.excluir(entity);
    }

    @Override
    public TipoAcao pesquisar(Long Key) {
        return tipoAcaoNegocio.pesquisar(Key);
    }

    @Override
    public List<TipoAcao> listar() {
        return tipoAcaoNegocio.listar();
    }
    
}
