/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.dao.impl;

import br.com.ms.syscon.util.dao.impl.GenericDaoImpl;
import br.com.ms.syscon.seguranca.dao.ModuloDao;
import br.com.ms.syscon.seguranca.dominio.Modulo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;


/**
 *
 * @author guilherme.stella
 */
@Stateless
public class ModuloDaoImpl extends GenericDaoImpl<Modulo, Long> implements ModuloDao {

    @Override
    public List<Modulo> listByNmModulo(Modulo modulo) {
        Query query = entityManager.createQuery("select m from Modulo m where m.nmDescricao = :nmDescricao");
        query.setParameter("nmDescricao", modulo.getNmDescricao());
        return query.getResultList();
    }
}
