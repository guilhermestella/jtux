/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.dominio;

import br.com.ms.syscon.util.dominio.TabelasBasicas;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author guilherme.stella
 */
@Entity
@Table(name="modulo")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "id_modulo")),
    @AttributeOverride(name = "nmDescricao", column = @Column(name="nm_modulo"))
})
public class Modulo extends TabelasBasicas {

    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "modulo")
    private List<GrupoFuncionalidade>listGrupoFuncionalidade = new ArrayList<>();

    public List<GrupoFuncionalidade> getListGrupoFuncionalidade() {
        return listGrupoFuncionalidade;
    }

    public void setListGrupoFuncionalidade(List<GrupoFuncionalidade> listGrupoFuncionalidade) {
        this.listGrupoFuncionalidade = listGrupoFuncionalidade;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.listGrupoFuncionalidade);
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
        final Modulo other = (Modulo) obj;
        if (!Objects.equals(this.listGrupoFuncionalidade, other.listGrupoFuncionalidade)) {
            return false;
        }
        return true;
    }



}
