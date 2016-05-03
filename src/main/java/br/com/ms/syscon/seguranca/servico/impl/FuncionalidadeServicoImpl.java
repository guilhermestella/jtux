/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.servico.impl;

import br.com.ms.syscon.seguranca.dominio.Funcionalidade;
import br.com.ms.syscon.seguranca.negocio.FuncionalidadeNegocio;
import br.com.ms.syscon.seguranca.servico.FuncionalidadeServico;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class FuncionalidadeServicoImpl implements FuncionalidadeServico {

    @Inject FuncionalidadeNegocio funcionalidadeNegocio;
    
    @Override
    public void cadastrar(Funcionalidade entity) {
        funcionalidadeNegocio.cadastrar(entity);
    }

    @Override
    public void alterar(Funcionalidade entity) {
        funcionalidadeNegocio.alterar(entity);
    }

    @Override
    public void excluir(Funcionalidade entity) {
        funcionalidadeNegocio.excluir(entity);
    }

    @Override
    public Funcionalidade pesquisar(Long Key) {
        return funcionalidadeNegocio.pesquisar(Key);
    }

    @Override
    public List<Funcionalidade> listar() {
        return funcionalidadeNegocio.listar();
    }
    
}
