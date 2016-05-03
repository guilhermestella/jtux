/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.servico;

import br.com.ms.syscon.seguranca.dominio.Usuario;
import br.com.ms.syscon.util.servico.CrudServico;

/**
 *
 * @author guilherme.stella
 */
public interface UsuarioServico extends CrudServico<Usuario, Long>{
    
    public Usuario pesquisarUsuarioPorNome(Usuario usuario);
    
}
