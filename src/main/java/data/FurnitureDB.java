package data;

import ENumeration.EFurnitureStatus;
import business.Furniture;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class FurnitureDB {

    public static List<Furniture> getAllFurnitures(int limit, int skip) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT f FROM Furniture f WHERE f.id IN (" +
                "SELECT MIN(f2.id) FROM Furniture f2 " +
                "WHERE f2.order IS NULL " +
                "AND f2.furnitureStatus = :status " +
                "GROUP BY f2.category.id)";
        TypedQuery<Furniture> q = em.createQuery(qString, Furniture.class);
        q.setParameter("status", EFurnitureStatus.ON_SALE); // Gắn enum giá trị 'Active'
        q.setFirstResult(skip); // Số lượng record bỏ qua
        q.setMaxResults(limit);  // Số lượng record lấy
        try {
            List<Furniture> listFurniture = q.getResultList();
            return listFurniture;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public static List<Furniture> getFurnitureInCategory (Furniture furniture){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT f FROM  Furniture  f" +
                " where f.category.id = :categoryId" +
                " and f.id <> :furnitureId" +
                " and f.furnitureStatus = :status" +
                " and  f.order Is NULL";
        TypedQuery<Furniture> q = em.createQuery(qString, Furniture.class);
        q.setParameter("categoryId", furniture.getCategory().getId());
        q.setParameter("furnitureId", furniture.getId());
        q.setParameter("status", EFurnitureStatus.ON_SALE);
        try{
            List<Furniture> listFurniture = q.getResultList();
            return listFurniture;
        }
        catch (NoResultException e){
            return null;
        }
        finally {
            em.close();
        }
    }
    public static long countFurniture() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT COUNT(f) FROM Furniture f WHERE f.id IN (" +
                "SELECT MIN(f2.id) FROM Furniture f2 " +
                "WHERE f2.order IS NULL " +
                "AND f2.furnitureStatus = :status " +
                "GROUP BY f2.category.id)";
        Query q = em.createQuery(qString);
        q.setParameter("status", EFurnitureStatus.ON_SALE);
        try {
            return (long)q.getSingleResult();
        }
        catch (NoResultException e) {
            return 0;
        }
        finally {
            em.close();
        }
    }

    public static Furniture getFurnitureByID(int furnitureID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT f FROM Furniture f where f.id = :furnitureID";
        TypedQuery<Furniture> q = em.createQuery(qString, Furniture.class);
        q.setParameter("furnitureID", furnitureID);
        try {
            Furniture furniture = q.getSingleResult();
            return furniture;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public static List<Furniture> getFurnitureQuantity(String categoryID, int quantity) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        // Tạo câu truy vấn lọc sản phẩm theo category và status
        String qString = "SELECT f FROM Furniture f " +
                "WHERE f.category.id = :categoryId " +
                "AND f.furnitureStatus = :status " +
                "AND f.order IS NULL";

        TypedQuery<Furniture> q = em.createQuery(qString, Furniture.class);
        q.setParameter("categoryId", Long.parseLong(categoryID));
        q.setParameter("status", EFurnitureStatus.ON_SALE);

        try {
            q.setMaxResults(quantity);  // Nếu số lượng đủ, giới hạn kết quả lấy theo số lượng yêu cầu
            List<Furniture> listFurniture = q.getResultList();
            System.out.println(listFurniture.size());
            // Kiểm tra số lượng sản phẩm trả về
            if (listFurniture.size() < quantity) {
                return null;  // Nếu số lượng ít hơn yêu cầu, trả về null
            }
            return listFurniture;

        } catch (NoResultException e) {
            return null;  // Trả về null nếu không có kết quả
        } finally {
            em.close();  // Đảm bảo EntityManager được đóng
        }
    }
    public static Furniture getFurnitureDiscount(String categoryID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        // Tạo câu truy vấn lọc sản phẩm theo category và status
        String qString = "SELECT f FROM Furniture f " +
                "WHERE f.category.id = :categoryId";

        TypedQuery<Furniture> q = em.createQuery(qString, Furniture.class);
        q.setParameter("categoryId", Long.parseLong(categoryID));

        try {
            // Lấy danh sách các sản phẩm
            List<Furniture> furnitureList = q.getResultList();

            // Kiểm tra nếu danh sách không rỗng, trả về một sản phẩm ngẫu nhiên
            if (!furnitureList.isEmpty()) {
                // Lấy một phần tử ngẫu nhiên trong danh sách
                return furnitureList.get(0); // Hoặc có thể thay `0` bằng một chỉ số ngẫu nhiên
            } else {
                return null;  // Trả về null nếu không có kết quả
            }
        } catch (NoResultException e) {
            return null;  // Trường hợp không có kết quả
        } finally {
            em.close();  // Đảm bảo EntityManager được đóng
        }
    }


}
