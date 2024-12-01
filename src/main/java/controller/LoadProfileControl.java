package controller;

import DAO.PersonDao;
import DAO.CustomerDao;
import DAO.customerDAO.ProfileDAO.StaffDao2;
import business.Person;
import business.Customer;
import business.Staff;
import business.Owner;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoadProfileControl", value = "/loadProfile")
public class LoadProfileControl extends HttpServlet {

    private final CustomerDao customerDao = new CustomerDao();
    private final StaffDao2 staffDao = new StaffDao2();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String url = "/profile.jsp";
        Customer cus = null ;
        Staff sta = null ;
        String userType = null;
        String email = null;
        boolean showChangePasswordButton = true;

        if (session.getAttribute("customer") != null) {
            Customer customer = (Customer) session.getAttribute("customer");
            cus = customerDao.getCustomerById(customer.getPersonID());
            if (cus == null) {
                customer.setAvatar("/assets/img/default-customer.jpg".getBytes());
                cus = customer;
            }
            userType = "Customer";
            email = cus.getEmail() != null ? cus.getEmail() : cus.getGoogleLogin();
            if (cus.getEmail() == null) {
                showChangePasswordButton = false;
            }
            request.setAttribute("person", cus);

        } else if (session.getAttribute("staff") != null) {
            Staff staff = (Staff) session.getAttribute("staff");
            sta = staffDao.getStaffById(staff.getPersonID());
            if (sta == null) {
                staff.setAvatar("/assets/img/default-staff.jpg".getBytes());
                sta = staff;
            }
            userType = "Staff";
            email = sta.getEmail(); // Giả định Staff không có Google Login
            request.setAttribute("person", sta);
        }

        if (cus != null) {
            request.setAttribute("birthDate", cus.getBirthDate() != null ? cus.getBirthDate().toString() : "");
            request.setAttribute("person", cus);
        } else if (sta != null) {
            request.setAttribute("birthDate", sta.getBirthDate() != null ? sta.getBirthDate().toString() : "");
            request.setAttribute("person", sta);
        }


        if (cus == null && sta == null) {
            session.setAttribute("message", "Vui lòng đăng nhập để truy cập thông tin hồ sơ.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.setAttribute("userType", userType);
        request.setAttribute("email", email);
        request.setAttribute("showChangePasswordButton", showChangePasswordButton);

        System.out.println("Forwarding to: " + url);
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
