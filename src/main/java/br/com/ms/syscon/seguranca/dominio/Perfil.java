/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.dominio;

import br.com.ms.syscon.util.dominio.TabelasBasicas;
import br.com.ms.syscon.seguranca.dominio.Acao;
import br.com.ms.syscon.seguranca.dominio.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author guilherme.stella
 */
@Entity
@Table(name="perfil")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "id_perfil")),
    @AttributeOverride(name = "nmDescricao", column = @Column(name="nm_perfil"))
})
public class Perfil extends TabelasBasicas {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="acao_perfil",
            joinColumns = @JoinColumn(name="id_perfil"),
            inverseJoinColumns = @JoinColumn(name="id_acao"))
    private List<Acao>listAcao = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "listPerfil")
    private List<Usuario>listUsuario = new ArrayList<>();

    public List<Acao> getListAcao() {
        return listAcao;
    }

    public void setListAcao(List<Acao> listAcao) {
        this.listAcao = listAcao;
    }

    public List<Usuario> getListUsuario() {
        return listUsuario;
    }

    public void setListUsuario(List<Usuario> listUsuario) {
        this.listUsuario = listUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.listAcao);
        hash = 97 * hash + Objects.hashCode(this.listUsuario);
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
        final Perfil other = (Perfil) obj;
        if (!Objects.equals(this.listAcao, other.listAcao)) {
            return false;
        }
        if (!Objects.equals(this.listUsuario, other.listUsuario)) {
            return false;
        }
        return true;
    }
    
    
    
}
