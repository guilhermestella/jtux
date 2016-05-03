/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.dominio;

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
@Table(name="variavel_global")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "id_variavel_global")),
    @AttributeOverride(name = "nmDescricao", column = @Column(name="nm_variavel_global"))
})
public class VariavelGlobal extends TabelasBasicas {
    
    @Column(name="ds_variavel_global")
    private String dsVariavelGlobal;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "variavelGlobal")
    private List<VariavelArquivo>listVariavelArquivo = new ArrayList<>();

    public String getDsVariavelGlobal() {
        return dsVariavelGlobal;
    }

    public void setDsVariavelGlobal(String dsVariavelGlobal) {
        this.dsVariavelGlobal = dsVariavelGlobal;
    }

    public List<VariavelArquivo> getListVariavelArquivo() {
        return listVariavelArquivo;
    }

    public void setListVariavelArquivo(List<VariavelArquivo> listVariavelArquivo) {
        this.listVariavelArquivo = listVariavelArquivo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.listVariavelArquivo);
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
        final VariavelGlobal other = (VariavelGlobal) obj;
        if (!Objects.equals(this.listVariavelArquivo, other.listVariavelArquivo)) {
            return false;
        }
        return true;
    }
    
    
    
}
