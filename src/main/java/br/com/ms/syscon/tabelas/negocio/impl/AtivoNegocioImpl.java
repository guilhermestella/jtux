/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.tabelas.negocio.impl;

import br.com.ms.syscon.tabelas.dao.AtivoDao;
import br.com.ms.syscon.tabelas.domain.Ativo;
import br.com.ms.syscon.tabelas.negocio.AtivoNegocio;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AtivoNegocioImpl implements AtivoNegocio {

    @Inject private AtivoDao ativoDao;
    
    @Override
    public void cadastrar(Ativo entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void alterar(Ativo entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void excluir(Ativo entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Ativo pesquisar(Long Key) {
        return ativoDao.find(Key);
    }

    @Override
    public List<Ativo> listar() {
        return ativoDao.list();
    }
    
}
