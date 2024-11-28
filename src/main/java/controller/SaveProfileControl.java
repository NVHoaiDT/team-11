package controller;

import DAO.PersonDao;
import business.Person;
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the logged-in user from the session
        HttpSession session = request.getSession();
        Person user = (Person) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login"); // Redirect if no user session
            return;
        }

        // Get the profile data from the form
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String birthDateStr = request.getParameter("birthDate");

        // Get individual address components
        String street = request.getParameter("street");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zipCode = request.getParameter("zipCode");

        // Create the Address object
        Address address = new Address(street, city, state, zipCode);

        // Get the uploaded profile image if any
        Part part = request.getPart("profileImage");
        byte[] profileImage = null;
        if (part != null && part.getSize() > 0) {
            profileImage = toByteArray(part.getInputStream());
        }

        // Update the Person object with new data
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);  // Set Address object
        // If a profile image was uploaded, set it
        if (profileImage != null) {
            user.setAvatar(profileImage);
        }

        // Parse birthDate and update if valid
        try {
            if (birthDateStr != null && !birthDateStr.isEmpty()) {
                user.setBirthDate(java.sql.Date.valueOf(birthDateStr));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid birth date format: " + e.getMessage());
        }

        // Update the user's profile in the database
        personDao.updatePerson(user);

        // Refresh session with updated user
        session.setAttribute("user", personDao.getPersonById(user.getPersonID()));

        // Redirect to profile page
        response.sendRedirect("profile");
    }

    // Utility method to convert InputStream to byte array
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

