$(document).ready(function(){
    // Kiểm tra xem người dùng đã đăng nhập hay chưa
    var token = localStorage.getItem('token') || sessionStorage.getItem('token');
    var username = localStorage.getItem('username') || sessionStorage.getItem('username');
    var role = localStorage.getItem('role') || sessionStorage.getItem('role');

    if (token && username) {
        // Nếu có token và username trong localStorage, người dùng đã đăng nhập
        if(role === "ROLE_ADMIN"){
            $('#userNameDisplay').text(username); // Hiển thị tên người dùng
            $('#adminLinkLi').show();
            $('#logoutLink').show(); // Hiện liên kết Logout
            $('#registerLink').hide(); // Ẩn liên kết Register
            $('#loginLink').hide(); // Ẩn liên kết Login
        }else{
            $('#userNameDisplay').text(username); // Hiển thị tên người dùng
            $('#adminLinkLi').hide();
            $('#logoutLink').show(); // Hiện liên kết Logout
            $('#registerLink').hide(); // Ẩn liên kết Register
            $('#loginLink').hide(); // Ẩn liên kết Login
            }
    } else {
        // Nếu không có token hoặc username trong localStorage, người dùng chưa đăng nhập
        $('#userNameDisplay').text("My Account"); // Hiển thị "My Account"
        $('#logoutLink').hide(); // Ẩn liên kết Logout
        $('#registerLink').show(); // Hiện liên kết Register
        $('#loginLink').show(); // Hiện liên kết Login
    }

  
    // Xử lý khi người dùng đăng xuất
    $('#logoutLink').click(function() {
        localStorage.removeItem('token'); 
        localStorage.removeItem('username'); 
        localStorage.removeItem('role'); 
        sessionStorage.removeItem('token');
        sessionStorage.removeItem('username');
        sessionStorage.removeItem('role');
        $('#logoutLink').hide();
        $('#registerLink').show();
        $('#loginLink').show();
        window.location.href = "index.html";
    });

    $('#adminLink').click(function (e) {
        e.preventDefault(); // Ngăn chặn hành động mặc định của thẻ a
        if (role === "ROLE_ADMIN") {
            window.location.href = "loginController.html";
        } else {
            alert('Bạn không có quyền truy cập vào trang này.'); // Thông báo không có quyền
        }
    });

    if (window.location.pathname.includes("login.html") || window.location.pathname.includes("register.html")) {
        if (token && username) {
            window.location.href = "index.html"; // Chuyển hướng nếu người dùng đã đăng nhập
        }
    }
    
});