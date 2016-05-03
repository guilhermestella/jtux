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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author guilherme.stella
 */
@Entity
@Table(name="servico")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "id_servico")),
    @AttributeOverride(name = "nmDescricao", column = @Column(name="nm_servico"))
})
public class Servico extends TabelasBasicas {
    
    @Column(name="nm_metodo_instalar")
    private String nmMetodoInstalar;
    
    @Column(name="nm_metodo_start")
    private String nmMetodoStart;
    
    @Column(name="nm_metodo_stop")
    private String nmMetodoStop;
    
    @Column(name="nm_metodo_reload")
    private String nmMetodoReload;
    
    @Column(name="nm_metodo_restart")
    private String nmMetodoRestart;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "servico")
    private List<ArquivoConfiguracao>listArquivoConfiguracao = new ArrayList<>();
    
    
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "listServico")
    private List<Servidor>listServidor = new ArrayList<>();

    public String getNmMetodoInstalar() {
        return nmMetodoInstalar;
    }

    public void setNmMetodoInstalar(String nmMetodoInstalar) {
        this.nmMetodoInstalar = nmMetodoInstalar;
    }

    public String getNmMetodoStart() {
        return nmMetodoStart;
    }

    public void setNmMetodoStart(String nmMetodoStart) {
        this.nmMetodoStart = nmMetodoStart;
    }

    public String getNmMetodoStop() {
        return nmMetodoStop;
    }

    public void setNmMetodoStop(String nmMetodoStop) {
        this.nmMetodoStop = nmMetodoStop;
    }

    public String getNmMetodoReload() {
        return nmMetodoReload;
    }

    public void setNmMetodoReload(String nmMetodoReload) {
        this.nmMetodoReload = nmMetodoReload;
    }

    public String getNmMetodoRestart() {
        return nmMetodoRestart;
    }

    public void setNmMetodoRestart(String nmMetodoRestart) {
        this.nmMetodoRestart = nmMetodoRestart;
    }

    public List<Servidor> getListServidor() {
        return listServidor;
    }

    public void setListServidor(List<Servidor> listServidor) {
        this.listServidor = listServidor;
    }

    public List<ArquivoConfiguracao> getListArquivoConfiguracao() {
        return listArquivoConfiguracao;
    }

    public void setListArquivoConfiguracao(List<ArquivoConfiguracao> listArquivoConfiguracao) {
        this.listArquivoConfiguracao = listArquivoConfiguracao;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.nmMetodoStart);
        hash = 13 * hash + Objects.hashCode(this.nmMetodoStop);
        hash = 13 * hash + Objects.hashCode(this.nmMetodoReload);
        hash = 13 * hash + Objects.hashCode(this.nmMetodoRestart);
        hash = 13 * hash + Objects.hashCode(this.listArquivoConfiguracao);
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
        final Servico other = (Servico) obj;
        if (!Objects.equals(this.nmMetodoStart, other.nmMetodoStart)) {
            return false;
        }
        if (!Objects.equals(this.nmMetodoStop, other.nmMetodoStop)) {
            return false;
        }
        if (!Objects.equals(this.nmMetodoReload, other.nmMetodoReload)) {
            return false;
        }
        if (!Objects.equals(this.nmMetodoRestart, other.nmMetodoRestart)) {
            return false;
        }
        if (!Objects.equals(this.listArquivoConfiguracao, other.listArquivoConfiguracao)) {
            return false;
        }
        return true;
    }

}
