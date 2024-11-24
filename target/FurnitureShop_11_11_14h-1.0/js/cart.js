document.getElementById('checkoutBtn').addEventListener('click', function(e) {
    var selectedProducts = [];
    var checkboxes = document.getElementsByName('selectedProducts');

    // Thu thập tất cả các sản phẩm đã chọn
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            selectedProducts.push(checkboxes[i].value);
        }
    }


    // Nếu không có sản phẩm nào được chọn, ngừng gửi form và hiển thị thông báo
    if (selectedProducts.length === 0) {
        alert("Vui lòng chọn ít nhất một sản phẩm để mua.");
        e.preventDefault(); // Ngừng gửi form
        return;
    }

    // Thêm các sản phẩm đã chọn vào input ẩn
    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'listFurnitureID'; // Tên input ẩn
    input.value = selectedProducts.join(','); // Dữ liệu của các sản phẩm chọn, nối lại bằng dấu phẩy
    document.getElementById('purchaseForm').appendChild(input); // Thêm vào form

});
