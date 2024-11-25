package controller.order;

import DAO.OrderDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Staff/updateOrderStatus")
public class UpdateOrderStatusServlet extends HttpServlet {

    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy các tham số từ request
            Long orderId = Long.parseLong(request.getParameter("orderId"));
            String newStatus = request.getParameter("newStatus");

            // Cập nhật trạng thái đơn hàng
            orderDAO.updateOrderStatus(orderId, newStatus);

            // Phản hồi lại client
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Order status updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update order status.");
        }
    }
}
