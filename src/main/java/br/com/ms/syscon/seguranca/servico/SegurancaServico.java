/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.servico;

import br.com.ms.syscon.seguranca.dominio.Funcionalidade;
import br.com.ms.syscon.seguranca.dominio.Usuario;
import java.util.List;

/**
 *
 * @author guilherme.stella
 */
public interface SegurancaServico {
    
    public Usuario autenticarUsuario(Usuario usuario);
    
    public List<Funcionalidade> obterPermissoesUsuario(Usuario usuario);
    
}
