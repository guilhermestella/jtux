/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.servico.impl;

import br.com.ms.syscon.seguranca.dominio.Modulo;
import br.com.ms.syscon.seguranca.negocio.ModuloNegocio;
import br.com.ms.syscon.seguranca.servico.ModuloServico;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ModuloServicoImpl implements ModuloServico {

    @Inject ModuloNegocio moduloNegocio;
    
    @Override
    public void cadastrar(Modulo entity) {
        moduloNegocio.cadastrar(entity);
    }

    @Override
    public void alterar(Modulo entity) {
        moduloNegocio.alterar(entity);
    }

    @Override
    public void excluir(Modulo entity) {
        moduloNegocio.excluir(entity);
    }

    @Override
    public Modulo pesquisar(Long Key) {
        return moduloNegocio.pesquisar(Key);
    }

    @Override
    public List<Modulo> listar() {
        return moduloNegocio.listar();
    }
    
}
