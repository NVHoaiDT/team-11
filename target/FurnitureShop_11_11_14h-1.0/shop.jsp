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
		<title>Furni Free Bootstrap 5 Template for Furniture and Interior Design Websites by Untree.co </title>
	</head>

	<body>

		<c:import url="includes/header.jsp" />

		<!-- Start Hero Section -->

		<div class="hero">
			<div class="container">
				<div class="row justify-content-between position-relative">
					<div class="col-lg-5">
						<div class="intro-excerpt">
							<h1>Shop</h1>
							<p class="mb-4">Donec vitae odio quis nisl dapibus malesuada. Nullam ac aliquet velit. Aliquam vulputate velit imperdiet dolor tempor tristique.</p>
							<p><a href="" class="btn btn-secondary me-2">Shop Now</a><a href="#" class="btn btn-white-outline">Explore</a></p>
						</div>
					</div>
					<div class="col-lg-7">
						<div class="hero-img-wrap">
							<img src="images/couch.png" class="img-fluid">
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- End Hero Section -->


		

		<div class="untree_co-section product-section before-footer-section">
		    <div class="container">
				<form action="searchServlet" method="POST" class="d-flex mb-3">
					<input
							type="text"
							name="keyword"
							class="form-control me-2"
							placeholder="Nhập từ khóa tìm kiếm..."
							style="width: 85%;"
					>
					<button
							type="submit"
							class="btn btn-primary"
							style="width: 10%;"
					>
						<i class="fas fa-search"></i>
					</button>
				</form>


				<div class="row">
					<div class="col-md-3">
						<div class="filter-container">
							<h5>Bộ lọc tìm kiếm </h5>

							<!-- Mức giá -->
							<div class="filter-section">
								<h3>Mức giá</h3>
								<div class="filter-options">
									<label><input type="radio" name="price" value="under-100"> Dưới 100.000đ</label>
									<label><input type="radio" name="price" value="100-200"> Từ 100.000đ - 200.000đ</label>
									<label><input type="radio" name="price" value="200-300"> Từ 200.000đ - 300.000đ</label>
									<label><input type="radio" name="price" value="300-500"> Từ 300.000đ - 500.000đ</label>
									<label><input type="radio" name="price" value="500-1mil"> Từ 500.000đ - 1 triệu</label>
									<label><input type="radio" name="price" value="1mil-2mil"> Từ 1 triệu - 2 triệu</label>
									<label><input type="radio" name="price" value="2mil-5mil"> Từ 2 triệu - 5 triệu</label>
									<label><input type="radio" name="price" value="5mil-10mil"> Từ 5 triệu - 10 triệu</label>
									<label><input type="radio" name="price" value="over-10mil"> Trên 10 triệu</label>
								</div>
							</div>

							<!-- Loại sản phẩm -->
							<div class="filter-section">
								<h3>Loại</h3>
								<div class="filter-options">
									<label><input type="radio" name="type" value="decor-table"> Bàn trang trí</label>
									<label><input type="radio" name="type" value="decor-pot"> Chậu trang trí</label>
									<label><input type="radio" name="type" value="chandelier"> Đèn chùm</label>
									<label><input type="radio" name="type" value="floor-lamp"> Đèn đứng</label>
									<label><input type="radio" name="type" value="pendant-lamp"> Đèn thả trần</label>
									<!-- Add more options as needed -->
								</div>
							</div>

							<div class="filter-section">
								<h3>Màu sắc</h3>
								<div class="filter-options">
									<label><input type="radio" name="color"> Đen</label>
									<label><input type="radio" name="color"> Đen Cam</label>
									<label><input type="radio" name="color"> Đỏ</label>
									<label><input type="radio" name="color"> Đồng Xám</label>
									<label><input type="radio" name="color"> Nâu Đậm</label>
									<label><input type="radio" name="color"> Xanh Rêu</label>
									<label><input type="radio" name="color"> Sồi Đậm</label>
									<label><input type="radio" name="color"> Nâu Đen</label>
								</div>
							</div>

							<div class="filter-section">
								<h3>Chất liệu</h3>
								<div class="filter-options">
									<label><input type="radio" name="material"> Gỗ</label>
									<label><input type="radio" name="material"> Kim loại</label>
									<label><input type="radio" name="material"> Da</label>
									<label><input type="radio" name="material"> Nhựa</label>
									<label><input type="radio" name="material"> Gỗ công nghiệp</label>
									<label><input type="radio" name="material"> Sắt</label>
									<label><input type="radio" name="material"> Vải</label>
								</div>
							</div>


							<!-- Các tiêu chí lọc khác -->
						</div>
					</div>
					<div class="col-md-9">
						<div class="row">
							<c:forEach var="furniture" items="${listFurnitures}">
								<div class="col-12 col-md-4 col-lg-3 mb-5">
									<form action="furnitureServlet" method="POST" style="display:inline;">
										<input type="hidden" name="furnitureId" value="${furniture.id}">
										<a href="javascript:void(0);" class="product-item" onclick="this.closest('form').submit();">
											<img src="data:image/png;base64,${furniture.representativeImage.base64Data}"
												 alt="${furniture.representativeImage.fileName}"
												 class="img-fluid product-thumbnail">
											<h3 class="product-title">${furniture.getCategory().getCategoryName()}</h3>
											<strong class="product-color">${furniture.furnitureColor}</strong><br>
											<strong class="product-price">${furniture.furniturePrice}</strong>
										</a>
									</form>
									<!-- Form chứa nút submit -->
									<form action="PurchaseServlet" method="POST" class="btn-form" style="display:inline;">
										<input type="hidden" name="furnitureID" value="${furniture.id}">
										<input type="hidden" name="action" value="addtocart">
										<button type="submit" class="btn-submit">
											<span class="icon-cross">
												<img src="images/cross.svg" class="img-fluid">
											</span>
										</button>
									</form>
								</div>
							</c:forEach>
						</div>
					</div>
					<c:if test="${pagination.totalPages > 0}">
						<ul class="pagination">
							<!-- Hiển thị trang 1 và trang 2 mà không có lớp 'active' -->
							<li class="page-item">
								<c:if test="${pagination.currentPage > 1}">
									<button class="page-link" data-pagination="${pagination.currentPage-1}"> Trang trước</button>
								</c:if>
							</li>

							<c:forEach var="i" begin="1" end="${pagination.totalPages}">
								<li class="page-item ${pagination.currentPage == i ? 'active' : ''}">
									<button class="page-link" data-pagination="${i}">${i}</button>
								</li>
							</c:forEach>

							<li class="page-item">
								<c:if test="${pagination.currentPage < pagination.totalPages}">
									<button class="page-link" data-pagination="${pagination.currentPage+1}"> Trang sau</button>
								</c:if>
							</li>
						</ul>
					</c:if>
				</div>
		    </div>
		</div>
		<%-- Kiểm tra xem có thông báo nào không --%>
		<% String message = (String) request.getAttribute("message"); %>
		<% if (message != null) { %>
		<script type="text/javascript">
			alert("<%= message %>");
			event.preventDefault();
		</script>
		<% } %>
		<c:import url="includes/footer.jsp" />

		<script src="js/bootstrap.bundle.min.js"></script>
		<script src="js/tiny-slider.js"></script>
		<script src="js/custom.js"></script>
		<script src="js/shop.js"></script>
	</body>

</html>
