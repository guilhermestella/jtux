/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.dao.impl;

import br.com.ms.syscon.seguranca.dao.UsuarioDao;
import br.com.ms.syscon.seguranca.dominio.Usuario;
import br.com.ms.syscon.util.dao.impl.GenericDaoImpl;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
public class UsuarioDaoImpl extends GenericDaoImpl<Usuario, Long> implements UsuarioDao {

    @Override
    public List<Usuario> listByName(Usuario usuario){
        Query query = entityManager.createQuery("select su from Usuario su where su.nmDescricao = :nmDescricao");
        query.setParameter("nmDescricao", usuario.getNmDescricao());
        return query.getResultList();
    }

    @Override
    public Usuario findByName(Usuario usuario) {
        Query query = entityManager.createQuery("select su from Usuario su where su.nmDescricao = :nmDescricao");
        query.setParameter("nmDescricao", usuario.getNmDescricao());
        return (Usuario) query.getSingleResult();
    }
}
