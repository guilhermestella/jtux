/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.negocio;

import br.com.ms.syscon.seguranca.dominio.Acao;
import br.com.ms.syscon.seguranca.dominio.Funcionalidade;
import br.com.ms.syscon.seguranca.dominio.Usuario;
import br.com.ms.syscon.util.negocio.GenericNegocio;
import java.util.List;

/**
 *
 * @author guilherme.stella
 */
public interface UsuarioNegocio extends GenericNegocio<Usuario, Long>{
    
    public Usuario pesquisarPorNome(Usuario usuario);

    public Usuario autenticarUsuario(Usuario usuario);
    
    public boolean verificarUsuarioLogado(Usuario usuario);
    
    public void logoutUsuario(Usuario usuario);
    
    public void registrarLoginUsuario(Usuario usuario);

    public List<Funcionalidade> obterFuncionalidadeUsuario(List<Acao> listAcao);
    
    public List<Acao> obterAcaoFuncionalidadeUsuario(Usuario usuario);
    
}
