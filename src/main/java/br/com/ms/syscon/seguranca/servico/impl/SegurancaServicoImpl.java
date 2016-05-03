/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.servico.impl;

import br.com.ms.syscon.seguranca.dominio.Acao;
import br.com.ms.syscon.seguranca.dominio.Funcionalidade;
import br.com.ms.syscon.seguranca.dominio.Usuario;
import br.com.ms.syscon.seguranca.negocio.UsuarioNegocio;
import br.com.ms.syscon.seguranca.servico.SegurancaServico;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class SegurancaServicoImpl implements SegurancaServico {

    @Inject private UsuarioNegocio usuarioNegocio;
    
    
    @Override
    public Usuario autenticarUsuario(Usuario usuario){
        return usuarioNegocio.autenticarUsuario(usuario);
    }

    @Override
    public List<Funcionalidade> obterPermissoesUsuario(Usuario usuario) {
        List<Acao>listAcao = usuarioNegocio.obterAcaoFuncionalidadeUsuario(usuario);
        return usuarioNegocio.obterFuncionalidadeUsuario(listAcao);
    }

}
