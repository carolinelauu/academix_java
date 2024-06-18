package DAOs;

import static DAOs.DAOGenerico.em;
import Entidades.CursoHasCampus;
import Entidades.CursoHasCampusPK;

import java.util.ArrayList;
import java.util.List;

public class DAOCampus_has_Curso extends DAOGenerico<CursoHasCampus> {

    public DAOCampus_has_Curso() {
        super(CursoHasCampus.class);
    }

    //Método obter essencial para pesquisar utilizando uma PK composta
    ////////////////////////////////////////////////////////////////////////
    public CursoHasCampus obter2(CursoHasCampusPK cursoHasCampusPk) {
        return em.find(CursoHasCampus.class, cursoHasCampusPk);
    }
    ////////////////////////////////////////////////////////////////////////
    
    

    public List<CursoHasCampus> listInOrderNome() {
        return em.createQuery("SELECT e FROM CursoHasCampus e ORDER BY e.cursoHasCampus").getResultList();
    }

    public List<String> listInOrderNomeStrings() {
        List<CursoHasCampus> lf = listInOrderNome();
        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getCursoHasCampusPK().toString());
        }
        return ls;
    }

    public List<CursoHasCampus> listarComSite() {
        //listar as pizzas e os ingredientes

        List<CursoHasCampus> lista = list();
        return lista;
    }
}
