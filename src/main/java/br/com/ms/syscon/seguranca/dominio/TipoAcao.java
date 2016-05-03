/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.dominio;

import br.com.ms.syscon.util.dominio.TabelasBasicas;
import br.com.ms.syscon.seguranca.dominio.Acao;
import br.com.ms.syscon.util.dominio.AbstractEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

/**
 *
 * @author guilherme.stella
 */
@Entity
@Table(name="tipo_acao")

@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "id_tipo_acao")),
    @AttributeOverride(name = "nmDescricao", column = @Column(name="nm_tipo_acao"))
})

public class TipoAcao extends TabelasBasicas {
    
    @Type(type = "boolean")
    @Column(name="fl_principal")
    private boolean flPrincipal;
    
    @Type(type = "boolean")
    @Column(name="fl_possui_sub_acao")
    private boolean flPossuiSubAcao;
    
    @Column(name="nm_bean")
    private String nmBean;
    
    @Column(name="nm_icone")
    private String nmIcone;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "tipoAcao")
    private List<Acao> listAcao = new ArrayList<>();

    public boolean isFlPrincipal() {
        return flPrincipal;
    }

    public void setFlPrincipal(boolean flPrincipal) {
        this.flPrincipal = flPrincipal;
    }

    public boolean isFlPossuiSubAcao() {
        return flPossuiSubAcao;
    }

    public void setFlPossuiSubAcao(boolean flPossuiSubAcao) {
        this.flPossuiSubAcao = flPossuiSubAcao;
    }

    public String getNmBean() {
        return nmBean;
    }

    public void setNmBean(String nmBean) {
        this.nmBean = nmBean;
    }

    public String getNmIcone() {
        return nmIcone;
    }

    public void setNmIcone(String nmIcone) {
        this.nmIcone = nmIcone;
    }

    public List<Acao> getListAcao() {
        return listAcao;
    }

    public void setListAcao(List<Acao> listAcao) {
        this.listAcao = listAcao;
    }
    
}
