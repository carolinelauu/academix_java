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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author LOUISE
 */
@Entity
@Table(name = "campus")
@NamedQueries({
    @NamedQuery(name = "Campus.findAll", query = "SELECT c FROM Campus c")})
public class Campus implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "campus")
    private List<CursoHasCampus> cursoHasCampusList;

    @JoinTable(name = "curso_has_campus", joinColumns = {
        @JoinColumn(name = "campus_address_cep", referencedColumnName = "address_cep")}, inverseJoinColumns = {
        @JoinColumn(name = "curso_id_curso", referencedColumnName = "id_curso")})
    @ManyToMany
    private List<Curso> cursoList;

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "nome_campus")
    private String nome_campus;
    @Column(name = "telefone")
    private String telefone;
    @Id
    @Basic(optional = false)
    @Column(name = "address_cep")
    private Integer address_cep;
    @JoinColumn(name = "address_cep", referencedColumnName = "cep", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Address address;
    @JoinColumn(name = "universidade_id_universidade", referencedColumnName = "id_universidade")
    @ManyToOne(optional = false)
    private Universidade universidade_id_universidade;

    public Campus() {
    }

    public Campus(Integer addressCep, String nome_campus, String telefone, Universidade address) {
        this.address_cep = addressCep;
        this.nome_campus = nome_campus;
        this.telefone = telefone;
        this.universidade_id_universidade = address;
        
    }

    public Campus(Integer addressCep, String nomeCampus) {
        this.address_cep = addressCep;
        this.nome_campus = nomeCampus;
    }

    public String getNome_campus() {
        return nome_campus;
    }

    public void setNome_campus(String nomeCampus) {
        this.nome_campus = nomeCampus;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Integer getAddress_cep() {
        return address_cep;
    }

    public void setAddress_cep(Integer addressCep) {
        this.address_cep = addressCep;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Universidade getUniversidade_id_universidade() {
        return universidade_id_universidade;
    }

    public void setUniversidade_id_universidade(Universidade universidadeIdUniversidade) {
        this.universidade_id_universidade = universidadeIdUniversidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (address_cep != null ? address_cep.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Campus)) {
            return false;
        }
        Campus other = (Campus) object;
        if ((this.address_cep == null && other.address_cep != null) || (this.address_cep != null && !this.address_cep.equals(other.address_cep))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return address_cep + ";" +nome_campus + ";" + telefone + ";" + universidade_id_universidade.toString().split(";")[1];
    }

    public List<Curso> getCursoList() {
        return cursoList;
    }

    public void setCursoList(List<Curso> cursoList) {
        this.cursoList = cursoList;
    }

    public List<CursoHasCampus> getCursoHasCampusList() {
        return cursoHasCampusList;
    }

    public void setCursoHasCampusList(List<CursoHasCampus> cursoHasCampusList) {
        this.cursoHasCampusList = cursoHasCampusList;
    }
    
}
