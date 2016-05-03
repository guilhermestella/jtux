/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.dominio;


import br.com.ms.syscon.tabelas.domain.Ativo;
import br.com.ms.syscon.util.dominio.AbstractEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.AttributeOverride;
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
@Table(name="funcionalidade")
@AttributeOverride(name = "id", column = @Column(name = "id_funcionalidade"))
public class Funcionalidade extends AbstractEntity {

    @Column(name="nm_funcionalidade")
    private String nmFuncionalidade;

    @Column(name="nm_icone")
    private String nmIcone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_grupo_funcionalidade")
    private GrupoFuncionalidade grupoFuncionalidade;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "funcionalidade")
    private List<Acao>listAcao = new ArrayList<>();
    
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="id_ativo")
    private Ativo ativo;
    
    public String getNmFuncionalidade() {
        return nmFuncionalidade;
    }

    public void setNmFuncionalidade(String nmFuncionalidade) {
        this.nmFuncionalidade = nmFuncionalidade;
    }

    public String getNmIcone() {
        return nmIcone;
    }

    public void setNmIcone(String nmIcone) {
        this.nmIcone = nmIcone;
    }

    public GrupoFuncionalidade getGrupoFuncionalidade() {
        return grupoFuncionalidade;
    }

    public void setGrupoFuncionalidade(GrupoFuncionalidade grupoFuncionalidade) {
        this.grupoFuncionalidade = grupoFuncionalidade;
    }

    public List<Acao> getListAcao() {
        return listAcao;
    }

    public void setListAcao(List<Acao> listAcao) {
        this.listAcao = listAcao;
    }

    public Ativo getAtivo() {
        return ativo;
    }

    public void setAtivo(Ativo ativo) {
        this.ativo = ativo;
    }

}
