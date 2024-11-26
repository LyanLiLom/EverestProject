$(document).ready(function() {
    
    var product = JSON.parse(sessionStorage.getItem('productToUpdate'));
    console.log("Dữ liệu sản phẩm:", product);

    if (product) {
        $('#currentProductImage').attr('src', product.imageUrl);   
        $('#productName').val(product.tenSp);                      
        $('#oldPrice').val(product.giaCu);                          
        $('#newPrice').val(product.giaMoi);                         
        $('#sku').val(product.sku);                                
        $('#description').val(product.moTa);                 
        $('#information').val(product.thongTinSp); 
                      
    } else {
        alert("Không tìm thấy sản phẩm cần cập nhật!");
    }

    $('#updateProductForm').submit(function(event) {
        event.preventDefault();

        var formData = new FormData();
    
        // Thêm các trường dữ liệu vào FormData
        formData.append('id', product.id);
        formData.append('productName', $('#productName').val());
        formData.append('oldPrice', $('#oldPrice').val());
        formData.append('newPrice', $('#newPrice').val());
        formData.append('sku', $('#sku').val());
        formData.append('description', $('#description').val());
        formData.append('information', $('#information').val());

        // Kiểm tra xem có tệp tin trong input không và thêm vào FormData
        var fileInput = $('#productImage')[0];
        if (fileInput.files.length > 0) {
            formData.append('multipartFile', fileInput.files[0]);
        } else {
            alert("Vui lòng chọn một tệp hình ảnh.");
            return;
        }

        // In ra dữ liệu để kiểm tra
        for (var pair of formData.entries()) {
            console.log(pair[0] + ': ' + pair[1]);
        }
        

        $.ajax({
            method: "POST",
            url: "https://localhost:6601/product/update",
            data: formData,
            processData: false,  
            contentType: false, 
            beforeSend: function(xhr) {
                var token = localStorage.getItem('token') || sessionStorage.getItem('token');
                xhr.setRequestHeader('Authorization', 'Bearer ' + token);
            },
            success: function(response) {
                alert("Sản phẩm đã được cập nhật thành công!");
        
                // Sau khi cập nhật thành công, gọi API GET để lấy lại danh sách sản phẩm
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
        
                sessionStorage.removeItem('productToUpdate');
            },
            error: function(xhr, status, error) {
                console.error("Lỗi khi cập nhật sản phẩm:", error);
                alert("Lỗi khi cập nhật sản phẩm!");
            }
        });
        
    });
});
