/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.dao.impl;

import br.com.ms.syscon.seguranca.dao.PerfilDao;
import br.com.ms.syscon.seguranca.dominio.Perfil;
import br.com.ms.syscon.util.dao.impl.GenericDaoImpl;
import javax.ejb.Stateless;

@Stateless
public class PerfilDaoImpl extends GenericDaoImpl<Perfil,Long> implements PerfilDao {

}
