<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="author" content="Untree.co">
  <link rel="shortcut icon" href="favicon.png">

  <meta name="description" content="" />
  <meta name="keywords" content="bootstrap, bootstrap4" />

		<!-- Bootstrap CSS -->
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
		<link href="css/tiny-slider.css" rel="stylesheet">
		<link href="css/style.css" rel="stylesheet">
		<link href="css/orders.css" rel="stylesheet">
		<title>Furni Free Bootstrap 5 Template for Furniture and Interior Design Websites by Untree.co </title>
	</head>

	<body>

		<c:import url="includes/header.jsp" />

		<!-- Start Hero Section -->
			<div class="hero">
				<div class="container">
					<div class="row justify-content-between">
						<div class="col-lg-5">
							<div class="intro-excerpt">
								<h1>Lịch sử mua</h1>
							</div>
						</div>
						<div class="col-lg-7">
							
						</div>
					</div>
				</div>
			</div>
		<!-- End Hero Section -->

		<div class="search-container">
			<input type="text" placeholder="Tìm kiếm..." class="search-input" />
			<button class="search-button">
				<img src="images/search.svg"/>
			</button>
		</div>

		<div class="untree_co-section before-footer-section">
            <div class="container">
              <div class="row mb-5">
                <form class="col-md-12" method="post">
                  <div class="site-blocks-table">
                    <table class="table">
                      <thead>
                        <tr>
                          <th class="product-thumbnail">Hình ảnh</th>
                          <th class="product-name">Tên sản phẩm</th>
                          <th class="product-price">Giá</th>
                          <th class="product-quantity">Số lượng</th>
                          <th class="product-total">Tổng giá</th>
                        </tr>
                      </thead>
					<%--<tbody class="order">
					<c:forEach var="product" items="${productList}">
						<tr>
							<td><img src="${product.image}" alt="Image" class="img-fluid"></td>
							<td><h2 class="h5 text-black">${product.name}</h2></td>
							<td>${product.price}</td>
							<td>${product.quantity}</td>
							<td>${product.totalPrice}</td>
						</tr>
					</c:forEach>
					</tbody>--%>
                      <tbody class="order">
						<tr>
						  <td class="product-thumbnail">
							<img src="images/product-1.png" alt="Image" class="img-fluid">
						  </td>
						  <td class="product-name">
							<h2 class="h5 text-black">Product 1</h2>
						  </td>
						  <td class="product-price">50.000VND</td>
						  <td class="product-quantity">1</td>
						  <td class="product-total">50.000VND</td>
						</tr>
						<tr>
							<td class="product-thumbnail">
								<img src="images/product-2.png" alt="Image" class="img-fluid">
							</td>
							<td class="product-name">
								<h2 class="h5 text-black">Product 2</h2>
							</td>
							<td class="product-price">50.000VND</td>
							<td class="product-quantity">1</td>
							<td class="product-total">50.000VND</td>
						</tr>
					  	<tr>
							<td colspan="2">Địa chỉ: 147/1/11, Tân Lập 2, Hiệp Phú, Thủ Đức, TP HCM, 0329119542</td>
							<td class="order-address">Đã giao</td>
							<td class="order-total">Thành tiền: 100.000VND</td>
							<td><a href="feedback.jsp"><button type="button" class="btn-order">Đánh giá</button></a></td>
						</tr>
                      </tbody>

						<tr style="height: 20px;"></tr>

					  <tbody class="order">
						<tr>
							<td class="product-thumbnail">
								<img src="images/product-1.png" alt="Image" class="img-fluid">
							</td>
							<td class="product-name">
								<h2 class="h5 text-black">Product 1</h2>
							</td>
							<td class="product-price">50.000VND</td>
							<td class="product-quantity">1</td>
							<td class="product-total">50.000VND</td>
						</tr>
						<tr>
							<td class="product-thumbnail">
								<img src="images/product-2.png" alt="Image" class="img-fluid">
							</td>
							<td class="product-name">
								<h2 class="h5 text-black">Product 2</h2>
							</td>
							<td class="product-price">50.000VND</td>
							<td class="product-quantity">1</td>
							<td class="product-total">50.000VND</td>
						</tr>
						<tr>
							<td colspan="2">Địa chỉ: 147/1/11, Tân Lập 2, Hiệp Phú, Thủ Đức, TP HCM, 0329119542</td>
							<td class="order-address">Đã giao</td>
							<td class="order-total">Thành tiền: 100.000VND</td>
							<td><a href="feedback.jsp"><button type="button" class="btn-order">Đánh giá</button></a></td>
						</tr>
					  </tbody>
                    </table>
                  </div>
                </form>
              </div>
            </div>
          </div>

		<c:import url="includes/footer.jsp" />

		<script src="js/bootstrap.bundle.min.js"></script>
		<script src="js/tiny-slider.js"></script>
		<script src="js/custom.js"></script>
	</body>

</html>
