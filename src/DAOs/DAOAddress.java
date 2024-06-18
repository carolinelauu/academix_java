package DAOs;


import static DAOs.DAOGenerico.em;
import Entidades.Address;
import java.util.ArrayList;
import java.util.List;

public class DAOAddress extends DAOGenerico<Address> {

    private List<Address> lista = new ArrayList<>();

    public DAOAddress() {
        super(Address.class);
    }

    public int autoIdAddress() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.cep) FROM Address e ").getSingleResult();
        if (a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }

    public List<Address> listByNome(String nome) {
        return em.createQuery("SELECT e FROM Address e WHERE e.logradouro) LIKE :nome").setParameter("nome", "%" + nome + "%").getResultList();
    }

    public List<Address> listById(int id) {
        return em.createQuery("SELECT e FROM Address + e WHERE e.cep= :id").setParameter("id", id).getResultList();
    }

    public List<Address> listInOrderNome() {
        return em.createQuery("SELECT e FROM Address e ORDER BY e.logradouro").getResultList();
    }

    public List<Address> listInOrderId() {
        return em.createQuery("SELECT e FROM Address e ORDER BY e.cep").getResultList();
    }

    public List<String> listInOrderNomeStrings(String qualOrdem) {
        List<Address> lf;
        if (qualOrdem.equals("id")) {
            lf = listInOrderId();
        } else {
            lf = listInOrderNome();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getCep()+ "-" + lf.get(i).getLogradouro());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOAddress daoAddress = new DAOAddress();
        List<Address> listaAddress = daoAddress.list();
        for (Address address : listaAddress) {
            System.out.println(address.getCep()+ "-" + address.getLogradouro());
        }
    }
}