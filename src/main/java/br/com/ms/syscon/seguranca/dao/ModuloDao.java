/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.dao;

import br.com.ms.syscon.util.dao.GenericDao;
import br.com.ms.syscon.seguranca.dominio.Modulo;
import java.util.List;

/**
 *
 * @author guilherme.stella
 */
public interface ModuloDao extends GenericDao<Modulo, Long>{
    
    public List<Modulo> listByNmModulo(Modulo modulo);
    
}
