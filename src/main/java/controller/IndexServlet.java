package controller;


import data.FurnitureDB;
import business.Furniture;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.beans.Customizer;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "IndexServlet", value = "/indexServlet")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        List<Furniture> listFurniture = FurnitureDB.getFurnituresHot(7);
        request.setAttribute("listFurniture", listFurniture);
        context.getRequestDispatcher("/KhachHang/index.jsp").forward(request, response);
    }
}