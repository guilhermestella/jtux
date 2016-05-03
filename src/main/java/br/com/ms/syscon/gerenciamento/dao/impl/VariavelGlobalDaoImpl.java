/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.dao.impl;

import br.com.ms.syscon.gerenciamento.dao.VariavelGlobalDao;
import br.com.ms.syscon.gerenciamento.dominio.VariavelGlobal;
import br.com.ms.syscon.util.dao.impl.GenericDaoImpl;
import javax.ejb.Stateless;

@Stateless
public class VariavelGlobalDaoImpl extends GenericDaoImpl<VariavelGlobal, Long> implements VariavelGlobalDao {

}
