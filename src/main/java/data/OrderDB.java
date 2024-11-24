package data;

import business.Furniture;
import business.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class OrderDB {
    public static boolean insertOrder(Order order) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            // Lưu trữ đơn hàng vào cơ sở dữ liệu
            em.persist(order);
            // Cập nhật order_id cho các đối tượng Furniture hiện có
            for (Furniture furniture : order.getListFurniture()) {
                // Nếu furniture đã tồn tại trong DB, chỉ cập nhật order_id mà không tạo mới
                if (furniture.getId() != null) {
                    furniture.setOrder(order); // Cập nhật lại order_id của furniture
                    em.merge(furniture); // Merge để cập nhật furniture vào DB
                }
            }
            trans.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Loi: " + e);
            if (trans.isActive()) {
                trans.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }
}
