<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 09/11/2024
  Time: 12:17 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<script>
  function confirmLogout() {
    if (confirm("Bạn có chắc chắn muốn đăng xuất?")) {
      window.location.href = "../LogoutServlet"; // Chuyển hướng đến servlet nếu người dùng chọn "OK"
    }
  }
</script>
  <!-- Start Header/Navigation -->
  <nav class="custom-navbar navbar navbar navbar-expand-md navbar-dark bg-dark" arial-label="Furni navigation bar">

    <div class="container">
      <img class="navbar-brand" src="../images/logoFurni.png" alt="Furni Logo" style="height: 150px;">
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsFurni" aria-controls="navbarsFurni" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarsFurni">
        <ul class="custom-navbar-nav navbar-nav ms-auto mb-2 mb-md-0">
          <li class="nav-item ">
            <a class="nav-link" href="/KhachHang/index.jsp">Trang Chủ</a>
          </li>
          <li><a class="nav-link" href="../shopServlet">Mua Hàng</a></li>
          <li><a class="nav-link" href="/KhachHang/about.jsp">About us</a></li>
          <li><a class="nav-link" href="/KhachHang/services.jsp">Services</a></li>
          <li><a class="nav-link" href="/KhachHang/blog.jsp">Blog</a></li>
          <li><a class="nav-link" href="/KhachHang/contact.jsp">Liên Lạc</a></li>
          <% if (session.getAttribute("customer") != null) { %>
          <li>
            <a class="nav-link" href="<%= request.getContextPath() %>/Staff/loadStaffChatList">Chat (KH)</a>
          </li>
          <% } %>

          <%
            if(session.getAttribute("customer") == null && session.getAttribute("staff") == null && session.getAttribute("owner") == null){
          %>
            <li><a class="nav-link" href="/KhachHang/login.jsp">Đăng Nhập</a></li>
          <%
            } else {
          %>
            <li>
              <a class="nav-link" href="#" onclick="confirmLogout()">Đăng Xuất</a>
            </li>
          <%
            }
          %>

        </ul>

        <ul class="custom-navbar-cta navbar-nav mb-2 mb-md-0 ms-5">
          <li class="nav-link">
            <form action="<c:url value='/loadProfile'/>" method="get" style="display: inline;">
              <button type="submit" class="nav-link btn btn-link p-0" style="border: none; background: none;">
                <img src="images/user.svg" alt="User Profile" >
              </button>
            </form>
          </li>
          <li><a class="nav-link" href="../PurchaseServlet"><img src="../images/cart.svg"></a></li>
          <li><a class="nav-link" href="../manageOrdersServlet?action=loadOrders"><img src="../images/orders.svg" alt="Orders"></a></li></ul>
      </div>
    </div>

  </nav>
  <!-- End Header/Navigation -->
</html>
