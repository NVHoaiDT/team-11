package controller;

import business.Furniture;
import data.FurnitureDB;
import ultil.PaginationHelper;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ShopServlet", value = "/shopServlet")
public class ShopServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        HttpSession session = request.getSession();  // Nếu chưa có session, nó sẽ tạo mới

        // xu li pagination
        int page = 1;
        int limitPage = 4;
        long totalItem = FurnitureDB.countFurniture();

        String pageParam = request.getParameter("page");
        if (pageParam != null &&  !pageParam.equals("")) {
            try {
                page = Integer.parseInt(pageParam);  // Chuyển đổi thành int
            } catch (NumberFormatException e) {
                // Xử lý khi không thể chuyển đổi pageParam sang int
                page = 1;  // Bạn có thể gán giá trị mặc định ở đây nếu cần
            }
        }

        PaginationHelper pagination = new PaginationHelper(page, limitPage, totalItem);



        List<Furniture> listFurnitures = FurnitureDB.getAllFurnitures(pagination.getLimitItem(),pagination.getSkip());

        session.setAttribute("listFurnitures", listFurnitures);
        session.setAttribute("pagination", pagination);
        sc.getRequestDispatcher("/shop.jsp").forward(request, response);    }
}