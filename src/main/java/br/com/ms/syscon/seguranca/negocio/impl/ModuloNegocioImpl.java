/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.negocio.impl;


import br.com.ms.syscon.seguranca.dao.ModuloDao;
import br.com.ms.syscon.seguranca.dominio.Modulo;
import br.com.ms.syscon.seguranca.negocio.ModuloNegocio;
import br.com.ms.syscon.util.validation.NegocioException;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ModuloNegocioImpl implements ModuloNegocio {
    
    @Inject private ModuloDao moduloDao;
    
    @Override
    public void cadastrar(Modulo entity) {
        
        if ( entity.getNmDescricao().isEmpty() )
            throw new NegocioException("Nome Vazio");
        
        moduloDao.add(entity);
    }

    @Override
    public void alterar(Modulo entity) {
        
        if ( entity.getNmDescricao().isEmpty() )
            throw new NegocioException("Nome Vazio");
        
        moduloDao.update(entity);
    }

    @Override
    public void excluir(Modulo entity) {
        moduloDao.remove(entity);
    }

    @Override
    public Modulo pesquisar(Long Key) {
        return moduloDao.find(Key);
    }

    @Override
    public List<Modulo> listar() {
        return moduloDao.list();
    }

}
