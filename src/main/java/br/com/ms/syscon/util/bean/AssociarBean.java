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
 */
public interface AssociarBean<E> extends Serializable {
    
    public void init();
    
    public void btnAssociar();
    
    public void gerarAssociacao(List<E>targets);
    
    public void btnAtualizarLista();
    
}
