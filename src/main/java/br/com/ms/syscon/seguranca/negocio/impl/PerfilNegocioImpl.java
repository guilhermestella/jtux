/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.negocio.impl;

import br.com.ms.syscon.seguranca.dao.PerfilDao;
import br.com.ms.syscon.seguranca.dominio.Perfil;
import br.com.ms.syscon.seguranca.negocio.PerfilNegocio;
import br.com.ms.syscon.util.validation.NegocioException;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class PerfilNegocioImpl implements PerfilNegocio {

    @Inject private PerfilDao perfilDao;
    
    @Override
    public void cadastrar(Perfil entity) {
        
        if ( entity.getNmDescricao().isEmpty() )
            throw new NegocioException("Nome Vazio");
        
        perfilDao.add(entity);
    }

    @Override
    public void alterar(Perfil entity) {
        
        if ( entity.getNmDescricao().isEmpty() )
            throw new NegocioException("Nome Vazio");
        
        perfilDao.update(entity);
    }

    @Override
    public void excluir(Perfil entity) {
        if ( !entity.getListAcao().isEmpty() ){
            throw new NegocioException("Existem Ações associadas à este perfil!");
        }
        if ( !entity.getListUsuario().isEmpty() ){
            throw new NegocioException("Existem Usuários associados à este perfil!");
        }
        perfilDao.remove(entity);
    }

    @Override
    public Perfil pesquisar(Long Key) {
        return perfilDao.find(Key);
    }

    @Override
    public List<Perfil> listar() {
        return perfilDao.list();
    }
    
}
