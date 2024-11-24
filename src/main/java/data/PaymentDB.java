package data;

import business.Coupon;
import business.Payment;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class PaymentDB {
    public static boolean insert(Payment payment) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();

            Coupon coupon = payment.getCoupon();
            // Tìm Coupon từ DB để đảm bảo nó được quản lý bởi JPA
            if(coupon != null) {
                Coupon managedCoupon = em.find(Coupon.class, coupon.getCouponID());
                managedCoupon.setCurrentUsage(managedCoupon.getCurrentUsage() + 1);
            }

            // Lưu Payment
            em.persist(payment);

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
