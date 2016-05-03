/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.dao;

import br.com.ms.syscon.seguranca.dominio.Usuario;
import br.com.ms.syscon.util.dao.GenericDao;
import java.util.List;

/**
 *
 * @author guilherme.stella
 */
public interface UsuarioDao extends GenericDao<Usuario,Long>{
    
    public List<Usuario> listByName(Usuario usuario);
    
    public Usuario findByName(Usuario usuario);
}
