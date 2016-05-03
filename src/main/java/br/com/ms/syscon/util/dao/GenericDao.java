/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.util.dao;

import java.util.List;

/**
 *
 * @author guilherme.stella
 * @param <E> Entidade
 * @param <K> Chave
 */
public interface GenericDao<E, K> {
    
    public void add(E entity);
    
    public void update(E entity);
    
    public void remove(E entity);
    
    public E find(K key);
    
    public List<E> list();
    
    public Class getPersistentClass();
}
