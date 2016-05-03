/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.negocio.impl;

import br.com.ms.syscon.gerenciamento.dao.SistemaOperacionalDao;
import br.com.ms.syscon.gerenciamento.dominio.SistemaOperacional;
import br.com.ms.syscon.gerenciamento.negocio.SistemaOperacionalNegocio;
import br.com.ms.syscon.util.validation.NegocioException;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class SistemaOperacionalNegocioImpl implements SistemaOperacionalNegocio {

    @Inject private SistemaOperacionalDao sistemaOperacionalDao;
    
    @Override
    public void cadastrar(SistemaOperacional entity) {
        
        if ( entity.getNmDescricao().isEmpty() )
            throw new NegocioException("Nome Vazio");
        
        sistemaOperacionalDao.add(entity);
    }

    @Override
    public void alterar(SistemaOperacional entity) {
        
        if ( entity.getNmDescricao().isEmpty() )
            throw new NegocioException("Nome Vazio");
        
        sistemaOperacionalDao.update(entity);
    }

    @Override
    public void excluir(SistemaOperacional entity) {
        sistemaOperacionalDao.remove(entity);
    }

    @Override
    public SistemaOperacional pesquisar(Long Key) {
        return sistemaOperacionalDao.find(Key);
    }

    @Override
    public List<SistemaOperacional> listar() {
        return sistemaOperacionalDao.list();
    }
    
}
