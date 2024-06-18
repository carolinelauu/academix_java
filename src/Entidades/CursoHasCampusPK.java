/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Caroline
 */
@Embeddable
public class CursoHasCampusPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "curso_id_curso")
    private int cursoIdCurso;
    @Basic(optional = false)
    @Column(name = "campus_address_cep")
    private int campusAddressCep;

    public CursoHasCampusPK() {
    }

    public CursoHasCampusPK(int cursoIdCurso, int campusAddressCep) {
        this.cursoIdCurso = cursoIdCurso;
        this.campusAddressCep = campusAddressCep;
    }

    public int getCursoIdCurso() {
        return cursoIdCurso;
    }

    public void setCursoIdCurso(int cursoIdCurso) {
        this.cursoIdCurso = cursoIdCurso;
    }

    public int getCampusAddressCep() {
        return campusAddressCep;
    }

    public void setCampusAddressCep(int campusAddressCep) {
        this.campusAddressCep = campusAddressCep;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) cursoIdCurso;
        hash += (int) campusAddressCep;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CursoHasCampusPK)) {
            return false;
        }
        CursoHasCampusPK other = (CursoHasCampusPK) object;
        if (this.cursoIdCurso != other.cursoIdCurso) {
            return false;
        }
        if (this.campusAddressCep != other.campusAddressCep) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return cursoIdCurso + ";" + campusAddressCep + ";";
    }
    
}
