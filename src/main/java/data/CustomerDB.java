package data;

import business.Customer;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class CustomerDB {
    public static Customer accountExists(String email, String pass){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        Customer customer = null;
        String query = "SELECT c FROM Customer c " + "WHERE c.email = :email AND c.password = :pass ";
        TypedQuery<Customer> q = em.createQuery(query, Customer.class);
        q.setParameter("email", email);
        q.setParameter("pass", pass);
        try{
            customer = q.getSingleResult();
            return customer;
        }
        catch(NoResultException e){
            return null;
        }
        finally{
            em.close();
        }
    }
    public static Customer getCustomer(String customerID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT c FROM Customer c where c.personID = :customerID";
        TypedQuery<Customer> q = em.createQuery(qString, Customer.class);
        q.setParameter("customerID", customerID);
        try {
            Customer customer = q.getSingleResult();
            return customer;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public static Customer getCustomerByEmailPass(String email, String password) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT c FROM Customer c where c.email = :email and c.password = :password";
        TypedQuery<Customer> q = em.createQuery(qString, Customer.class);
        q.setParameter("email", email);
        q.setParameter("password", password);
        try {
            Customer customer = q.getSingleResult();
            return customer;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public static Customer getCustomerByGoogleLogin(String googleLogin) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT c FROM Customer c where c.googleLogin = :googleLogin";
        TypedQuery<Customer> q = em.createQuery(qString, Customer.class);
        q.setParameter("googleLogin", googleLogin);
        try {
            Customer customer = q.getSingleResult();
            return customer;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public static void addCustomer(Customer customer) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(customer);
            System.out.println("them thanh cong");
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
