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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author guilherme.stella
 */
@Entity
@Table(name="grupo_funcionalidade")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "id_grupo_funcionalidade")),
    @AttributeOverride(name = "nmDescricao", column = @Column(name="nm_grupo_funcionalidade"))
})
public class GrupoFuncionalidade extends TabelasBasicas {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_modulo")
    private Modulo modulo = new Modulo();
    
    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "grupoFuncionalidade")
    private List<Funcionalidade>listFuncionalidade = new ArrayList<>();

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public List<Funcionalidade> getListFuncionalidade() {
        return listFuncionalidade;
    }

    public void setListFuncionalidade(List<Funcionalidade> listFuncionalidade) {
        this.listFuncionalidade = listFuncionalidade;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.modulo);
        hash = 61 * hash + Objects.hashCode(this.listFuncionalidade);
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
        final GrupoFuncionalidade other = (GrupoFuncionalidade) obj;
        if (!Objects.equals(this.modulo, other.modulo)) {
            return false;
        }
        return Objects.equals(this.listFuncionalidade, other.listFuncionalidade);
    }

    

}
