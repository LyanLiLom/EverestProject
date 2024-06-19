$(document).ready(function () {

    $('#productTable').submit(function (event) {
  
      event.preventDefault();
      var token = localStorage.getItem('token') || sessionStorage.getItem('token')
  
      var formData = {
        token: token
      };
  
      $.ajax({
        method: "POST",
        url: "http://localhost:6601/login",
        data: JSON.stringify(formData),
        contentType: "application/json; charset=utf-8",
      })
        .done(function (response, textStatus, xhr) {
          
          window.location.href = "index.html";
        })
        .fail(function (xhr, status, error) {
          // Xử lý khi có lỗi trong quá trình gửi yêu cầu AJAX
          console.error("Lỗi khi gửi yêu cầu AJAX:", error);
        })
  
    })
})