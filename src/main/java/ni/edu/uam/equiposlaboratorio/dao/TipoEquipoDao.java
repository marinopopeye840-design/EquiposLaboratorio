
package ni.edu.uam.equiposlaboratorio.dao;

import jakarta.persistence.EntityManager;
import ni.edu.uam.equiposlaboratorio.database.JPAUtil;
import ni.edu.uam.equiposlaboratorio.model.TipoEquipo;

import java.util.List;

public class TipoEquipoDao {

    public List<TipoEquipo> listar() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("SELECT t FROM TipoEquipo t ORDER BY t.nombre", TipoEquipo.class).getResultList();

        } finally {
            em.close();
        }
    }
}
