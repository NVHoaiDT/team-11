<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 09/11/2024
  Time: 12:17 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <!-- Start Header/Navigation -->
  <nav class="custom-navbar navbar navbar navbar-expand-md navbar-dark bg-dark" arial-label="Furni navigation bar">

    <div class="container">
      <a class="navbar-brand" href="index.jsp">Furni<span>.</span></a>

      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsFurni" aria-controls="navbarsFurni" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarsFurni">
        <ul class="custom-navbar-nav navbar-nav ms-auto mb-2 mb-md-0">
          <li class="nav-item ">
            <a class="nav-link" href="index.jsp">Home</a>
          </li>
          <li><a class="nav-link" href="shopServlet">Shop</a></li>
          <li><a class="nav-link" href="about.jsp">About us</a></li>
          <li><a class="nav-link" href="services.jsp">Services</a></li>
          <li><a class="nav-link" href="blog.jsp">Blog</a></li>
          <li><a class="nav-link" href="contact.jsp">Contact us</a></li>
          <%
            if(session.getAttribute("customer") == null && session.getAttribute("staff") == null && session.getAttribute("owner") == null){
          %>
            <li><a class="nav-link" href="login.jsp">Login</a></li>
          <%
            } else {
          %>
            <li><a class="nav-link" href="LogoutServlet">Logout</a></li>
          <%
            }
          %>

        </ul>

        <ul class="custom-navbar-cta navbar-nav mb-2 mb-md-0 ms-5">
          <li><a class="nav-link" href="#"><img src="images/user.svg"></a></li>
          <li><a class="nav-link" href="cart.jsp"><img src="images/cart.svg"></a></li>
          <li><a class="nav-link" href="orders.jsp"><img src="images/orders.svg"></a></li>
        </ul>
      </div>
    </div>

  </nav>
  <!-- End Header/Navigation -->
</html>
