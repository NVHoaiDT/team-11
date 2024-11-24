package controller.staffManagementController;


import DAO.StaffDAO;
import data.AccountManagement;
import data.ImageUtil;
import business.Address;
import business.Staff;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/addStaff")
@MultipartConfig
public class AddStaffServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("emp-name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        Date birthDate = java.sql.Date.valueOf(request.getParameter("birth-date"));
        Date workDate = java.sql.Date.valueOf(request.getParameter("work-date"));
        Double salary = Double.parseDouble(request.getParameter("salary"));

        String addressCountry = request.getParameter("address-country");
        String addressCity = request.getParameter("address-city");
        String addressStreet = request.getParameter("address-street");
        String addressProvince = request.getParameter("address-province");

        Address address = new Address(addressStreet, addressCity, addressProvince, addressCountry);
        String password = AccountManagement.generatePassword(10);
        byte[] avatarBytes = ImageUtil.renderImage(request.getPart("avatar"));

        String message = "";
        boolean isSuccess = false;
        if (StaffDAO.isExisted(name, phone)){
            message = "Nhân viên này đã tồn tại!";
        }else{
            try{
                Staff staff = new Staff(name, birthDate, address, email, password, phone, avatarBytes, salary, workDate);
                StaffDAO.insert(staff);
                HttpSession session = request.getSession();
                List<Staff> listStaff = (List<Staff>) session.getAttribute("listStaff");
                listStaff.add(staff);
                session.setAttribute("listStaff", listStaff);
                message = "Thêm nhân viên thành công!";
                isSuccess = true;
            } catch (Exception e) {
                e.printStackTrace();
                message = "Thêm nhân viên thất bại!";
            }
        }

        /*
        try{
            String fromMail = "hdphat123@gmail.com";
            String subject = "Tạo tài khoản thành công!";
            String content = "Xin chào " + empName +",\n\n"
                    + "Tài khoản nhân viên của bạn đã được tạo thành công.\n"
                    + "Mật khẩu đăng nhập: " + password + "\n\n"
                    + "Không chia sẻ mật khẩu này với bất kỳ ai!";
            MailUtil.sendMail(fromMail, email, subject, content);
        }
        catch (MessagingException e){
            System.out.println(e.getMessage());
        }
        */
        request.setAttribute("message", message);
        request.setAttribute("isSuccess", isSuccess);
        request.getRequestDispatcher("addStaff.jsp").forward(request, response);
    }
}
