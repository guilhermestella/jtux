/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.util.dao.impl;

import br.com.ms.syscon.util.dao.GenericDao;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author guilherme.stella
 * @param <E>
 * @param <K>
 */
public class GenericDaoImpl<E, K extends Serializable> implements GenericDao<E, K>{

    @PersistenceContext
    protected EntityManager entityManager;
    
    @Override
    public void add(E entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(E entity) {
        entityManager.merge(entity);
    }

    @Override
    public void remove(E entity) {
        entityManager.remove(entityManager.merge(entity));
    }

    @Override
    public E find(K key) {
        return (E) entityManager.find(getPersistentClass(), key);
    }

    @Override
    public List<E> list() {
        return entityManager.createQuery("from " + getPersistentClass().getName()).getResultList();
    }
    
    @Override
    public Class getPersistentClass(){
        return (Class<E>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
}
