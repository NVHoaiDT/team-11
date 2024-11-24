/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ENumeration;

/**
 *
 * @author ASUS
 */
public enum EOrderStatus {
    WAITING_PROCESS,  // Đang chờ xử lý
    CANCELED,        // Đã hủy
    DELIVERING,      // Đang giao hàng
    DELIVERED,       // Đã giao hàng
    ACCEPTED,        // Đã chấp nhận
    REFUNDED,        // Đã hoàn tiền
    FEEDBACKED       // Đã nhận phản hồi
}