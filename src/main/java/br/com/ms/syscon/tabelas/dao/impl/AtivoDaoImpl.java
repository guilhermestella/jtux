/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.tabelas.dao.impl;

import br.com.ms.syscon.tabelas.dao.AtivoDao;
import br.com.ms.syscon.tabelas.domain.Ativo;
import br.com.ms.syscon.util.dao.impl.GenericDaoImpl;
import javax.ejb.Stateless;

@Stateless
public class AtivoDaoImpl extends GenericDaoImpl<Ativo, Long> implements AtivoDao {
 
}
