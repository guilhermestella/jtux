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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author guilherme.stella
 */
@Entity
@Table(name="arquivo_configuracao")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "id_arquivo_configuracao")),
    @AttributeOverride(name = "nmDescricao", column = @Column(name="nm_arquivo_configuracao"))
})
public class ArquivoConfiguracao extends TabelasBasicas {
    
    @Column(name="nm_local_arquivo")
    private String nmLocalArquivo;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_servico")
    private Servico servico;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "arquivoConfiguracao")
    private List<VariavelArquivo>listVariavelArquivo = new ArrayList<>();

    public String getNmLocalArquivo() {
        return nmLocalArquivo;
    }

    public void setNmLocalArquivo(String nmLocalArquivo) {
        this.nmLocalArquivo = nmLocalArquivo;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public List<VariavelArquivo> getListVariavelArquivo() {
        return listVariavelArquivo;
    }

    public void setListVariavelArquivo(List<VariavelArquivo> listVariavelArquivo) {
        this.listVariavelArquivo = listVariavelArquivo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.servico);
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
        final ArquivoConfiguracao other = (ArquivoConfiguracao) obj;
        if (!Objects.equals(this.servico, other.servico)) {
            return false;
        }
        return true;
    }
    
    
    
}
