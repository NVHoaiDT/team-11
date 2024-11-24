/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;


import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.PersonDB;
import mail.EmailSender;
import mail.OTPGenerater;

@WebServlet(urlPatterns = {"/forgotPassword", "/ValidateOtp", "/newPassword"})
public class ForgotPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        RequestDispatcher dispatcher = null;

        if(action.equals("DoiMatKhau")){
            String url = "index.jsp";
            String email = request.getParameter("email");
            HttpSession session = request.getSession();

            if(!PersonDB.emailExists(email)){
                url = "forgotpass.jsp";
                request.setAttribute("message", "Email này không tồn tại trong hệ thống");
            }
            else{
                session.setAttribute("email", email);
                url = "enterOtp.jsp";
                session.removeAttribute("otp");
                String generatedOTP = OTPGenerater.generateOTP(6);
                session.setAttribute("otp", generatedOTP);
                try{
                    EmailSender.sendOTP(email, generatedOTP);
                } catch(Exception e){
                    url = "forgotpass.jsp";
                    request.setAttribute("message", "Có lỗi xảy ra khi gửi OTP, vui lòng thử lại.");
                }
            }

            dispatcher=request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
        }

        if(action.equals("XacThucOTP")){
            String url = "";
            String otp = request.getParameter("otp");
            HttpSession session = request.getSession();
            String OTPss = (String) session.getAttribute("otp");
            if(OTPss.equals(otp)){
                url = "newPassword.jsp";
            } else {
                url = "enterOtp.jsp";
                request.setAttribute("message", "OTP đã nhập không đúng");
            }
            session.removeAttribute("otp");
            dispatcher=request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
        }

        if(action.equals("LuuMatKhau")){
            String pass = request.getParameter("password");
            String confPass = request.getParameter("confPassword");
            HttpSession session = request.getSession();
            String email = (String) session.getAttribute("email");
            String url = "newPassword.jsp";
            if(!pass.equals(confPass)){
                request.setAttribute("message", "Xác nhận mật khẩu không khớp");
            } else if( pass == null || confPass == null || pass.equals("") || confPass.equals("")){
                request.setAttribute("message", "Vui lòng nhập đầy đủ");
            } else{
                if(PersonDB.updatePassword(email, pass)){
                    request.setAttribute("message_success", "Đổi mật khẩu thành công");
                } else {
                    request.setAttribute("message", "Đổi mật khẩu thất bại");
                }
            }
            session.removeAttribute("email");
            dispatcher=request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
        }
    }
}
