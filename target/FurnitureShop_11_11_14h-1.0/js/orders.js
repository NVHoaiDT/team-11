function confirmAction(orderId, action) {
    const userConfirmed = confirm("Bạn chắc chắn muốn thực hiện thao tác này?");

    if (userConfirmed) {
        // Gửi yêu cầu đến servlet để cập nhật trạng thái
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "../manageOrdersServlet", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhr.onload = function() {
            if (xhr.status === 200) {
                alert("Thực hiện thao tác thành công!");
                // Bạn có thể tự động làm mới trang hoặc cập nhật lại trạng thái trên giao diện
                location.reload(); // Hoặc cập nhật lại phần giao diện nếu cần
            } else {
                alert("Có lỗi xảy ra khi thực hiện thao tác.");
            }
        };

        // Gửi ID đơn hàng đến servlet
        xhr.send("orderId=" + orderId + "&action=" + action);
    }
}

function feedbackOrder(orderId, customerId) {
    // Chuyển hướng sang feedback.jsp với orderId dưới dạng tham số URL
    window.location.href = "/KhachHang/feedback.jsp?orderId=" + orderId + "&customerId=" + customerId;
}
