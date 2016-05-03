/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.negocio.impl;

import br.com.ms.syscon.seguranca.dao.TipoAcaoDao;
import br.com.ms.syscon.seguranca.dominio.TipoAcao;
import br.com.ms.syscon.seguranca.negocio.TipoAcaoNegocio;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TipoAcaoNegocioImpl implements TipoAcaoNegocio {

    @Inject private TipoAcaoDao tipoAcaoDao;
    
    @Override
    public void cadastrar(TipoAcao entity) {
        tipoAcaoDao.add(entity);
    }

    @Override
    public void alterar(TipoAcao entity) {
        tipoAcaoDao.update(entity);
    }

    @Override
    public void excluir(TipoAcao entity) {
        tipoAcaoDao.remove(entity);
    }

    @Override
    public TipoAcao pesquisar(Long Key) {
        return tipoAcaoDao.find(Key);
    }

    @Override
    public List<TipoAcao> listar() {
        return tipoAcaoDao.list();
    }
    
}
