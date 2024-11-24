/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import ENumeration.EOrderStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "Orders") // Renaming the table in the database
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<Furniture> listFurniture;
    
    @OneToOne
    @JoinColumn(name = "CUSTOMERID")
    private Customer customer;
    
    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @Enumerated(EnumType.STRING)
    private EOrderStatus status;

    public Order() {
    }

    public Order(List<Furniture> listFurniture, Customer customer, Date orderDate, EOrderStatus status) {
        this.listFurniture = listFurniture;
        this.customer = customer;
        this.orderDate = orderDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Furniture> getListFurniture() {
        return listFurniture;
    }

    public void setListFurniture(List<Furniture> listFurniture) {
        this.listFurniture = listFurniture;
        for (Furniture furniture : listFurniture) {
            furniture.setOrder(this);  // Đồng bộ quan hệ hai chiều
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public EOrderStatus getStatus() {
        return status;
    }

    public void setStatus(EOrderStatus status) {
        this.status = status;
    }

    public double getTotalAmount() {
        double totalAmount = 0;
        Set<Long> categoryIds = new HashSet<>(); // Set để lưu trữ categoryID duy nhất
        Long categoryPrice = 0L; //Biến để lưu giá tiền của từng category
        for (Furniture item : listFurniture) {
            // Kiểm tra nếu categoryID chưa có trong Set
            if (!categoryIds.contains(item.getCategory().getId())) {
                // Nếu chưa có, thêm vào Set và tính tổng cho category này
                categoryIds.add(item.getCategory().getId());
                // Lấy giá tiền của các sản phẩm cùng category
                categoryPrice = item.getFurniturePrice();

                // Đếm số lượng sản phẩm cùng category
                long count = 0;

                for (Furniture furniture : listFurniture) {
                    if (furniture.getCategory().getId().equals(item.getCategory().getId())) {
                        count++; // Tăng số lượng sản phẩm trong category
                    }
                }
                totalAmount += categoryPrice * count; // Cộng giá trị của sản phẩm đại diện nhân với số lượng
            }
        }
        return totalAmount;
    }


}
