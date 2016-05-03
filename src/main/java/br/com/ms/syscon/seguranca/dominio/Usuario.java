/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.dominio;

import br.com.ms.syscon.gerenciamento.dominio.Servidor;
import br.com.ms.syscon.util.dominio.TabelasBasicas;
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
@Table(name="usuario")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "id_usuario")),
    @AttributeOverride(name = "nmDescricao", column = @Column(name="nm_usuario"))
})
public class Usuario extends TabelasBasicas {
    
    @Column(name="nm_senha")
    private String nmSenha;
    
    @Column(name="nm_email")
    private String nmEmail;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "perfil_usuario",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_perfil"))
    private List<Perfil>listPerfil = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_servidor",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_servidor"))
    private List<Servidor>listServidor = new ArrayList<>();
    

    public String getNmSenha() {
        return nmSenha;
    }

    public void setNmSenha(String nmSenha) {
        this.nmSenha = nmSenha;
    }

    public String getNmEmail() {
        return nmEmail;
    }

    public void setNmEmail(String nmEmail) {
        this.nmEmail = nmEmail;
    }

    public List<Perfil> getListPerfil() {
        return listPerfil;
    }

    public void setListPerfil(List<Perfil> listPerfil) {
        this.listPerfil = listPerfil;
    }

    public List<Servidor> getListServidor() {
        return listServidor;
    }

    public void setListServidor(List<Servidor> listServidor) {
        this.listServidor = listServidor;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.nmSenha);
        hash = 59 * hash + Objects.hashCode(this.nmEmail);
        hash = 59 * hash + Objects.hashCode(this.listPerfil);
        hash = 59 * hash + Objects.hashCode(this.listServidor);
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
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.nmSenha, other.nmSenha)) {
            return false;
        }
        if (!Objects.equals(this.nmEmail, other.nmEmail)) {
            return false;
        }
        if (!Objects.equals(this.listPerfil, other.listPerfil)) {
            return false;
        }
        if (!Objects.equals(this.listServidor, other.listServidor)) {
            return false;
        }
        return true;
    }

}
