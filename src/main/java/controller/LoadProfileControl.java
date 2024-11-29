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

    private final PersonDao personDao = new PersonDao();
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
        Person person = null;
        String userType = null;
        String email = null;
        boolean showChangePasswordButton = true;

        if (session.getAttribute("customer") != null) {
            Customer customer = (Customer) session.getAttribute("customer");
            person = customerDao.getCustomerById(customer.getPersonID());
            if (person == null) {
                customer.setAvatar("/assets/img/default-customer.jpg".getBytes());
                person = customer;
            }
            userType = "Customer";
            email = person.getEmail() != null ? person.getEmail() : person.getGoogleLogin();
            if (person.getEmail() == null) {
                showChangePasswordButton = false;
            }
        } else if (session.getAttribute("staff") != null) {
            Staff staff = (Staff) session.getAttribute("staff");
            person = staffDao.getStaffById(staff.getPersonID());
            if (person == null) {
                staff.setAvatar("/assets/img/default-staff.jpg".getBytes());
                person = staff;
            }
            userType = "Staff";
            email = person.getEmail(); // Giả định Staff không có Google Login
        } else if (session.getAttribute("owner") != null) {
            Owner owner = (Owner) session.getAttribute("owner");
            person = personDao.getPersonByIdOrDefault(owner.getPersonID());
            userType = "Owner";
            email = person.getEmail(); // Giả định Owner không có Google Login
        }

        if (person == null) {
            session.setAttribute("message", "Vui lòng đăng nhập để truy cập thông tin hồ sơ.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.setAttribute("person", person);
        request.setAttribute("userType", userType);
        request.setAttribute("email", email);
        request.setAttribute("showChangePasswordButton", showChangePasswordButton);

        System.out.println("Forwarding to: " + url);
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
