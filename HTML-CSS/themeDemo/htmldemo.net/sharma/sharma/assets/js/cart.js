$('#myformLogin').submit(function(event){

    event.preventDefault();
    var username = $('#updateLink').val()
    

    var formData = {
      username: username,
      password: password
  };
    var token = localStorage.getItem('token');
    $.ajax({
        url: 'https://localhost:6601/test',
        type: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function(response) {
            console.log('Success:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
        }
    });
})