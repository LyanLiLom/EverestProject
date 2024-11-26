$('#insertProductForm').submit(function(event) {
    event.preventDefault();

    // Tạo đối tượng FormData với các giá trị cần thiết
    var formData = new FormData();
    formData.append('productName', $('#productName').val());
    formData.append('oldPrice', parseFloat($('#oldPrice').val()));
    formData.append('newPrice', parseFloat($('#newPrice').val()));
    formData.append('description', $('#description').val());
    formData.append('information', $('#information').val());
    formData.append('sku', parseInt($('#sku').val()));
    formData.append('multipartFile', $('#multipathFile')[0].files[0]);  // Sửa thành đúng ID file input

    $.ajax({
        url: 'https://localhost:6601/product/insert',
        method: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        beforeSend: function(xhr) {
            var token = localStorage.getItem('token') || sessionStorage.getItem('token');
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
        },
        success: function(response) {
            alert("Thêm sản phẩm thành công!");
            console.log("Phản hồi từ server:", response);
            
            var token = localStorage.getItem('token') || sessionStorage.getItem('token');

            $.ajax({
                method: "GET",
                url: "https://localhost:6601/product/table",
                beforeSend: function(xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
                },
                success: function(response) {
                    // Cập nhật lại danh sách sản phẩm vào sessionStorage
                    sessionStorage.setItem('productList', JSON.stringify(response.data));
    
                    // Chuyển hướng về trang productAdmin.html
                    window.location.href = "productAdmin.html";
                },
                error: function(xhr, status, error) {
                    console.error("Lỗi khi lấy danh sách sản phẩm:", error);
                    alert("Lỗi khi lấy danh sách sản phẩm!");
                }
            });
        },
        
        error: function(xhr, status, error) {
            console.error("Lỗi khi thêm sản phẩm:", error);
            if (xhr.responseJSON && xhr.responseJSON.errors) {
                xhr.responseJSON.errors.forEach(function(err) {
                    alert(err.message); 
                });
            } else {
                alert("Thêm sản phẩm thất bại!");
            }
        }
    });
});

