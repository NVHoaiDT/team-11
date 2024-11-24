package tables;

import javax.persistence.*;

@Entity
@Table(name = "coupon_category")
public class CouponCategory {
    @Id
    private String couponID;
    private String categoryID;

    public String getCategory() {
        return categoryID;
    }
    public String getCoupon() {
        return couponID;
    }
}
