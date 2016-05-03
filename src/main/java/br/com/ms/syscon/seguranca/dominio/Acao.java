/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.dominio;

import br.com.ms.syscon.util.dominio.AbstractEntity;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author guilherme.stella
 */
@Entity
@Table(name="acao")
@AttributeOverride(name = "id", column = @Column(name = "id_acao"))
public class Acao extends AbstractEntity {

    @Column(name="nm_link")
    private String nmLink;
    
    @ManyToOne
    @JoinColumn(name="id_funcionalidade")
    private Funcionalidade funcionalidade;
    
    @ManyToMany(mappedBy = "listAcao")
    private List<Perfil> listPerfil;
    
    @ManyToOne
    @JoinColumn(name = "id_tipo_acao")
    private TipoAcao tipoAcao;

    public String getNmLink() {
        return nmLink;
    }

    public void setNmLink(String nmLink) {
        this.nmLink = nmLink;
    }

    public Funcionalidade getFuncionalidade() {
        return funcionalidade;
    }

    public void setFuncionalidade(Funcionalidade funcionalidade) {
        this.funcionalidade = funcionalidade;
    }

    public List<Perfil> getListPerfil() {
        return listPerfil;
    }

    public void setListPerfil(List<Perfil> listPerfil) {
        this.listPerfil = listPerfil;
    }

    public TipoAcao getTipoAcao() {
        return tipoAcao;
    }

    public void setTipoAcao(TipoAcao tipoAcao) {
        this.tipoAcao = tipoAcao;
    }

}
