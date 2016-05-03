/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.servico.impl;

import br.com.ms.syscon.seguranca.dominio.GrupoFuncionalidade;
import br.com.ms.syscon.seguranca.negocio.GrupoFuncionalidadeNegocio;
import br.com.ms.syscon.seguranca.servico.GrupoFuncionalidadeServico;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class GrupoFuncionalidadeServicoImpl implements GrupoFuncionalidadeServico {

    @Inject GrupoFuncionalidadeNegocio grupoFuncionalidadeNegocio;
    
    @Override
    public void cadastrar(GrupoFuncionalidade entity) {
        grupoFuncionalidadeNegocio.cadastrar(entity);
    }

    @Override
    public void alterar(GrupoFuncionalidade entity) {
        grupoFuncionalidadeNegocio.alterar(entity);
    }

    @Override
    public void excluir(GrupoFuncionalidade entity) {
        grupoFuncionalidadeNegocio.excluir(entity);
    }

    @Override
    public GrupoFuncionalidade pesquisar(Long Key) {
        return grupoFuncionalidadeNegocio.pesquisar(Key);
    }

    @Override
    public List<GrupoFuncionalidade> listar() {
        return grupoFuncionalidadeNegocio.listar();
    }

    
}
