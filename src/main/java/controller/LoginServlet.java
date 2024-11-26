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

@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/login.jsp";
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        String role = request.getParameter("role");
        String message = "";
        HttpSession session = request.getSession();

        if (email == null || email.equals("") || pass == null || pass.equals("")) {
            message = "Vui lòng nhập đủ thông tin";
        } else {
            if(role.equals("customer")) {
                Customer customer = CustomerDB.getCustomerByEmailPass(email, pass);
                if(customer == null || customer.getStatus().equals("InActive")) {
                    message = "Sai tài khoản hoặc mật khẩu";
                } else {
                    session.setAttribute("customer", customer);
                    url = "/index.jsp";
                }
            } else if (role.equals("staff")) {
                Staff staff = StaffDB.getStaffByEmailPass(email, pass);
                if(staff == null) {
                    message = "Sai tài khoản hoặc mật khẩu";
                } else {
                    session.setAttribute("staff", staff);
                    url = "/Staff/dashboard.jsp";
                }
            } else if (role.equals("owner")) {
                Owner owner = OwnerDB.getOwnerByEmailPass(email, pass);
                if(owner == null) {
                    message = "Sai tài khoản hoặc mật khẩu";
                } else {
                    session.setAttribute("owner", owner);
                    url = "/Chu.jsp";
                }
            } else {
                message = "Vui lòng chọn vai trò của bạn";
            }
        }
        session.setAttribute("message", message);
        response.sendRedirect(request.getContextPath() + url);
    }
}