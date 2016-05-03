/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.dominio;

import br.com.ms.syscon.util.dominio.AbstractEntity;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author guilherme.stella
 */
@Entity
@Table(name="variavel_arquivo")
@AttributeOverride(name = "id", column = @Column(name = "id_variavel_arquivo"))
public class VariavelArquivo extends AbstractEntity {
    
    @Column(name="ds_campo")
    private String dsCampo;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_arquivo_configuracao")
    private ArquivoConfiguracao arquivoConfiguracao;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_variavel_global")
    private VariavelGlobal variavelGlobal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_servidor")
    private Servidor servidor;
    
    public String getDsCampo() {
        return dsCampo;
    }

    public void setDsCampo(String dsCampo) {
        this.dsCampo = dsCampo;
    }

    public ArquivoConfiguracao getArquivoConfiguracao() {
        return arquivoConfiguracao;
    }

    public void setArquivoConfiguracao(ArquivoConfiguracao arquivoConfiguracao) {
        this.arquivoConfiguracao = arquivoConfiguracao;
    }

    public VariavelGlobal getVariavelGlobal() {
        return variavelGlobal;
    }

    public void setVariavelGlobal(VariavelGlobal variavelGlobal) {
        this.variavelGlobal = variavelGlobal;
    }

    public Servidor getServidor() {
        return servidor;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.dsCampo);
        hash = 73 * hash + Objects.hashCode(this.arquivoConfiguracao);
        hash = 73 * hash + Objects.hashCode(this.variavelGlobal);
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
        final VariavelArquivo other = (VariavelArquivo) obj;
        if (!Objects.equals(this.dsCampo, other.dsCampo)) {
            return false;
        }
        if (!Objects.equals(this.arquivoConfiguracao, other.arquivoConfiguracao)) {
            return false;
        }
        if (!Objects.equals(this.variavelGlobal, other.variavelGlobal)) {
            return false;
        }
        return true;
    }
    
    
}
