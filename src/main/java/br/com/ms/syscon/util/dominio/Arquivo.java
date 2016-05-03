/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.util.dominio;

import java.io.File;

/**
 *
 * @author guilherme.stella
 */
public class Arquivo extends File {

    String nmLocalArquivoRemoto;

    public String getNmLocalArquivoRemoto() {
        return nmLocalArquivoRemoto;
    }

    public void setNmLocalArquivoRemoto(String nmLocalArquivoRemoto) {
        this.nmLocalArquivoRemoto = nmLocalArquivoRemoto;
    }

    public Arquivo(String pathname) {
        super(pathname);
    }
    
}
