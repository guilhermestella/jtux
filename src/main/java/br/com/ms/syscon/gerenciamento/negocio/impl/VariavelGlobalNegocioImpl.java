/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.negocio.impl;

import br.com.ms.syscon.gerenciamento.dao.VariavelGlobalDao;
import br.com.ms.syscon.gerenciamento.dominio.VariavelGlobal;
import br.com.ms.syscon.gerenciamento.negocio.VariavelGlobalNegocio;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class VariavelGlobalNegocioImpl implements VariavelGlobalNegocio {

    @Inject private VariavelGlobalDao variavelGlobalDao;
    
    @Override
    public void cadastrar(VariavelGlobal entity) {
        variavelGlobalDao.add(entity);
    }

    @Override
    public void alterar(VariavelGlobal entity) {
        variavelGlobalDao.update(entity);
    }

    @Override
    public void excluir(VariavelGlobal entity) {
        variavelGlobalDao.remove(entity);
    }

    @Override
    public VariavelGlobal pesquisar(Long Key) {
        return variavelGlobalDao.find(Key);
    }

    @Override
    public List<VariavelGlobal> listar() {
        return variavelGlobalDao.list();
    }
    
}
