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
@Table(name="sistema_operacional")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "id_sistema_operacional")),
    @AttributeOverride(name = "nmDescricao", column = @Column(name="nm_sistema_operacional"))
})
public class SistemaOperacional extends TabelasBasicas {
    
    @Column(name="nm_imagem_so")
    private String nmImagemSO;
    
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "sistemaOperacional")
    private List<Servidor>listServidor = new ArrayList<>();

    public String getNmImagemSO() {
        return nmImagemSO;
    }

    public void setNmImagemSO(String nmImagemSO) {
        this.nmImagemSO = nmImagemSO;
    }

    public List<Servidor> getListServidor() {
        return listServidor;
    }

    public void setListServidor(List<Servidor> listServidor) {
        this.listServidor = listServidor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.nmImagemSO);
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
        final SistemaOperacional other = (SistemaOperacional) obj;
        if (!Objects.equals(this.nmImagemSO, other.nmImagemSO)) {
            return false;
        }
        if (!Objects.equals(this.listServidor, other.listServidor)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
