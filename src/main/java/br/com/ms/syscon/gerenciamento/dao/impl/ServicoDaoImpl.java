/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.dao.impl;

import br.com.ms.syscon.gerenciamento.dao.ServicoDao;
import br.com.ms.syscon.gerenciamento.dominio.Servico;
import br.com.ms.syscon.util.dao.impl.GenericDaoImpl;
import javax.ejb.Stateless;

@Stateless
public class ServicoDaoImpl extends GenericDaoImpl<Servico, Long> implements ServicoDao {

}
