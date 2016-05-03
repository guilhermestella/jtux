/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.util.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author guilherme.stella
 * @param <E>
 * @param <K>
 */

public interface CrudBean<E, K extends Serializable> extends Serializable {
    
    public void init();
    
    public void atualizaListaCadastro();
    
    public void btnGravar();
    
    public void btnAlterar(E entity);
    
    public void btnExcluir(E entity);
    
    public E btnPesquisar(K key);
    
    public List<E> listar();
    
}
