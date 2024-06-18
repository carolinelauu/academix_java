/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
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
@Table(name = "universidade")
@NamedQueries({
    @NamedQuery(name = "Universidade.findAll", query = "SELECT u FROM Universidade u")})
public class Universidade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_universidade")
    private Integer id_universidade;
    @Column(name = "nome_universidade")
    private String nome_universidade;
    @Column(name = "privada")
    private Short privada;
    @Column(name = "sigla_universidade")
    private String sigla_universidade;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "universidade_id_universidade")
    private List<Campus> campusList;

    public Universidade() {
    }

    public Universidade(Integer idUniversidade, String nome_universidade, Short privada, String sigla_universidade) {
        this.id_universidade = idUniversidade;
        this.nome_universidade = nome_universidade;
        this.privada = privada;
        this.sigla_universidade = sigla_universidade;
    }

    public Integer getId_universidade() {
        return id_universidade;
    }

    public void setId_universidade(Integer idUniversidade) {
        this.id_universidade = idUniversidade;
    }

    public String getNome_universidade() {
        return nome_universidade;
    }

    public void setNome_universidade(String nomeUniversidade) {
        this.nome_universidade = nomeUniversidade;
    }

    public Short getPrivada() {
        return privada;
    }

    public void setPrivada(Short privada) {
        this.privada = privada;
    }

    public String getSigla_universidade() {
        return sigla_universidade;
    }

    public void setSigla_universidade(String siglaUniversidade) {
        this.sigla_universidade = siglaUniversidade;
    }

    public List<Campus> getCampusList() {
        return campusList;
    }

    public void setCampusList(List<Campus> campusList) {
        this.campusList = campusList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_universidade != null ? id_universidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Universidade)) {
            return false;
        }
        Universidade other = (Universidade) object;
        if ((this.id_universidade == null && other.id_universidade != null) || (this.id_universidade != null && !this.id_universidade.equals(other.id_universidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id_universidade + ";" + nome_universidade + ";" + (Objects.equals(privada, Short.valueOf("1"))?"sim":"não") + ";" + sigla_universidade;
    }
    
}
