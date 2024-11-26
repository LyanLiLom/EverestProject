$(document).ready(function () {
    console.log("Trang productAdmin đã được tải");

    var productListString = sessionStorage.getItem('productList');
    
    if (productListString) {
        var productList = JSON.parse(productListString);

        if (Array.isArray(productList)) {
            var currentPage = 1;  // Bắt đầu ở trang 1
            var productsPerPage = 10;  // Mỗi trang hiển thị 10 sản phẩm

            // Hàm render sản phẩm cho một trang
            function renderProductsPage(page) {
                var start = (page - 1) * productsPerPage;
                var end = start + productsPerPage;
                var productsToShow = productList.slice(start, end);

                var htmlItem = '';
                for (var i = 0; i < productsToShow.length; i++) {
                    htmlItem += `<tr>
                                    <td class="product-thumbnail">
                                        <div style="width: 150px;height:auto"><a href="#"><img class="img-responsive" src="${productsToShow[i].imageUrl}" alt="" /></a></div>
                                    </td>
                                    <td class="product-name"><a href="#">${productsToShow[i].tenSp}</a></td>
                                    <td class="product-price-cart"><span class="amount">${productsToShow[i].giaCu}</span></td>
                                    <td class="product-price-cart"><span class="amount">${productsToShow[i].giaMoi}</span></td>
                                    <td class="product-quantity">
                                        <div class="cart-plus-minus">
                                            <input class="cart-plus-minus-box" type="text" name="qtybutton" value="${productsToShow[i].tonKho}" />
                                        </div>
                                    </td>
                                    <td class="product-remove">
                                        <a class="product-update" href="#" data-product-id="${productsToShow[i].id}"><i class="fa fa-pencil-alt"></i></a>
                                        <a class="product-delete" href="#" data-product-id = ${productsToShow[i].id}><i class="fa fa-times"></i></a>
                                    </td>
                                </tr>`;
                }

                $('#list-product').html(htmlItem);  // Cập nhật bảng sản phẩm với dữ liệu mới
                $('#current-page').text("Page " + page);  // Cập nhật số trang hiện tại
            }

            // Hàm xử lý sự kiện cho nút phân trang
            function setupPagination() {
                var totalPages = Math.ceil(productList.length / productsPerPage);

                // Hiển thị nút "Previous"
                $('#prev-page').prop('disabled', currentPage <= 1);

                // Hiển thị nút "Next"
                $('#next-page').prop('disabled', currentPage >= totalPages);

                $('#prev-page').off('click').on('click', function() {
                    if (currentPage > 1) {
                        currentPage--;
                        renderProductsPage(currentPage);  // Render lại sản phẩm khi chuyển trang
                        setupPagination();  // Cập nhật lại trạng thái phân trang
                    }
                });

                $('#next-page').off('click').on('click', function() {
                    if (currentPage < totalPages) {
                        currentPage++;
                        renderProductsPage(currentPage);  // Render lại sản phẩm khi chuyển trang
                        setupPagination();  // Cập nhật lại trạng thái phân trang
                    }
                });
            }

            // Render trang đầu tiên khi load
            renderProductsPage(currentPage);
            setupPagination();  // Cập nhật trạng thái phân trang

        } else {
            console.error("productList không phải là một mảng");
        }
    } else {
        console.error("Không tìm thấy dữ liệu productList trong sessionStorage");
    }

    $(document).on('click', '.product-delete', function (event) {
        event.preventDefault();  // Ngừng hành động mặc định của liên kết
        
        var productId = $(this).data('product-id');  // Lấy productId từ data-product-id
        var token = localStorage.getItem('token') || sessionStorage.getItem('token');

        // Gửi yêu cầu AJAX xóa sản phẩm
        $.ajax({
            method: "DELETE",
            url: "https://localhost:6601/product/delete/" + productId,  // Đảm bảo URL đúng
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + token);  // Thêm header Authorization
            },
            success: function (response, textStatus, xhr) {
                console.log("Sản phẩm đã được xóa thành công!");
                productList = productList.filter(function(product){
                    return product.id != productId;
                });
                sessionStorage.setItem('productList', JSON.stringify(productList))||localStorage.setItem('productList', JSON.stringify(productList));
                // Loại bỏ dòng sản phẩm khỏi bảng
                $(event.target).closest('tr').remove();  // Loại bỏ dòng sản phẩm khỏi bảng
                renderProductsPage(currentPage);
            },
            error: function (xhr, status, error) {
                console.error("Lỗi khi gửi yêu cầu AJAX:", error);
                alert("Xóa sản phẩm thất bại!");
            }
        });
    });

    $(document).on('click', '.product-update', function(event) {
        event.preventDefault();
    
        var productId = $(this).data('product-id');
        var product = productList.find(p => p.id === productId);
    
        if (product) {
            sessionStorage.setItem('productToUpdate', JSON.stringify(product));
            window.location.href = "UpdateProduct.html";
        }
    });

    
    
});



