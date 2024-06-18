package DAOs;

import Entidades.Campus;
import java.util.ArrayList;
import java.util.List;

public class DAOCampus extends DAOGenerico<Campus> {

    private List<Campus> lista = new ArrayList<>();

    public DAOCampus() {
        super(Campus.class);
    }

    public int autoIdCampus() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.cpf) FROM Campus e ").getSingleResult();
        if (a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }

    public List<Campus> listByNome(String nome) {
        return em.createQuery("SELECT e FROM Campus e WHERE e.nome_campus) LIKE :nome").setParameter("nome", "%" + nome + "%").getResultList();
    }

    public List<Campus> listById(int id) {
        return em.createQuery("SELECT e FROM Campus + e WHERE e.address_cep= :id").setParameter("id", id).getResultList();
    }

    public List<Campus> listInOrderNome() {
        return em.createQuery("SELECT e FROM Campus e ORDER BY e.nome_campus").getResultList();
    }

    public List<Campus> listInOrderId() {
        return em.createQuery("SELECT e FROM Campus e ORDER BY e.address_cep").getResultList();
    }

    public List<String> listInOrderNomeStrings(String qualOrdem) {
        List<Campus> lf;
        if (qualOrdem.equals("id")) {
            lf = listInOrderId();
        } else {
            lf = listInOrderNome();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getAddress_cep()+ "-" + lf.get(i).getNome_campus());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOCampus daoCampus = new DAOCampus();
        List<Campus> listaCampus = daoCampus.list();
        for (Campus campus : listaCampus) {
            System.out.println(campus.getAddress_cep()+ "-" + campus.getNome_campus());
        }
    }
}