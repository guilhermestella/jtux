/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.negocio.impl;

import br.com.ms.syscon.gerenciamento.dao.VariavelArquivoDao;
import br.com.ms.syscon.gerenciamento.dominio.VariavelArquivo;
import br.com.ms.syscon.gerenciamento.negocio.VariavelArquivoNegocio;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class VariavelArquivoNegocioImpl implements VariavelArquivoNegocio {

    @Inject private VariavelArquivoDao variavelArquivoDao;
    
    @Override
    public void cadastrar(VariavelArquivo entity) {
        variavelArquivoDao.add(entity);
    }

    @Override
    public void alterar(VariavelArquivo entity) {
        variavelArquivoDao.update(entity);
    }

    @Override
    public void excluir(VariavelArquivo entity) {
        variavelArquivoDao.remove(entity);
    }

    @Override
    public VariavelArquivo pesquisar(Long Key) {
        return variavelArquivoDao.find(Key);
    }

    @Override
    public List<VariavelArquivo> listar() {
        return variavelArquivoDao.list();
    }
    
}
