$(document).ready(function () {

    $('#productForm').submit(function (event) {
      event.preventDefault();

      var token = localStorage.getItem('token') || sessionStorage.getItem('token')
  
      var formData = {
        token: token
      };
  
      $.ajax({
        method: "GET",
        url: "http://localhost:6601/product/table",
        data: formData,
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
        },
        success: function (response, textStatus, xhr) {
            sessionStorage.setItem('productList', JSON.stringify(response.data));
            window.location.href = "productAdmin.html";
        },
        error: function (xhr, status, error) {
            console.error("Lỗi khi gửi yêu cầu AJAX:", error);
        }
    });
  
    })
})