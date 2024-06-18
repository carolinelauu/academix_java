package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author LOUISE
 */
@Entity
@Table(name = "estado")
@NamedQueries({
    @NamedQuery(name = "Estado.findAll", query = "SELECT e FROM Estado e")})
public class Estado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "sigla_estado")
    private String sigla_estado;
    @Column(name = "nome_estado")
    private String nome_estado;

    public Estado() {
    }

    public Estado(String siglaEstado, String nome_estado) {
        this.sigla_estado = siglaEstado;
        this.nome_estado = nome_estado;
    }

    public String getSigla_estado() {
        return sigla_estado;
    }

    public void setSigla_estado(String siglaEstado) {
        this.sigla_estado = siglaEstado;
    }

    public String getNome_estado() {
        return nome_estado;
    }

    public void setNome_estado(String nomeEstado) {
        this.nome_estado = nomeEstado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sigla_estado != null ? sigla_estado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estado)) {
            return false;
        }
        Estado other = (Estado) object;
        if ((this.sigla_estado == null && other.sigla_estado != null) || (this.sigla_estado != null && !this.sigla_estado.equals(other.sigla_estado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return sigla_estado + ";" + nome_estado;
    }

    
}
