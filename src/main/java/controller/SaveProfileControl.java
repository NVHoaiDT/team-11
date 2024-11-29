package controller;

import DAO.PersonDao;
import DAO.CustomerDao;
import DAO.customerDAO.ProfileDAO.StaffDao2;
import business.Customer;
import business.Staff;
import business.Owner;
import business.Address;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "SaveProfileControl", value = "/saveProfile")
@MultipartConfig
public class SaveProfileControl extends HttpServlet {

    private final PersonDao personDao = new PersonDao();
    private final CustomerDao customerDao = new CustomerDao();
    private final StaffDao2 staffDao = new StaffDao2();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Kiểm tra session để lấy thông tin người dùng
        String url = "/KhachHang/index.jsp"; // Mặc định redirect về trang index của khách hàng
        Customer customer = null;
        Staff staff = null;
        Owner owner = null;

        if (session.getAttribute("customer") != null) {
            customer = (Customer) session.getAttribute("customer");
        } else if (session.getAttribute("staff") != null) {
            staff = (Staff) session.getAttribute("staff");
        } else if (session.getAttribute("owner") != null) {
            owner = (Owner) session.getAttribute("owner");
        }

        // Kiểm tra nếu không có người dùng trong session
        if (customer == null && staff == null && owner == null) {
            response.sendRedirect("login");
            return;
        }

        try {
            // Lấy các thông tin từ form
            String phone = request.getParameter("phone");
            String birthDateStr = request.getParameter("birthDate");
            String street = request.getParameter("street");
            String city = request.getParameter("city");
            String province = request.getParameter("province");
            String country = request.getParameter("country");

            Address address = new Address(street, city, province, country);

            // Xử lý ảnh đại diện
            Part part = request.getPart("profileImage");
            byte[] profileImage = null;
            if (part != null && part.getSize() > 0) {
                profileImage = toByteArray(part.getInputStream());
            }

            // Cập nhật thông tin người dùng
            if (customer != null) {
                if (phone != null && !phone.isEmpty()) {
                    customer.setPhone(phone);
                }
                customer.setAddress(address);
                if (profileImage != null) {
                    customer.setAvatar(profileImage);
                }
                if (birthDateStr != null && !birthDateStr.isEmpty()) {
                    try {
                        customer.setBirthDate(java.sql.Date.valueOf(birthDateStr));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid birth date format: " + e.getMessage());
                    }
                }
                customerDao.updateCustomer(customer);
                session.setAttribute("customer", customer);
                url = "/KhachHang/index.jsp";  // Redirect đến trang chính của khách hàng

            } else if (staff != null) {
                if (phone != null && !phone.isEmpty()) {
                    staff.setPhone(phone);
                }
                staff.setAddress(address);
                if (profileImage != null) {
                    staff.setAvatar(profileImage);
                }
                if (birthDateStr != null && !birthDateStr.isEmpty()) {
                    try {
                        staff.setBirthDate(java.sql.Date.valueOf(birthDateStr));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid birth date format: " + e.getMessage());
                    }
                }
                staffDao.updateStaff(staff);
                session.setAttribute("staff", staff);
                url = "/Staff/dashboard.jsp";  // Redirect đến trang dashboard của nhân viên

            } else if (owner != null) {
                if (phone != null && !phone.isEmpty()) {
                    owner.setPhone(phone);
                }
                owner.setAddress(address);
                if (profileImage != null) {
                    owner.setAvatar(profileImage);
                }
                if (birthDateStr != null && !birthDateStr.isEmpty()) {
                    try {
                        owner.setBirthDate(java.sql.Date.valueOf(birthDateStr));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid birth date format: " + e.getMessage());
                    }
                }
                // Cập nhật thông tin của Owner nếu cần thiết, ví dụ:
                // ownerDao.updateOwner(owner);
                session.setAttribute("owner", owner);
                url = "/Owner/dashboard.jsp";  // Redirect đến trang dashboard của chủ sở hữu

            }

        } catch (Exception e) {
            System.out.println("Error while saving profile: " + e.getMessage());
            response.sendRedirect("profile?error=Unable to save profile");
            return;
        }

        // Redirect về URL đã xác định sau khi lưu thông tin
        response.sendRedirect(request.getContextPath() + url);
    }

    // Phương thức chuyển InputStream thành byte array
    private byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(data)) != -1) {
            buffer.write(data, 0, bytesRead);
        }
        return buffer.toByteArray();
    }
}
