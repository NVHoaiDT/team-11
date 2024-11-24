package controller.staff;

import business.Staff;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@WebServlet("/index")
public class FakeStaffSession extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Lưu Staff vào session
        session.setAttribute("staffID", "3");

        // Chuyển hướng tới index.jsp
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
