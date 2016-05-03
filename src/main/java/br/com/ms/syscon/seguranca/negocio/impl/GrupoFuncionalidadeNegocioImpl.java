/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.negocio.impl;

import br.com.ms.syscon.seguranca.dao.GrupoFuncionalidadeDao;
import br.com.ms.syscon.seguranca.dominio.GrupoFuncionalidade;
import br.com.ms.syscon.seguranca.negocio.GrupoFuncionalidadeNegocio;
import br.com.ms.syscon.util.validation.NegocioException;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class GrupoFuncionalidadeNegocioImpl implements GrupoFuncionalidadeNegocio {

    @Inject private GrupoFuncionalidadeDao grupoFuncionalidadeDao;

    @Override
    public void cadastrar(GrupoFuncionalidade entity) {
        
        if ( entity.getNmDescricao().isEmpty() || entity.getModulo() == null )
            throw new NegocioException("Nome ou Módulo Vazio");
        
        grupoFuncionalidadeDao.add(entity);
    }

    @Override
    public void alterar(GrupoFuncionalidade entity) {
        if ( entity.getNmDescricao().isEmpty() || entity.getModulo() == null )
            throw new NegocioException("Nome ou Módulo Vazio");
        grupoFuncionalidadeDao.update(entity);
    }

    @Override
    public void excluir(GrupoFuncionalidade entity) {
        grupoFuncionalidadeDao.remove(entity);
    }

    @Override
    public GrupoFuncionalidade pesquisar(Long Key) {
        return grupoFuncionalidadeDao.find(Key);
    }

    @Override
    public List<GrupoFuncionalidade> listar() {
        return grupoFuncionalidadeDao.list();
    }

    
}
