package googlelogin;


import business.Customer;
import business.Owner;
import business.Staff;
import data.CustomerDB;
import data.OwnerDB;
import data.StaffDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "loginGG", value = "/loginGG")
public class LoginByGoogle extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String code = request.getParameter("code");
        GoogleLogin googleLogin = new GoogleLogin();
        String accessToken = googleLogin.getToken(code);
        Customer newCustomer = googleLogin.getUserInfo(accessToken);
        String url = "";
        HttpSession session = request.getSession();

        Customer customer = CustomerDB.getCustomerByGoogleLogin(newCustomer.getGoogleLogin());
        if (customer != null) {
            session.setAttribute("customer", customer);

            // Kiểm tra hồ sơ của khách hàng
            if (!isProfileCompleteCus(customer)) {
                url = "/KhachHang/saveProfile.jsp"; // Chuyển đến trang hoàn thiện hồ sơ
            } else {
                url = "/KhachHang/index.jsp";
            }
        } else {
            // Thêm khách hàng mới
            newCustomer.setStatus("Active");
            CustomerDB.addCustomer(newCustomer);
            session.setAttribute("customer", newCustomer);

            // Kiểm tra hồ sơ của khách hàng mới
            if (!isProfileCompleteCus(newCustomer)) {
                url = "/KhachHang/saveProfile.jsp"; // Chuyển đến trang hoàn thiện hồ sơ
            } else {
                url = "/KhachHang/index.jsp";
            }
        }

        response.sendRedirect(request.getContextPath() + url);
    }
    private boolean isProfileCompleteCus(Customer customer) {
        return customer.getPhone() != null && !customer.getPhone().isEmpty() &&
                customer.getAddress() != null && customer.getAddress().isComplete();
    }

}
