package DAOs;


import static DAOs.DAOGenerico.em;
import Entidades.Curso;
import java.util.ArrayList;
import java.util.List;

public class DAOCurso extends DAOGenerico<Curso> {

    private List<Curso> lista = new ArrayList<>();

    public DAOCurso() {
        super(Curso.class);
    }

    public int autoIdCurso() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.id_curso) FROM  Curso e ").getSingleResult();
        if (a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }

    public List<Curso> listByNome(String nome) {
        return em.createQuery("SELECT e FROM Curso e WHERE e.nome_curso) LIKE :nome").setParameter("nome", "%" + nome + "%").getResultList();
    }

    public List<Curso> listById(int id) {
        return em.createQuery("SELECT e FROM Curso + e WHERE e.id_curso= :id").setParameter("id", id).getResultList();
    }

    public List<Curso> listInOrderNome() {
        return em.createQuery("SELECT e FROM Campus e ORDER BY e.nome_curso").getResultList();
    }

    public List<Curso> listInOrderId() {
        return em.createQuery("SELECT e FROM Curso e ORDER BY e.id_curso").getResultList();
    }

    public List<String> listInOrderNomeStrings(String qualOrdem) {
        List<Curso> lf;
        if (qualOrdem.equals("id")) {
            lf = listInOrderId();
        } else {
            lf = listInOrderNome();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getIdCurso()+ "-" + lf.get(i).getNomeCurso());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOCurso daoCurso = new DAOCurso();
        List<Curso> listaCurso = daoCurso.list();
        for (Curso curso : listaCurso) {
            System.out.println(curso.getIdCurso()+ "-" + curso.getNomeCurso());
        }
    }
}