/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.servico.impl;

import br.com.ms.syscon.seguranca.dominio.Perfil;
import br.com.ms.syscon.seguranca.negocio.PerfilNegocio;
import br.com.ms.syscon.seguranca.servico.PerfilServico;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class PerfilServicoImpl implements PerfilServico {

    @Inject private PerfilNegocio perfilNegocio;
    
    @Override
    public void cadastrar(Perfil entity) {
        perfilNegocio.cadastrar(entity);
    }

    @Override
    public void alterar(Perfil entity) {
        perfilNegocio.alterar(entity);
    }

    @Override
    public void excluir(Perfil entity) {
        perfilNegocio.excluir(entity);
    }

    @Override
    public Perfil pesquisar(Long Key) {
        return perfilNegocio.pesquisar(Key);
    }

    @Override
    public List<Perfil> listar() {
        return perfilNegocio.listar();
    }
    
}
