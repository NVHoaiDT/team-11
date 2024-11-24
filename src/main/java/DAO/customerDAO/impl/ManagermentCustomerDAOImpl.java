package DAO.customerDAO.impl;



import DAO.customerDAO.IManagermentCustomerDAO;
import DTO.customerDTO.requestDTO.CustomerRequestDTO;
import business.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.lang.reflect.Type;
import java.util.List;

public class ManagermentCustomerDAOImpl implements IManagermentCustomerDAO {

    private EntityManagerFactory emf;

    public ManagermentCustomerDAOImpl() {
        this.emf = Persistence.createEntityManagerFactory("employeePU"); // Tạo EntityManagerFactory
    }

    @Override
    public List<Customer> getAllCustomer(CustomerRequestDTO reqDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            StringBuilder queryBuilder = new StringBuilder("SELECT c FROM Customer c WHERE 1=1");
            // Thêm điều kiện nếu email được cung cấp
            if (reqDTO.getEmail() != null && !reqDTO.getEmail().isEmpty()) {
                queryBuilder.append(" AND c.email LIKE :email");
            }
            // Thêm điều kiện nếu phone được cung cấp
            if (reqDTO.getPhone() != null && !reqDTO.getPhone().isEmpty()) {
                queryBuilder.append(" AND c.phone LIKE :phone");
            }
            // Thêm điều kiện nếu name được cung cấp
            if (reqDTO.getName() != null && !reqDTO.getName().isEmpty()) {
                queryBuilder.append(" AND c.name LIKE :name");
            }
            TypedQuery<Customer> query = em.createQuery(queryBuilder.toString(), Customer.class);

            if (reqDTO.getEmail() != null && !reqDTO.getEmail().isEmpty()) {
                query.setParameter("email", "%" + reqDTO.getEmail() + "%");
            }
            if (reqDTO.getPhone() != null && !reqDTO.getPhone().isEmpty()) {
                query.setParameter("phone", "%" + reqDTO.getPhone() + "%");
            }
            if (reqDTO.getName() != null && !reqDTO.getName().isEmpty()) {
                query.setParameter("name", "%" + reqDTO.getName() + "%");
            }

            return query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Có lỗi xảy ra khi lấy danh sách khách hàng: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Customer findById(Long customerId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Customer.class, customerId);
        } finally {
            em.close();
        }
    }

    @Override
    public void updateCustomerStatus(List<Long> customerIds, String status) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            // Truy vấn JPQL(Java Persistence Query Language) để cập nhật trạng thái hàng loạt
            String jpql = "UPDATE Customer c SET c.status = :status WHERE c.personID IN :ids";
            em.createQuery(jpql)
                    .setParameter("status", status)
                    .setParameter("ids", customerIds)
                    .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Có lỗi xảy ra khi cập nhật trạng thái hàng loạt: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}
