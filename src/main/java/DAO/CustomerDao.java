package DAO;

import data.DBUtil;

import business.Customer;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.EntityTransaction;

public class CustomerDao {
    public Customer getCustomerById(Long id) { // Chuyển đổi tham số thành Long
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT c FROM Customer c WHERE c.personID = :id";
        TypedQuery<Customer> q = em.createQuery(qString, Customer.class);
        q.setParameter("id", id);

        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    public void updateCustomer(Customer customer) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.merge(customer); // Use merge to update the existing customer entity
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            throw new RuntimeException("Failed to update customer profile: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}
