<%--
  Created by IntelliJ IDEA.
  User: MyPC
  Date: 18/11/2024
  Time: 8:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="business.Customer" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Trang khách hàng</h1>
    <%
        Customer customer = (Customer) session.getAttribute("customer");
        String email = customer.getEmail();
        out.print("<div class='alert alert-danger mt-3'>" + email + "</div>");
    %>
</body>
</html>
