/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.util.dominio;

import br.com.ms.syscon.tabelas.domain.Ativo;
import java.util.Objects;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author guilherme.stella
 */
@MappedSuperclass
public abstract class TabelasBasicas extends AbstractEntity {
    
    private String nmDescricao;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="id_ativo")
    private Ativo ativo = new Ativo();

    public String getNmDescricao() {
        return nmDescricao;
    }

    public void setNmDescricao(String nmDescricao) {
        this.nmDescricao = nmDescricao;
    }

    public Ativo getAtivo() {
        return ativo;
    }

    public void setAtivo(Ativo ativo) {
        this.ativo = ativo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.nmDescricao);
        hash = 23 * hash + Objects.hashCode(this.ativo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TabelasBasicas other = (TabelasBasicas) obj;
        if (!Objects.equals(this.nmDescricao, other.nmDescricao)) {
            return false;
        }
        if (!Objects.equals(this.ativo, other.ativo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TabelasBasicas{" + "nmDescricao=" + nmDescricao + ", ativo=" + ativo + '}';
    }

}
