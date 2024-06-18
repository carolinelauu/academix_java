package DAOs;

import Entidades.Universidade;
import java.util.ArrayList;
import java.util.List;

public class DAOUniversidade extends DAOGenerico<Universidade> {

    private List<Universidade> lista = new ArrayList<>();

    public DAOUniversidade() {
        super(Universidade.class);
    }

    public int autoIdUniversidade() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.cpf) FROM Universidade e ").getSingleResult();
        if (a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }

    public List<Universidade> listByNome(String nome) {
        return em.createQuery("SELECT e FROM Universidade e WHERE e.nome_universidade) LIKE :nome").setParameter("nome", "%" + nome + "%").getResultList();
    }

    public List<Universidade> listById(int id) {
        return em.createQuery("SELECT e FROM Universidade + e WHERE e.id_universidade= :id").setParameter("id", id).getResultList();
    }

    public List<Universidade> listInOrderNome() {
        return em.createQuery("SELECT e FROM Universidade e ORDER BY e.nome_universidade").getResultList();
    }

    public List<Universidade> listInOrderId() {
        return em.createQuery("SELECT e FROM Universidade e ORDER BY e.id_universidade").getResultList();
    }

    public List<String> listInOrderNomeStrings(String qualOrdem) {
        List<Universidade> lf;
        if (qualOrdem.equals("id")) {
            lf = listInOrderId();
        } else {
            lf = listInOrderNome();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getId_universidade()+ "-" + lf.get(i).getNome_universidade());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOUniversidade daoUniversidade = new DAOUniversidade();
        List<Universidade> listaUniversidade = daoUniversidade.list();
        for (Universidade universidade : listaUniversidade) {
            System.out.println(universidade.getId_universidade()+ "-" + universidade.getNome_universidade());
        }
    }
}