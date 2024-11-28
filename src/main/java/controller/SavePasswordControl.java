package controller;

import DAO.PersonDao;
import business.Person;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SavePasswordControl", value = "/savePassword")
public class SavePasswordControl extends HttpServlet {

    private final PersonDao personDao = new PersonDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the logged-in user from the session
        HttpSession session = request.getSession();
        Person user = (Person) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login"); // Redirect to login if no user session exists
            return;
        }

        // Get form parameters
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Check if the current password is correct
        if (!user.getPassword().equals(currentPassword)) {
            // If the current password is incorrect
            response.sendRedirect("updatePassword?error=true");
            return;
        }

        // Check if the new password and confirmation match
        if (newPassword == null || !newPassword.equals(confirmPassword)) {
            // If the new passwords do not match
            response.sendRedirect("updatePassword?error=true");
            return;
        }

        // Update the user's password in the database
        user.setPassword(newPassword);
        personDao.updatePerson(user);

        // Redirect to profile page with success message
        response.sendRedirect("updatePassword?success=true");
    }
}
