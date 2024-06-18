/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Caroline
 */
@Entity
@Table(name = "curso_has_campus")
@NamedQueries({
    @NamedQuery(name = "CursoHasCampus.findAll", query = "SELECT c FROM CursoHasCampus c"),
    @NamedQuery(name = "CursoHasCampus.findByCursoIdCurso", query = "SELECT c FROM CursoHasCampus c WHERE c.cursoHasCampusPK.cursoIdCurso = :cursoIdCurso"),
    @NamedQuery(name = "CursoHasCampus.findByCampusAddressCep", query = "SELECT c FROM CursoHasCampus c WHERE c.cursoHasCampusPK.campusAddressCep = :campusAddressCep"),
    @NamedQuery(name = "CursoHasCampus.findBySite", query = "SELECT c FROM CursoHasCampus c WHERE c.site = :site")})
public class CursoHasCampus implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CursoHasCampusPK cursoHasCampusPK;
    @Column(name = "site")
    private String site;
    @JoinColumn(name = "campus_address_cep", referencedColumnName = "address_cep", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Campus campus;
    @JoinColumn(name = "curso_id_curso", referencedColumnName = "id_curso", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Curso curso;

    public CursoHasCampus() {
    }

    public CursoHasCampus(CursoHasCampusPK cursoHasCampusPK) {
        this.cursoHasCampusPK = cursoHasCampusPK;
    }

    public CursoHasCampus(int cursoIdCurso, int campusAddressCep, String site) {
        this.cursoHasCampusPK = new CursoHasCampusPK(cursoIdCurso, campusAddressCep);
        this.site = site;
    }

    public CursoHasCampusPK getCursoHasCampusPK() {
        return cursoHasCampusPK;
    }

    public void setCursoHasCampusPK(CursoHasCampusPK cursoHasCampusPK) {
        this.cursoHasCampusPK = cursoHasCampusPK;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cursoHasCampusPK != null ? cursoHasCampusPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CursoHasCampus)) {
            return false;
        }
        CursoHasCampus other = (CursoHasCampus) object;
        if ((this.cursoHasCampusPK == null && other.cursoHasCampusPK != null) || (this.cursoHasCampusPK != null && !this.cursoHasCampusPK.equals(other.cursoHasCampusPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.CursoHasCampus[ cursoHasCampusPK=" + cursoHasCampusPK + " ]";
    }
    
}
