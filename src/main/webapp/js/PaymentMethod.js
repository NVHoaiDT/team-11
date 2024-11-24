document.querySelectorAll('input[name="paymentMethod"]').forEach((elem) => {
    elem.addEventListener("change", function() {
        document.getElementById("collapsebank").classList.remove("show");
        document.getElementById("collapsecash").classList.remove("show");

        if (this.id === "paymentBank") {
            document.getElementById("collapsebank").classList.add("show");
        } else if (this.id === "paymentCash") {
            document.getElementById("collapsecash").classList.add("show");
        }
    });
});

// Hàm xác nhận
function confirmSelection() {
    // 1. Khóa chỉnh sửa số lượng
    let quantityInputs = document.querySelectorAll('.quantity');
    quantityInputs.forEach(input => {
        input.setAttribute('readonly', true);
        input.setAttribute('disabled', true);
    });

    // 2. Hiển thị phần mã giảm giá
    document.getElementById('coupon-section').style.display = 'block';
    document.getElementById('payment-section').style.display = 'block';
    // 3. Ẩn nút xác nhận và hiển thị nút quay lại
    document.getElementById('btn-confirm').style.display = 'none';
    document.getElementById('btn-back').style.display = 'block';
}

// Hàm quay lại
function goBack() {
    // 1. Ẩn phần mã giảm giá
    document.getElementById('coupon-section').style.display = 'none';
    document.getElementById('payment-section').style.display = 'none';

    // 2. Hiển thị lại nút xác nhận và ẩn nút quay lại
    document.getElementById('btn-confirm').style.display = 'block';
    document.getElementById('btn-back').style.display = 'none';

    // 3. Cho phép chỉnh sửa lại số lượng (nếu cần)
    let quantityInputs = document.querySelectorAll('.quantity');
    quantityInputs.forEach(input => {
        input.removeAttribute('readonly');
        input.removeAttribute('disabled');
    });

    // 4. Đặt lại giá trị giảm giá về 0
    let discountInput = document.getElementById('c_code');
    discountInput.value = '';  // Đặt lại mã giảm giá

    // Cập nhật lại tổng thanh toán khi mã giảm giá bị xóa
    updateAmount();
}


function updateTotal() {
    let grandTotal = 0;

    // Lặp qua tất cả các input số lượng và tính lại tổng tiền
    document.querySelectorAll('.quantity').forEach(function(input) {
        let quantity = parseInt(input.value);

        // Nếu số lượng nhập vào nhỏ hơn 1, tự động đổi về 1
        if (quantity < 1) {
            quantity = 1;
            input.value = quantity; // Cập nhật giá trị trong ô nhập
        }

        const price = parseInt(input.getAttribute('data-price'));
        const totalCell = document.getElementById(input.getAttribute('data-total'));

        // Tính lại tổng tiền cho sản phẩm này
        const totalPrice = price * quantity;
        totalCell.textContent = `${Math.round(totalPrice).toFixed(0)}`; // Làm tròn và chuyển thành chuỗi số nguyên

        // Cộng vào tổng tiền toàn bộ
        grandTotal += totalPrice;
    });
    // Cập nhật tổng tiền toàn bộ
    let discount = 0;
    document.getElementById('grand-total').textContent = `${Math.round(grandTotal).toFixed(0)}`; // Làm tròn tổng tiền
    document.getElementById('discount').textContent = `${Math.round(discount).toFixed(0)}`;
    document.getElementById('amount').textContent = `${Math.round(grandTotal).toFixed(0)}`;
}

// Hàm cập nhật tổng thanh toán
function updateAmount() {
    // Lấy giá trị tổng tiền (order) và giảm giá từ các phần tử trong giao diện
    let order = document.getElementById('grand-total').textContent;
    let discount = document.getElementById('discount').textContent;

    // Lấy mã giảm giá (coupon code) đã chọn
    let couponCode = document.getElementById('c_code').value;

    // Kiểm tra mã giảm giá và tính toán giảm giá
    if (couponCode) {
        const formData = new URLSearchParams();
        formData.append('couponCode', couponCode);
        formData.append('action', 'coupon');
        formData.append('total', order);
        const furnitureQuantities = getFurnitureQuantities();

        // Thêm dữ liệu số lượng các sản phẩm vào formData
        furnitureQuantities.forEach(furniture => {
            formData.append('listCategoryID', furniture.furnitureID);  // Dùng [] để gửi một mảng
            formData.append('quantity', furniture.quantity);
        });
        // Gửi yêu cầu POST đến servlet
        fetch('PurchaseServlet', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded', // Đảm bảo rằng servlet có thể xử lý request
            },
            body: formData.toString() // Gửi couponCode trong body
        }) .then(response => response.text())  // Chuyển đổi phản hồi thành JSON
            .then(data => {
                if (data === 'NoCoupon') {
                    discount = 0;
                    document.getElementById('discount').textContent = `${Math.round(discount).toFixed(0)}`;
                    let totalAmount = order - discount;
                    document.getElementById('amount').textContent = `${Math.round(totalAmount).toFixed(0)}`;
                    alert('Mã giảm giá đã hết lượt sử dụng');
                } else {
                    discount = parseFloat(data);
                    document.getElementById('discount').textContent = `${Math.round(discount).toFixed(0)}`;
                    let totalAmount = order - discount;
                    document.getElementById('amount').textContent = `${Math.round(totalAmount).toFixed(0)}`;
                }
            })
            .catch(error => {
                console.error('Lỗi khi gọi servlet:', error); // Xử lý lỗi nếu có
            });
    }
}

//Hàm cập nhật thông tin của mã giảm giá
document.getElementById('c_code').addEventListener('change', function () {
    var selectedOption = this.options[this.selectedIndex];

    if (selectedOption.value !== "") {
        var couponType = selectedOption.getAttribute('data-coupon-type');
        var couponValue = parseInt(selectedOption.getAttribute('data-coupon-value')); // Loại bỏ phần thập phân
        var endDate = selectedOption.getAttribute('data-end-date');
        var conditions = selectedOption.getAttribute('data-conditions');
        var minOrderValue = parseInt(selectedOption.getAttribute('data-min-order-value')); // Loại bỏ phần thập phân
        var useLimit = parseInt(selectedOption.getAttribute('data-use-limit')); // Loại bỏ phần thập phân
        var currentUsage = parseInt(selectedOption.getAttribute('data-current-usage')); // Loại bỏ phần thập phân

        document.getElementById('data-end-date').textContent = endDate;
        document.getElementById('data-conditions').textContent =
            (conditions === 'all')
                ? 'Tất cả sản phẩm'
                : (conditions === 'min')
                    ? 'Giá trị đơn hàng tối thiểu: ' + minOrderValue
                    : 'Các sản phẩm nhất định';
        document.getElementById('data-coupon-value').textContent = (couponType === "money") ? couponValue : couponValue + "%";
        document.getElementById('data-current-usage').textContent = useLimit - currentUsage;

        // Hiển thị phần chi tiết mã giảm giá
        document.getElementById('coupon-details').style.display = 'block';
    } else {
        // Ẩn phần chi tiết nếu không chọn mã giảm giá
        document.getElementById('coupon-details').style.display = 'none';
    }
    updateAmount(); // Hàm cập nhật tổng thanh toán
});

// lấy list idcategory và số lượng
function getFurnitureQuantities() {
    const furnitureQuantities = [];

    // Lấy tất cả các thẻ <td> chứa sản phẩm
    document.querySelectorAll('td[data-id]').forEach(function(item) {
        const furnitureID = item.getAttribute('data-id');  // Lấy ID từ thuộc tính data-id của <td>
        const quantity = item.querySelector('.quantity').value;  // Lấy giá trị số lượng từ input
        if (furnitureID && quantity > 0) {
            furnitureQuantities.push({ furnitureID, quantity });
        }
    });
    return furnitureQuantities;
}

//Kiểm tra phương thức thanh toán và xử lý
function checkMethodPayment() {
    const paymentMethod = document.querySelector('input[name="paymentMethod"]:checked').value;
    let amount = document.getElementById('amount').textContent;
    let action = '';
    // Tạo đối tượng FormData và thêm action cùng paymentMethod vào yêu cầu
    const formData = new URLSearchParams();
    formData.append('paymentMethod', paymentMethod);
    const furnitureQuantities = getFurnitureQuantities();
    // Thêm dữ liệu số lượng các sản phẩm vào formData
    furnitureQuantities.forEach(furniture => {
        formData.append('listCategoryID', furniture.furnitureID);  // Dùng [] để gửi một mảng
        formData.append('quantity', furniture.quantity);
    });
    formData.append('amount', amount);
    if (paymentMethod === "bank") {
        action = 'QRCODE'
        formData.append('action', action);
        // Lấy giá trị của mô tả và số tiền từ các trường input
        const description = document.getElementById('description').value;
        const amount = document.getElementById('amount').textContent || document.getElementById('amount').innerText;
        // Hiển thị lớp phủ và thông báo chờ
        document.getElementById('overlay').style.display = 'flex';
        // Hiển thị modal thông báo thanh toán ngân hàng
        formData.append('description', description);
        formData.append('amount', amount);
        fetch('PurchaseServlet', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: formData.toString()
        }).then(response => response.text())  // Đọc phản hồi trả về dưới dạng chuỗi (URL)
            .then(qrCodeUrl => {
                // Kiểm tra nếu URL mã QR hợp lệ
                document.getElementById('overlay').style.display = 'none';
                if (qrCodeUrl !== 'False') {
                    showPaymentModal(qrCodeUrl);
                    setTimeout(() =>{
                        setInterval(() =>{
                            checkPaid(amount,description);
                        },1000);
                    },20000);

                } else {
                    alert('Sản phẩm không đủ hoặc đã ngưng kinh doanh!');
                }
            })
    } else {
        action = 'payment';
        formData.append('action', action);
        fetch('PurchaseServlet', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: formData.toString()
        }).then(response => response.text())  // Đọc phản hồi dưới dạng văn bản
            .then(data => {
                if (data === 'True') {
                    alert('Đặt hàng thành công!');  // Thông báo khi thành công
                    window.location.href = "shopServlet"; // Chuyển trang
                } else {
                    alert('Đặt hàng thất bại, sản phẩm không đủ hoặc đã ngưng kinh doanh!');  // Thông báo khi thất bại
                }
            })
    }
}

// Hàm hiển thị modal với mã QR
function showPaymentModal(qrCodeUrl) {
    const modal = document.getElementById('paymentModal');
    const qrCodeImage = document.getElementById('qr-code-img');
    qrCodeImage.src = qrCodeUrl;
    modal.style.display = 'none';
    modal.style.display = 'block';

    // Dừng bộ đếm trước đó nếu có
    const timerDisplay = document.getElementById("timer");
    clearInterval(countdown);

    // Reset thời gian đếm ngược về 600 giây (10 phút)
    countdownTime = 600;
    timerDisplay.textContent = `${String(Math.floor(countdownTime / 60)).padStart(2, '0')}:${String(countdownTime % 60).padStart(2, '0')}`;
    startCountdown(); // Gọi hàm bắt đầu đếm ngược
}
// Đóng modal khi nhấn vào dấu X
function closeModal() {
    document.getElementById("paymentModal").style.display = "none";
}

// Đóng modal nếu nhấn ra ngoài modal hoặc dấu X
window.onclick = function(event) {
    if (event.target === document.getElementById("paymentModal")) closeModal();
}

//Bộ đếm thời gian của mã QR
let countdown;
let countdownTime = 600; // 10 phút tính bằng giây
function startCountdown() {
    const timerDisplay = document.getElementById("timer");

    countdown = setInterval(() => {
        let minutes = Math.floor(countdownTime / 60);
        let seconds = countdownTime % 60;

        timerDisplay.textContent =
            `${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;

        if (countdownTime <= 0) {
            clearInterval(countdown);
            closeModal();
        }
        countdownTime--;
    }, 1000);
}

//Kiểm tra chuyển khoản ngân hàng và đk dừng
let isSsucess = false
let stop = true;
async function checkPaid(price, content) {
    if(isSsucess)
    {
        if(stop) {
            const amount = document.getElementById('amount').textContent || document.getElementById('amount').innerText;
            const formDataNew = new URLSearchParams();
            const paymentMethod = document.querySelector('input[name="paymentMethod"]:checked').value;
            const action = 'payment';
            const furnitureQuantities = getFurnitureQuantities();
            stop = false;
            furnitureQuantities.forEach(furniture => {
                formDataNew.append('listCategoryID', furniture.furnitureID);  // Dùng [] để gửi một mảng
                formDataNew.append('quantity', furniture.quantity);
            });
            formDataNew.append('paymentMethod', paymentMethod);
            formDataNew.append('amount', amount);
            formDataNew.append('action', action);
            fetch('PurchaseServlet', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: formDataNew.toString()
            }).then(response => response.text())  // Đọc phản hồi dưới dạng văn bản
                .then(data => {
                    if (data === 'True') {
                        alert('Đặt hàng thành công!');  // Thông báo khi thành công
                        window.location.href = "shopServlet"; // Chuyển trang
                        return;
                    }
                })
        }
    }
    else
    {
        try {
            const response = await fetch("https://script.google.com/macros/s/AKfycbyXH6MugTcWjRNvC5rH_ynvCvq44B-VWdKwCleA5fEYKemBisrgSCdBXR_Z6QccAybM/exec");
            const data = await response.json();
            const lastPaid = data.data[data.data.length -1];
            lastPrice = lastPaid["Giá trị"];
            lastContent = lastPaid["Mô tả"];
            console.log(lastPaid["Giá trị"]);
            console.log(lastPaid["Mô tả"]);
            if(lastPrice>= price && lastContent.includes(content))
            {
                isSsucess = true;
            }
        } catch (error) {
            console.error("Lỗi", error);
        }
    }
}