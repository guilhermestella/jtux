/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.negocio.impl;

import br.com.ms.syscon.seguranca.dao.AcaoDao;
import br.com.ms.syscon.seguranca.dominio.Acao;
import br.com.ms.syscon.seguranca.negocio.AcaoNegocio;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AcaoNegocioImpl implements AcaoNegocio {

    @Inject private AcaoDao acaoDao;
    
    @Override
    public void cadastrar(Acao entity) {
        acaoDao.add(entity);
    }

    @Override
    public void alterar(Acao entity) {
        acaoDao.update(entity);
    }

    @Override
    public void excluir(Acao entity) {
        acaoDao.remove(entity);
    }

    @Override
    public Acao pesquisar(Long Key) {
        return acaoDao.find(Key);
    }

    @Override
    public List<Acao> listar() {
        return acaoDao.list();
    }
    
}
