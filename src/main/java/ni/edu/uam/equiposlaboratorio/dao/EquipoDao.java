
package ni.edu.uam.equiposlaboratorio.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import ni.edu.uam.equiposlaboratorio.database.JPAUtil;
import ni.edu.uam.equiposlaboratorio.model.Equipo;
import ni.edu.uam.equiposlaboratorio.model.TipoEquipo;

import java.util.List;

public class EquipoDao {

    public void guardar(Equipo equipo) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(equipo);
            tx.commit();

        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;

        } finally {
            em.close();
        }
    }

    public List<Equipo> listar() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT e FROM Equipo e JOIN FETCH e.tipoEquipo ORDER BY e.idEquipo",
                    Equipo.class
            ).getResultList();

        } finally {
            em.close();
        }
    }

    public void modificar(Equipo equipo) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(equipo);
            tx.commit();

        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;

        } finally {
            em.close();
        }
    }

    public void eliminar(Long idEquipo) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Equipo equipo = em.find(Equipo.class, idEquipo);

            if (equipo != null) {
                em.remove(equipo);
            }

            tx.commit();

        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;

        } finally {
            em.close();
        }
    }
    public List<Equipo> buscar(String nombre, TipoEquipo tipoEquipo) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT e FROM Equipo e JOIN FETCH e.tipoEquipo " +
                    "WHERE LOWER(e.nombre) LIKE LOWER(:nombre) ";

            if (tipoEquipo != null) {
                jpql += "AND e.tipoEquipo = :tipo ";
            }

            jpql += "ORDER BY e.idEquipo";
            TypedQuery<Equipo> consulta = em.createQuery(jpql, Equipo.class);
            consulta.setParameter("nombre", "%" + nombre + "%");
            if (tipoEquipo != null) {
                consulta.setParameter("tipo", tipoEquipo);
            }
            return consulta.getResultList();
        } finally {
            em.close();
        }
    }
}
