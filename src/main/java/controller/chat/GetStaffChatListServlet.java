package controller.chat;

import DAO.UserInfoDAO;
import business.Customer;
import DAO.ChatDAO;
import business.Message;
import business.Staff;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/Staff/loadStaffChatList")
public class GetStaffChatListServlet extends HttpServlet {

    private ChatDAO chatDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        chatDAO = new ChatDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Lấy staffID từ session
        HttpSession session = request.getSession();

        session.setAttribute("customerID", "1");

        String customerIDString = (String) session.getAttribute("customerID");
        Long customerID = Long.parseLong(customerIDString);

        try {
            //get cutomer
            UserInfoDAO userInfoDAO = new UserInfoDAO();
            Customer customer = userInfoDAO.getCustomerInfoById(customerID);

            List<Staff> staffs = chatDAO.getStaffChatList(customerID);

            Map<Long, String> latestMessages = new HashMap<>();

            for (Staff staff : staffs) {
                Message latestMessageObj = chatDAO.getLatestMessage(staff.getPersonID(), customerID);
                if (latestMessageObj != null) {
                    latestMessages.put(staff.getPersonID(), latestMessageObj.getMsg());
                } else {
                    latestMessages.put(staff.getPersonID(), "Chưa có tin nhắn");
                }
            }

            request.setAttribute("customer", customer);
            request.setAttribute("staffs", staffs);
            request.setAttribute("latestMessages", latestMessages);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Có lỗi xảy ra khi xử lý yêu cầu.");
        }

        // Chuyển đến JSP
        request.getRequestDispatcher("staffChatList.jsp").forward(request, response);
    }
}

