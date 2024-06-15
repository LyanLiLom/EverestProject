$(document).ready(function () {

  $('#myformLogin').submit(function (event) {

    event.preventDefault();
    var username = $('#usernameLogin').val()
    var password = $('#passwordLogin').val()

    var formData = {
      username: username,
      password: password
    };

    $.ajax({
      method: "POST",
      url: "http://localhost:6601/login",
      data: JSON.stringify(formData),
      contentType: "application/json; charset=utf-8",
    })
      .done(function (response, textStatus, xhr) {
        var token = xhr.getResponseHeader('Authorization');
        var role = xhr.getResponseHeader('Role');
        var rememberMe = $('#rememberMe').prop('checked');
        if (rememberMe) {
          localStorage.setItem('username', formData.username);
          localStorage.setItem('token', token);
          localStorage.setItem('role', role);
        } else {
          sessionStorage.setItem('username', formData.username);
          sessionStorage.setItem('token', token);
          sessionStorage.setItem('role', role);
        }
        console.log("Login Success");
        window.location.href = "index.html";
      })
      .fail(function (xhr, status, error) {
        // Xử lý khi có lỗi trong quá trình gửi yêu cầu AJAX
        console.error("Lỗi khi gửi yêu cầu AJAX:", error);
      })

  })

  $('#myformRegister').submit(function (event) {

    event.preventDefault();
    var email = $('#usernameRegister').val()
    var password = $('#passwordRegister ').val()
    var firstname = $('#firstname').val()
    var lastname = $('#lastname').val()
    var phone = $('#phone').val()

    console.log("Email trước khi gửi:", email);

    var formData = {
      email: email,
      password: password,
      firstname: firstname,
      lastname: lastname,
      phone: phone
    };

    console.log("Form data:", formData)

    $.ajax({
      method: "POST",
      url: "http://localhost:6601/login/register",
      data: JSON.stringify(formData),
      contentType: "application/json; charset=utf-8",
    })
      .done(function (response, textStatus, xhr) {
        console.log("Server response:", response.statusCode);
        if (xhr.status === 201) {
          alert("Tạo tài khoản thành công")
          window.location.href = "login.html"
        }


      })
      .fail(function (xhr, status, error) {
        // Xử lý khi có lỗi trong quá trình gửi yêu cầu AJAX
        console.error("Lỗi khi gửi yêu cầu AJAX:", error);

        if (xhr.status === 400) {
          alert("Tạo tài khoản thất bại: Trùng lặp tài khoản");
        } else {
          alert("Tạo tài khoản thất bại");
        }
      })

  })
  


})