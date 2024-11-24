package DAO;

import business.Customer;
import business.Message;
import business.Staff;
import data.DBUtil;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class ChatDAO {

    public List<Customer> getCustomerList(Long staffID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        List<Customer> customers = null;
        String query = "SELECT DISTINCT c " +
                "FROM Customer c " +
                "LEFT JOIN Message m ON c.personID = m.incomingMsgID " +
                "WHERE m.outgoingMsgID = :staffID OR m.outgoingMsgID IS NULL";
        try {
            // Lấy tất cả khách hàng, kể cả những khách hàng chưa nhắn tin
            customers = em.createQuery(query, Customer.class)
                    .setParameter("staffID", staffID)
                    .getResultList();

            // Lấy tin nhắn mới nhất cho mỗi khách hàng và sắp xếp theo thời gian gửi
            customers = customers.stream()
                    .sorted((c1, c2) -> {
                        // Lấy tin nhắn mới nhất của khách hàng
                        Message msg1 = getLatestMessage(c1.getPersonID(), staffID);
                        Message msg2 = getLatestMessage(c2.getPersonID(), staffID);

                        // Nếu cả hai khách hàng đều có tin nhắn mới nhất
                        if (msg1 != null && msg2 != null) {
                            return msg2.getSentDate().compareTo(msg1.getSentDate());
                        }

                        if (msg1 == null && msg2 == null) {
                            return 0;
                        } else if (msg1 == null) {
                            return 1; //msg1 null
                        } else {
                            return -1; //msg2 null
                        }
                    })
                    .collect(Collectors.toList());

            System.out.println(customers);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return customers;
    }

    public List<Staff> getStaffChatList(Long customerID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        List<Staff> staffs = null;
        String query = "SELECT DISTINCT s " +
                "FROM Staff s " +
                "LEFT JOIN Message m ON s.personID = m.incomingMsgID " +
                "WHERE m.outgoingMsgID = :customerID OR m.outgoingMsgID IS NULL";
        try {
            // Lấy tất cả nhân viên
            staffs = em.createQuery(query, Staff.class)
                    .setParameter("customerID", customerID)
                    .getResultList();

            // Lấy tin nhắn mới nhất cho mỗi nhân viên và sắp xếp theo thời gian gửi
            staffs = staffs.stream()
                    .sorted((s1, s2) -> {
                        // Lấy tin nhắn mới nhất của nhân viên
                        Message msg1 = getLatestMessage(s1.getPersonID(), customerID);
                        Message msg2 = getLatestMessage(s2.getPersonID(), customerID);

                        // Nếu cả hai nhân viên đều có tin nhắn mới nhất
                        if (msg1 != null && msg2 != null) {
                            return msg2.getSentDate().compareTo(msg1.getSentDate());
                        }

                        if (msg1 == null && msg2 == null) {
                            return 0;
                        } else if (msg1 == null) {
                            return 1; //msg1 null
                        } else {
                            return -1; //msg2 null
                        }
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return staffs;
    }

    public List<Message> getChatHistory(Long outgoingID, Long incomingID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        List<Message> chatHistory = null;
        try {
            String query = "SELECT m FROM Message m " +
                    "WHERE (m.incomingMsgID = :incomingID AND m.outgoingMsgID = :outgoingID) " +
                    "   OR (m.incomingMsgID = :outgoingID AND m.outgoingMsgID = :incomingID) " +
                    "ORDER BY m.sentDate ASC";
            chatHistory = em.createQuery(query, Message.class)
                    .setParameter("incomingID", incomingID)
                    .setParameter("outgoingID", outgoingID)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return chatHistory;
    }

//    public Message getLatestMessage(String incomingMsgID, String outgoingMsgID) {
//        EntityManager em = DBUtil.getEmFactory().createEntityManager();
//        Message latestMessage = null;
//        try {
//            String query = "SELECT m FROM Message m " +
//                    "WHERE m.incomingMsgID = :incomingMsgID " +
//                    "AND m.outgoingMsgID = :outgoingMsgID " +
//                    "ORDER BY m.sentDate DESC";
//            latestMessage = em.createQuery(query, Message.class)
//                    .setParameter("incomingMsgID", incomingMsgID)
//                    .setParameter("outgoingMsgID", outgoingMsgID)
//                    .setMaxResults(1) // Chỉ lấy tin nhắn mới nhất
//                    .getSingleResult();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            em.close();
//        }
//        return latestMessage;
//    }


    public Message getLatestMessage(Long personID1, Long personID2) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        List<Message> messages = null;

        String query = "SELECT m FROM Message m " +
                "WHERE (m.incomingMsgID = :personID1 AND m.outgoingMsgID = :personID2) " +
                "   OR (m.incomingMsgID = :personID2 AND m.outgoingMsgID = :personID1) " +
                "ORDER BY m.sentDate DESC";
        messages = em.createQuery(query, Message.class)
                .setParameter("personID1", personID1)
                .setParameter("personID2", personID2)
                .setMaxResults(1)
                .getResultList();
                //getSingleResult()

        if (messages.isEmpty()) {
            return null;
        } else {
            return messages.get(0);
        }
    }


    public boolean insertMessage(Message message) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(message);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

}

