/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.util.validation;

/**
 *
 * @author guilherme.stella
 */
public class ComunicacaoException extends RuntimeException{
    
    public ComunicacaoException(String mensagem){
        super(mensagem);
    }
    
    public ComunicacaoException(){
        super();
    }
}
