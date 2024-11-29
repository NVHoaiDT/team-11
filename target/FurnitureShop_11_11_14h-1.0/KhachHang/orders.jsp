<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
		<link href="../css/bootstrap.min.css" rel="stylesheet">
		<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
		<link href="../css/tiny-slider.css" rel="stylesheet">
		<link href="../css/style.css" rel="stylesheet">
		<link href="../css/orders.css" rel="stylesheet">

		<script type="text/javascript" src="../js/orders.js"></script>

		<title>Furni Free Bootstrap 5 Template for Furniture and Interior Design Websites by Untree.co </title>
</head>

	<body>

		<c:import url="../includes/header.jsp" />
		<c:set var="currentDate" value="<%= new java.util.Date() %>" />
		<c:set var="sevenDaysAgo" value="<%= new java.util.Date(System.currentTimeMillis() - (7L * 24 * 60 * 60 * 1000)) %>" />

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

		<div class="filter-container">
			<a class="nav-link" href="manageOrdersServlet?action=filterOrders&status=" ${param.status == '' ? 'class="active"' : ''}>Tất cả trạng thái</a>
			<a class="nav-link" href="manageOrdersServlet?action=filterOrders&status=WAITING_PROCESS" ${param.status == 'WAITING_PROCESS' ? 'class="active"' : ''}>Chờ xử lý</a>
			<a class="nav-link" href="manageOrdersServlet?action=filterOrders&status=CANCELED" ${param.status == 'CANCELED' ? 'class="active"' : ''}>Đã hủy</a>
			<a class="nav-link" href="manageOrdersServlet?action=filterOrders&status=DELIVERING" ${param.status == 'DELIVERING' ? 'class="active"' : ''}>Đang giao hàng</a>
			<a class="nav-link" href="manageOrdersServlet?action=filterOrders&status=DELIVERED" ${param.status == 'DELIVERED' ? 'class="active"' : ''}>Đã giao</a>
			<a class="nav-link" href="manageOrdersServlet?action=filterOrders&status=ACCEPTED" ${param.status == 'ACCEPTED' ? 'class="active"' : ''}>Đã chấp nhận</a>
			<a class="nav-link" href="manageOrdersServlet?action=filterOrders&status=REFUNDED" ${param.status == 'REFUNDED' ? 'class="active"' : ''}>Đã hoàn trả</a>
			<a class="nav-link" href="manageOrdersServlet?action=filterOrders&status=FEEDBACKED" ${param.status == 'FEEDBACKED' ? 'class="active"' : ''}>Đã đánh giá</a>
		</div>

		<div class="untree_co-section before-footer-section">
            <div class="container">
              <div class="row mb-5">
                <form class="col-md-12" method="post">
                  <div class="site-blocks-table">
                    <table class="table">
                      <thead>
                        <tr>
							<th class="product-image">Hình ảnh</th>
							<th class="product-name">Tên sản phẩm</th>
							<th class="product-price">Giá</th>
							<th class="product-quantity">Số lượng</th>
							<th class="product-total">Tổng giá</th>
                        </tr>
                      </thead>
						<c:forEach var="order" items="${listOrder}">
							<tbody class="order">
							<c:forEach var="item" items="${order.getFurnitureQuantity()}">
								<tr>
									<td class="product-image">
										<img src="data:image/png;base64,${item[0].representativeImage.base64Data}"
											 alt="${item[0].representativeImage.fileName}"
											 style="max-width: 100px; max-height: 100px; object-fit: cover; border-radius: 5px; margin: 5px;">
									</td>
									<td class="product-name">${item[0].category.categoryName}</td>
									<td>
										<fmt:formatNumber value="${item[0].furniturePrice}" type="number" pattern="#,###" /> VND
									</td>
									<td class="product-quantity">${item[1]}</td> <!-- Hiển thị số lượng -->
									<td class="product-total">
										<fmt:formatNumber value="${item[0].furniturePrice * item[1]}" type="number" pattern="#,###" /> VND
									</td>
								</tr>
							</c:forEach>

								<tr>
									<td colspan="2">
										Địa chỉ: ${order.customer.address.getAddress()}
									</td>
									<td>
										Ngày mua: <fmt:formatDate value="${order.orderDate}" pattern="dd MMMM yyyy" />
									</td>
									<td>
										Trạng thái: ${order.status.toString()}
									</td>
									<td>
										Thành tiền: <fmt:formatNumber value="${order.getTotalAmount()}" type="number" pattern="#,###" /> VND
									</td>
								</tr>
								<tr>
									<c:if test="${order.status == 'WAITING_PROCESS'}">
										<td>
											<button type="button" class="btn-order" onclick="confirmAction(${order.id}, 'cancelOrder')">Hủy đơn</button>
										</td>
									</c:if>

									<c:if test="${order.orderDate ge sevenDaysAgo}">
										<c:if test="${order.status == 'DELIVERED' || order.status == 'ACCEPTED' || order.status == 'FEEDBACKED'}">
											<td>
												<button type="button" class="btn-order" onclick="confirmAction(${order.id}, 'refundOrder')">Hoàn đơn</button>
											</td>
										</c:if>
										<c:if test="${order.status == 'DELIVERED'}">
											<td>
												<button type="button" class="btn-order" onclick="confirmAction(${order.id}, 'acceptOrder')">Chấp nhận</button>
											</td>
										</c:if>
										<c:if test="${order.status == 'ACCEPTED'}">
											<td>
												<button type="button" class="btn-order" onclick="feedbackOrder(${order.id}, ${order.customer.personID})">Phản hồi</button>
											</td>
										</c:if>
<%--										<c:if test="${order.status == 'FEEDBACKED'}">--%>
<%--											<td>--%>
<%--												<button type="button" class="btn-order" onclick="getFeedback(${order.id})">Xem phản hồi</button>--%>
<%--											</td>--%>
<%--										</c:if>--%>
									</c:if>
								</tr>
							</tbody>

							<tr style="height: 20px;"></tr>
						</c:forEach>
                    </table>
                  </div>
                </form>
              </div>
            </div>
          </div>

		<!-- Modal Hiển thị thông tin phản hồi -->
		<div class="modal fade" id="feedbackModal" tabindex="-1" aria-labelledby="feedbackModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="feedbackModalLabel">Phản hồi của đơn hàng</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<!-- Kiểm tra nếu có phản hồi và hiển thị -->
						<c:if test="${not empty feedback}">
							<p><strong>Đánh giá:</strong> ${feedback.rate} sao</p>
							<p><strong>Mô tả:</strong> ${feedback.description}</p>
						</c:if>
						<!-- Nếu không có phản hồi -->
						<c:if test="${empty feedback}">
							<p>Không có phản hồi cho đơn hàng này.</p>
						</c:if>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
					</div>
				</div>
			</div>
		</div>

		<script>
			// Kiểm tra xem có phản hồi hay không và nếu có thì hiển thị modal
			<c:if test="${not empty feedback}">
			var myModal = new bootstrap.Modal(document.getElementById('feedbackModal'), {
				keyboard: false
			});
			myModal.show();
			</c:if>
		</script>

		<c:import url="../includes/footer.jsp" />

		<script src="../js/bootstrap.bundle.min.js"></script>
		<script src="../js/tiny-slider.js"></script>
		<script src="../js/custom.js"></script>
	</body>

</html>
