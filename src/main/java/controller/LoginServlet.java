package controller;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.IOException;

import business.Customer;
import business.Staff;
import business.Owner;

import data.CustomerDB;
import data.StaffDB;
import data.OwnerDB;
import utils.MaHoa;

@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/KhachHang/login.jsp";
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        String role = request.getParameter("role");
        String message = "";
        HttpSession session = request.getSession();

        if (email == null || email.equals("") || pass == null || pass.equals("")) {
            message = "Vui lòng nhập đủ thông tin";
        } else {
            if(role.equals("customer")) {
                String passW = MaHoa.toSHA1(pass);
                Customer customer = CustomerDB.getCustomerByEmailPass(email, passW);
                if(customer == null || customer.getStatus().equals("InActive")) {
                    message = "Sai tài khoản hoặc mật khẩu";
                } else {
                    session.setAttribute("customer", customer);
                    if (!isProfileCompleteCus(customer)) {
                        url = "/KhachHang/saveProfile.jsp";
                    } else {
                        // Nếu hồ sơ đầy đủ, chuyển đến trang index.jsp
                        url = "/KhachHang/index.jsp";
                    }

                }
            } else if (role.equals("staff")) {
                Staff staff = StaffDB.getStaffByEmailPass(email, pass);
                if(staff == null) {
                    message = "Sai tài khoản hoặc mật khẩu";
                } else {
                    session.setAttribute("staff", staff);
                    // Kiểm tra tính đầy đủ thông tin hồ sơ của nhân viên
                    if (!isProfileCompleteSta(staff)) {
                        url = "/KhachHang/saveProfile.jsp";
                    } else {
                        url = "/Staff/dashboard.jsp";
                    }
                }
            } else if (role.equals("owner")) {
                Owner owner = OwnerDB.getOwnerByEmailPass(email, pass);
                if(owner == null) {
                    message = "Sai tài khoản hoặc mật khẩu";
                } else {
                    session.setAttribute("owner", owner);
                    url = "/listStaff";
                }
            } else {
                message = "Vui lòng chọn vai trò của bạn";
            }
        }
        session.setAttribute("message", message);
        response.sendRedirect(request.getContextPath() + url);
    }
    /**
     */
    private boolean isProfileCompleteCus(Customer customer) {
        return customer.getPhone() != null && !customer.getPhone().isEmpty() &&
                customer.getAddress() != null && customer.getAddress().isComplete();
    }

    /**
     * Kiểm tra xem hồ sơ của Staff có đầy đủ thông tin chưa.
     */
    private boolean isProfileCompleteSta(Staff staff) {
        return staff.getPhone() != null && !staff.getPhone().isEmpty() &&
                staff.getAddress() != null && staff.getAddress().isComplete();
    }
}
