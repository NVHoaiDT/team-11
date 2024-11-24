package controller.staffManagementController;

import business.Staff;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/searchStaff")
public class SearchEmployeeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String action = request.getParameter("search-action");
        List<Staff> listStaff = (List<Staff>) session.getAttribute("listStaff");
        List<Staff> searchStaff = new ArrayList<>();

        if(action.equals("search-name")) {
            String searchName = request.getParameter("search-name");
            for (Staff s : listStaff) {
                if (s.getName().toLowerCase().contains(searchName.toLowerCase())) {
                    searchStaff.add(s);
                }
            }
            request.setAttribute("listStaff", searchStaff);
            request.setAttribute("searchName", searchName);
        }
        else if(action.equals("search-filters")) {
            String searchSalary = request.getParameter("search-salary");
            String searchAge = request.getParameter("search-age");
            String searchWorkTime = request.getParameter("search-workTime");

            double salaryMin=0;
            double salaryMax=999999999;
            if(searchSalary.equals("under10")){
                salaryMin = 0.0;
                salaryMax = 10000000;
            }
            else if(searchSalary.equals("10to20")){
                salaryMin = 10000000;
                salaryMax = 20000000;
            }
            else if(searchSalary.equals("over20")){
                salaryMin = 20000000;
                salaryMax = 99999999;
            }

            int ageMin=0;
            int ageMax=99;
            if(searchAge.equals("18to25")){
                ageMin = 18;
                ageMax = 25;
            }
            else if(searchAge.equals("25to30")){
                ageMin = 25;
                ageMax = 30;
            }
            else if(searchAge.equals("over30")){
                ageMin = 30;
                ageMax = 99;
            }

            int workTimeMin=0;
            int workTimeMax=99;
            if(searchWorkTime.equals("under1")){
                workTimeMin = 0;
                workTimeMax = 1;
            }
            else if(searchWorkTime.equals("1to3")){
                workTimeMin = 1;
                workTimeMax = 3;
            }
            else if(searchWorkTime.equals("3to5")){
                workTimeMin = 3;
                workTimeMax = 5;
            }
            else if(searchWorkTime.equals("over5")){
                workTimeMin = 5;
                workTimeMax = 99;
            }

            //listEmployee = getEmployeeByFilters(salaryMin, salaryMax, ageMin, ageMax, workTimeMin, workTimeMax);
            for(Staff s : listStaff) {
                if(s.getSalary() >= salaryMin && s.getSalary() <= salaryMax &&
                        s.getAge() >= ageMin && s.getAge() <= ageMax &&
                        s.getWorkTime() >= workTimeMin && s.getWorkTime() <= workTimeMax) {

                    searchStaff.add(s);
                }
            }
            request.setAttribute("listStaff", searchStaff);
        }

        request.getRequestDispatcher("listStaff.jsp").forward(request, response);
    }

}
