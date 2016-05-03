/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.servico.impl;

import br.com.ms.syscon.gerenciamento.dominio.VariavelGlobal;
import br.com.ms.syscon.gerenciamento.negocio.VariavelGlobalNegocio;
import br.com.ms.syscon.gerenciamento.servico.VariavelGlobalServico;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class VariavelGlobalServicoImpl implements VariavelGlobalServico {

    @Inject VariavelGlobalNegocio variavelGlobalNegocio;
    
    @Override
    public void cadastrar(VariavelGlobal entity) {
        variavelGlobalNegocio.cadastrar(entity);
    }

    @Override
    public void alterar(VariavelGlobal entity) {
        variavelGlobalNegocio.alterar(entity);
    }

    @Override
    public void excluir(VariavelGlobal entity) {
        variavelGlobalNegocio.excluir(entity);
    }

    @Override
    public VariavelGlobal pesquisar(Long Key) {
        return variavelGlobalNegocio.pesquisar(Key);
    }

    @Override
    public List<VariavelGlobal> listar() {
        return variavelGlobalNegocio.listar();
    }
    
}
