/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.util.servico;

import java.util.List;

/**
 *
 * @author guilherme.stella
 * @param <E>
 * @param <K>
 */
public interface CrudServico<E,K> {
    
    public void cadastrar(E entity);
    
    public void alterar(E entity);
    
    public void excluir(E entity);
    
    public E pesquisar(K Key);
    
    public List<E> listar();
    
    
}
