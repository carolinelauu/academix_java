package DAOs;


import Entidades.Estado;
import java.util.ArrayList;
import java.util.List;

public class DAOEstado extends DAOGenerico<Estado> {

    private List<Estado> lista = new ArrayList<>();

    public DAOEstado() {
        super(Estado.class);
    }

    public int autoIdEstado() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.sigla_estado) FROM Estado e ").getSingleResult();
        if (a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }

    public List<Estado> listByNome(String nome) {
        return em.createQuery("SELECT e FROM Estado e WHERE e.nome_estado) LIKE :nome").setParameter("nome", "%" + nome + "%").getResultList();
    }

    public List<Estado> listById(int id) {
        return em.createQuery("SELECT e FROM Estado + e WHERE e.sigla_estado= :id").setParameter("id", id).getResultList();
    }

    public List<Estado> listInOrderNome() {
        return em.createQuery("SELECT e FROM Estado e ORDER BY e.nome_estado").getResultList();
    }

    public List<Estado> listInOrderId() {
        return em.createQuery("SELECT e FROM Estado e ORDER BY e.sigla_estado").getResultList();
    }

    public List<String> listInOrderNomeStrings(String qualOrdem) {
        List<Estado> lf;
        if (qualOrdem.equals("id")) {
            lf = listInOrderId();
        } else {
            lf = listInOrderNome();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getSigla_estado()+ "-" + lf.get(i).getNome_estado());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOEstado daoEstado = new DAOEstado();
        List<Estado> listaEstado = daoEstado.list();
        for (Estado estado : listaEstado) {
            System.out.println(estado.getSigla_estado()+ "-" + estado.getNome_estado());
        }
    }
}