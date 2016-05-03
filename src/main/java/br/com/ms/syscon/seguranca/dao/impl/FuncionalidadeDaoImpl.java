/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.dao.impl;

import br.com.ms.syscon.util.dao.impl.GenericDaoImpl;
import br.com.ms.syscon.seguranca.dao.FuncionalidadeDao;
import br.com.ms.syscon.seguranca.dominio.Funcionalidade;
import javax.ejb.Stateless;

@Stateless
public class FuncionalidadeDaoImpl extends GenericDaoImpl<Funcionalidade, Long> implements FuncionalidadeDao {

}
