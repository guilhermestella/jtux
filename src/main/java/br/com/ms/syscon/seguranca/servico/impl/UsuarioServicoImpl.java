/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.servico.impl;

import br.com.ms.syscon.seguranca.dominio.Usuario;
import br.com.ms.syscon.seguranca.negocio.UsuarioNegocio;
import br.com.ms.syscon.seguranca.servico.UsuarioServico;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UsuarioServicoImpl implements UsuarioServico {

    @Inject private UsuarioNegocio usuarioNegocio;
    
    @Override
    public void cadastrar(Usuario entity) {
        usuarioNegocio.cadastrar(entity);
    }

    @Override
    public void alterar(Usuario entity) {
        usuarioNegocio.alterar(entity);
    }

    @Override
    public void excluir(Usuario entity) {
        usuarioNegocio.excluir(entity);
    }

    @Override
    public Usuario pesquisar(Long Key) {
        return usuarioNegocio.pesquisar(Key);
    }

    @Override
    public List<Usuario> listar() {
        return usuarioNegocio.listar();
    } 
    
    
    @Override
    public Usuario pesquisarUsuarioPorNome(Usuario usuario) {
        return usuarioNegocio.pesquisarPorNome(usuario);
    }


    
}
