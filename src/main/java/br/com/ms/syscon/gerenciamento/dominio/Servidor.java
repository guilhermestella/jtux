/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.dominio;

import br.com.ms.syscon.seguranca.dominio.Usuario;
import br.com.ms.syscon.util.dominio.TabelasBasicas;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author guilherme.stella
 */
@Entity
@Table(name="servidor")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "id_servidor")),
    @AttributeOverride(name = "nmDescricao", column = @Column(name="nm_servidor"))
})
public class Servidor extends TabelasBasicas {
    
    @Column(name="nm_endereco_ip")
    private String nmEnderecoIp;
    
    @Column(name="nr_porta")
    private int nrPorta;
    
    @Column(name="nm_usuario_ssh")
    private String nmUsuarioSsh;
    
    @Column(name="nm_senha_ssh")
    private String nmSenhaSsh;
    
    @Column(name="nm_repositorio_arquivos")
    private String nmRepositorioArquivos;
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_sistema_operacional")
    private SistemaOperacional sistemaOperacional;
    
    
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "listServidor")
    private List<Usuario>listUsuario = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "servidor_servico",
            joinColumns = @JoinColumn(name = "id_servidor"),
            inverseJoinColumns = @JoinColumn(name = "id_servico"))
    private List<Servico>listServico = new ArrayList<>();
    

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "servidor")
    private List<VariavelArquivo>listVariavelArquivo = new ArrayList<>();
    
    public String getNmEnderecoIp() {
        return nmEnderecoIp;
    }

    public void setNmEnderecoIp(String nmEnderecoIp) {
        this.nmEnderecoIp = nmEnderecoIp;
    }

    public int getNrPorta() {
        return nrPorta;
    }

    public void setNrPorta(int nrPorta) {
        this.nrPorta = nrPorta;
    }

    public String getNmUsuarioSsh() {
        return nmUsuarioSsh;
    }

    public void setNmUsuarioSsh(String nmUsuarioSsh) {
        this.nmUsuarioSsh = nmUsuarioSsh;
    }

    public String getNmSenhaSsh() {
        return nmSenhaSsh;
    }

    public void setNmSenhaSsh(String nmSenhaSsh) {
        this.nmSenhaSsh = nmSenhaSsh;
    }

    public SistemaOperacional getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(SistemaOperacional sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public List<Usuario> getListUsuario() {
        return listUsuario;
    }

    public void setListUsuario(List<Usuario> listUsuario) {
        this.listUsuario = listUsuario;
    }

    public List<Servico> getListServico() {
        return listServico;
    }

    public void setListServico(List<Servico> listServico) {
        this.listServico = listServico;
    }

    public String getNmRepositorioArquivos() {
        return nmRepositorioArquivos;
    }

    public void setNmRepositorioArquivos(String nmRepositorioArquivos) {
        this.nmRepositorioArquivos = nmRepositorioArquivos;
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
        hash = 53 * hash + Objects.hashCode(this.nmEnderecoIp);
        hash = 53 * hash + this.nrPorta;
        hash = 53 * hash + Objects.hashCode(this.nmUsuarioSsh);
        hash = 53 * hash + Objects.hashCode(this.nmSenhaSsh);
        hash = 53 * hash + Objects.hashCode(this.nmRepositorioArquivos);
        hash = 53 * hash + Objects.hashCode(this.sistemaOperacional);
        hash = 53 * hash + Objects.hashCode(this.listUsuario);
        hash = 53 * hash + Objects.hashCode(this.listServico);
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
        final Servidor other = (Servidor) obj;
        if (!Objects.equals(this.nmEnderecoIp, other.nmEnderecoIp)) {
            return false;
        }
        if (this.nrPorta != other.nrPorta) {
            return false;
        }
        if (!Objects.equals(this.nmUsuarioSsh, other.nmUsuarioSsh)) {
            return false;
        }
        if (!Objects.equals(this.nmSenhaSsh, other.nmSenhaSsh)) {
            return false;
        }
        if (!Objects.equals(this.nmRepositorioArquivos, other.nmRepositorioArquivos)) {
            return false;
        }
        if (!Objects.equals(this.sistemaOperacional, other.sistemaOperacional)) {
            return false;
        }
        if (!Objects.equals(this.listUsuario, other.listUsuario)) {
            return false;
        }
        return Objects.equals(this.listServico, other.listServico);
    }




}
