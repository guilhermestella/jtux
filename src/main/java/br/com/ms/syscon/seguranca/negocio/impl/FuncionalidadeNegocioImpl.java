/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.negocio.impl;

import br.com.ms.syscon.seguranca.dao.FuncionalidadeDao;
import br.com.ms.syscon.seguranca.dominio.Funcionalidade;
import br.com.ms.syscon.seguranca.negocio.FuncionalidadeNegocio;
import br.com.ms.syscon.util.validation.NegocioException;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class FuncionalidadeNegocioImpl implements FuncionalidadeNegocio {

    @Inject private FuncionalidadeDao funcionalidadeDao;
    
    @Override
    public void cadastrar(Funcionalidade entity) {
        if ( entity.getGrupoFuncionalidade() == null ){
            throw new NegocioException("Não é permitido Cadastrar sem Grupo");
        }
        funcionalidadeDao.add(entity);
    }

    @Override
    public void alterar(Funcionalidade entity) {
        if ( entity.getGrupoFuncionalidade() == null ){
            throw new NegocioException("Não é permitido Alterar sem Grupo");
        }
        funcionalidadeDao.update(entity);
    }

    @Override
    public void excluir(Funcionalidade entity) {
        funcionalidadeDao.remove(entity);
    }

    @Override
    public Funcionalidade pesquisar(Long Key) {
        return funcionalidadeDao.find(Key);
    }

    @Override
    public List<Funcionalidade> listar() {
        return funcionalidadeDao.list();
    }
    
}
