/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.tabelas.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

/**
 *
 * @author guilherme.stella
 */
@Entity
@Table(name="ativo")
public class Ativo implements Serializable {
    
    @Id
    @Type(type = "boolean")
    @Column(name="id_ativo")
    private boolean id;
    
    @Column(name="nm_ativo")
    private String nmAtivo;

    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    public String getNmAtivo() {
        return nmAtivo;
    }

    public void setNmAtivo(String nmAtivo) {
        this.nmAtivo = nmAtivo;
    }

}
