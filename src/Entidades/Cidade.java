/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author LOUISE
 */
@Entity
@Table(name = "cidade")
@NamedQueries({
    @NamedQuery(name = "Cidade.findAll", query = "SELECT c FROM Cidade c")})
public class Cidade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_cidade")
    private Integer id_cidade;
    @Column(name = "nome_cidade")
    private String nome_cidade;
    @JoinColumn(name = "estado_sigla_estado", referencedColumnName = "sigla_estado")
    @ManyToOne(optional = false)
    private Estado estado_sigla_estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cidade_id_cidade")
    private List<Address> addressList;

    public Cidade() {
    }

    public Cidade(Integer idCidade, String nome_cidade, Estado estado_sigla_estado) {
        this.id_cidade = idCidade;
        this.nome_cidade = nome_cidade;
        this.estado_sigla_estado = estado_sigla_estado;
    }

    public Integer getId_cidade() {
        return id_cidade;
    }

    public void setId_cidade(Integer idCidade) {
        this.id_cidade = idCidade;
    }

    public String getNome_cidade() {
        return nome_cidade;
    }

    public void setNome_cidade(String nomeCidade) {
        this.nome_cidade = nomeCidade;
    }

    public Estado getEstado_sigla_estado() {
        return estado_sigla_estado;
    }

    public void setEstado_sigla_estado(Estado estadoSiglaEstado) {
        this.estado_sigla_estado = estadoSiglaEstado;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_cidade != null ? id_cidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cidade)) {
            return false;
        }
        Cidade other = (Cidade) object;
        if ((this.id_cidade == null && other.id_cidade != null) || (this.id_cidade != null && !this.id_cidade.equals(other.id_cidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id_cidade + ";" + nome_cidade + ";" + estado_sigla_estado.toString().split(";")[0];
    }
    
}
