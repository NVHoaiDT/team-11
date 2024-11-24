package DAO.customerDAO.impl;



import DAO.customerDAO.IOrderDAO;
import DTO.customerDTO.requestDTO.OrderRequestDTO;
import business.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class OrderDAOImpl implements IOrderDAO {
    private EntityManagerFactory emf;

    public OrderDAOImpl() {
        this.emf = Persistence.createEntityManagerFactory("employeePU"); // Tạo EntityManagerFactory
    }
    @Override
    public List<Order> getOrder(OrderRequestDTO orderRequestDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            // Bắt đầu xây dựng câu truy vấn JPQL
            StringBuilder jpql = new StringBuilder("SELECT o FROM Order o WHERE o.customer.personID = :customerId");

            if (orderRequestDTO.getId() != null) {
                jpql.append(" AND o.id = :orderId");
            }
            if (orderRequestDTO.getOrderDate() != null) {
                jpql.append(" AND o.orderDate = :orderDate");
            }
            // Tạo truy vấn
            TypedQuery<Order> query = em.createQuery(jpql.toString(), Order.class);
            query.setParameter("customerId", orderRequestDTO.getCustomerId());

            if (orderRequestDTO.getId()!= null) {
                query.setParameter("orderId", orderRequestDTO.getId());
            }
            if (orderRequestDTO.getOrderDate() != null) {
                query.setParameter("orderDate", orderRequestDTO.getOrderDate());
            }

            return query.getResultList();
        } finally {
            em.close();
        }
    }

}
