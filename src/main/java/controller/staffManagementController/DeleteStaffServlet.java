package controller.staffManagementController;

import DAO.StaffDAO;
import business.Staff;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/deleteStaff")
public class DeleteStaffServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String empId = request.getParameter("emp-id");
        Staff staff = StaffDAO.getStaffById(empId);
        StaffDAO.delete(staff);
        HttpSession session = request.getSession();
        List<Staff> listStaff = (List<Staff>) session.getAttribute("listStaff");
        //listEmployee.remove(employee);
        for (Staff s : listStaff) {
            if (s.getPersonID() == staff.getPersonID()) {
                listStaff.remove(s);
                break;
            }
        }

        session.setAttribute("listStaff", listStaff);
        /*
        try{
            String toMail = employee.getEmail();
            String fromMail = "hdphat123@gmail.com";
            String subject = "Thông báo xóa tài khoản!";
            String content = "Xin chào " + employee.getEmpName() +",\n\n"
                    + "Tài khoản nhân viên của bạn đã bị xóa khỏi hệ thống.\n"
                    + "Cảm ơn bạn đã làm việc tại hệ thống trong thời gian qua." + "\n\n"
                    + "Mọi thắc mắc vui lòng liên hệ Đặng Bá Hiền (0xxx-xxx-xxx)!";
            MailUtil.sendMail(fromMail, toMail, subject, content);
        }
        catch (MessagingException e){
            System.out.println(e.getMessage());
        }
        */
        response.sendRedirect("listStaff");
    }
}
