package controller;

import business.Furniture;
import data.FurnitureDB;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchServlet", value = "/searchServlet")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        ServletContext sc = getServletContext();
//        String keyword = request.getParameter("keyword");
//        List<Furniture> listFurnitures;
//        if (keyword == null || keyword.equals("")) {
//            listFurnitures = FurnitureDB.getAllFurnitures();
//        }
//        else{
//            listFurnitures = FurnitureDB.getFurnitureByName(keyword);
//        }
//        request.setAttribute("listFurnitures", listFurnitures);
//        sc.getRequestDispatcher("/shop.jsp").forward(request, response);
    }
}