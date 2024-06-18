/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author LOUISE
 */
@Entity
@Table(name = "address")
@NamedQueries({
    @NamedQuery(name = "Address.findAll", query = "SELECT a FROM Address a")})
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cep")
    private Integer cep;
    @Column(name = "logradouro")
    private String logradouro;
    @Column(name = "number")
    private Integer number;
    @JoinColumn(name = "cidade_id_cidade", referencedColumnName = "id_cidade")
    @ManyToOne(optional = false)
    private Cidade cidade_id_cidade;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "address")
    private Campus campus;

    public Address() {
    }

    public Address(Integer cep, String logradouro, Integer number, Cidade cidade_id_cidade) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.number = number;
        this.cidade_id_cidade = cidade_id_cidade;
        
        
    }

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Cidade getCidade_id_cidade() {
        return cidade_id_cidade;
    }

    public void setCidade_id_cidade(Cidade cidadeIdCidade) {
        this.cidade_id_cidade = cidadeIdCidade;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cep != null ? cep.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        return !((this.cep == null && other.cep != null) || (this.cep != null && !this.cep.equals(other.cep)));
    }

    @Override
    public String toString() {
        return cep + ";" + logradouro + ";" + number + ";" + cidade_id_cidade.toString().split(";")[1];
    }
    
}
