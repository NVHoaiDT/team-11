package controller;

import DAO.PersonDao;
import business.Person;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoadProfileControl", value = "/loadProfile")
public class LoadProfileControl extends HttpServlet {

    private final PersonDao personDao = new PersonDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get logged-in user from the session
        HttpSession session = request.getSession();
        Person user = (Person) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        // Use the session's user ID if no ID is provided in the request
        Long personID = user.getPersonID();

        // Get the Person details by ID
        Person person = personDao.getPersonByIdOrDefault(personID);

        if (person != null) {
            // Set the person object in the request scope for JSP access
            request.setAttribute("person", person);
            RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
            dispatcher.forward(request, response);  // Forward request to profile.jsp
        } else {
            response.sendRedirect("eror.jsp");  // Redirect if person not found
        }
    }
}