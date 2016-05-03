/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.negocio.impl;

import br.com.ms.syscon.seguranca.dao.AcaoDao;
import br.com.ms.syscon.seguranca.dao.FuncionalidadeDao;
import br.com.ms.syscon.seguranca.dao.UsuarioDao;
import br.com.ms.syscon.seguranca.dominio.Acao;
import br.com.ms.syscon.seguranca.dominio.Funcionalidade;
import br.com.ms.syscon.seguranca.dominio.Usuario;
import br.com.ms.syscon.seguranca.negocio.UsuarioNegocio;
import br.com.ms.syscon.seguranca.dominio.Perfil;
import br.com.ms.syscon.util.validation.NegocioException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UsuarioNegocioImpl implements UsuarioNegocio {

    @Inject UsuarioDao usuarioDao;
    
    @Inject FuncionalidadeDao funcionalidadeDao;
    
    @Inject AcaoDao acaoDao;

    @Override
    public void cadastrar(Usuario entity) {
        
        if ( entity.getNmDescricao().isEmpty() || entity.getNmSenha().isEmpty() )
            throw new NegocioException("Nome ou Senha Vazios");

        usuarioDao.add(entity);
    }

    @Override
    public void alterar(Usuario entity) {
        
        if ( entity.getNmDescricao().isEmpty() || entity.getNmSenha().isEmpty() )
            throw new NegocioException("Nome ou Senha Vazios");
        
        usuarioDao.update(entity);
    }

    @Override
    public void excluir(Usuario entity) {
        usuarioDao.remove(entity);
    }

    @Override
    public Usuario pesquisar(Long Key) {
        return usuarioDao.find(Key);
    }

    @Override
    public List<Usuario> listar() {
        return usuarioDao.list();
    }
    
    
    
    @Override
    public Usuario pesquisarPorNome(Usuario usuario) {
        return usuarioDao.findByName(usuario);
    }

    /**
     * Recebe um nome de usuário e retorna ele completo se válido
     * Validações:
     *      Usuário e Senha preenchido
     *      Algum usuário encontrado
     *      Somente um usuário encontrado
     *      Senha correta
     *      Usuário Ativo
     * @param usuario nome do usuário
     * @return usuário completo
     */
    @Override
    public Usuario autenticarUsuario(Usuario usuario){
        
        if ( usuario.getNmDescricao().equals("") ){
            throw new NegocioException("Login vazio!");
        }
        
        if ( usuario.getNmSenha().equals("") ){
            throw new NegocioException("Senha Vazia!");
        }
        
        List<Usuario>listUsuario = usuarioDao.listByName(usuario);
        
        if ( listUsuario.isEmpty() ){
            throw new NegocioException("Usuário não encontrado!");
        }
        
        if ( listUsuario.size() > 1 ){
            throw new NegocioException("Encontrado mais de um Usuário!");
        }
        
        Usuario usuarioValidacao = listUsuario.get(0);
        usuarioValidacao = usuarioDao.findByName(usuarioValidacao);

        if ( !usuarioValidacao.getNmSenha().equals(usuario.getNmSenha()) ){
            throw new NegocioException("Senha Incorreta!");
        }

        if ( usuarioValidacao.getAtivo().isId() == false ){
            throw new NegocioException("Usuário Inativo!");
        }
        return usuarioValidacao;
    }

    @Override
    public boolean verificarUsuarioLogado(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void logoutUsuario(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registrarLoginUsuario(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public List<Funcionalidade> obterFuncionalidadeUsuario(List<Acao> listAcao) {
        List<Funcionalidade>listFuncionalidade = new ArrayList<>();
        List<Funcionalidade>listFuncionalidadeInativo = new ArrayList<>();

        
        /* Das listas de ações, retorna cada funcionalidade e suas ações */
        for ( Funcionalidade funcionalidade : funcionalidadeDao.list() ){
            funcionalidade.getListAcao().clear();
            for ( Acao acao : listAcao ){
                if ( funcionalidade.getId().equals(acao.getFuncionalidade().getId())){
                    funcionalidade.getListAcao().add(acao);
                }
            }
            listFuncionalidade.add(funcionalidade);
        }


        
        //Retirar Funcionalidades Inativas
        for ( Funcionalidade funcionalidade : listFuncionalidade ){
            if ( funcionalidade.getAtivo().isId() == false ){
                listFuncionalidadeInativo.add(funcionalidade);
            }
        }
        listFuncionalidade.removeAll(listFuncionalidadeInativo);
        
        if ( listFuncionalidade.isEmpty() ){
            throw new NegocioException("Usuário não possuí nenhuma permissão no Sistema!");
        }

        return listFuncionalidade;

    }

    @Override
    public List<Acao> obterAcaoFuncionalidadeUsuario(Usuario usuario) {
        List<Acao>listAcao = new ArrayList<>();
        List<Acao>listAcaoSemDuplicidade = new ArrayList<>();
        HashSet<Acao>setAcaoSemDuplicidade = new HashSet();

        //A partir de um Usuário é obtido as ações do seu perfil
        for ( Perfil perfil : usuario.getListPerfil() ){
            listAcao = perfil.getListAcao();
        }
        
        //Retirando as Ações Duplicadas
        for ( Acao acao : listAcao ){
            if ( !setAcaoSemDuplicidade.contains(acao) ){
                listAcaoSemDuplicidade.add(acao);
                setAcaoSemDuplicidade.add(acao);
            }
        }
        return listAcaoSemDuplicidade;
    }



}
