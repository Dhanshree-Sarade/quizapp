$("#login").on('click', function(e){
    e.preventDefault();

    let username = $("input[name='username']").val();
    let password = $("input[name='password']").val();

    let details = {
        username: username,
        password: password
    };

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: '/login', // Update the URL to match the controller mapping
        data: JSON.stringify(details),
        dataType: 'json',
        success: function(response) {
            if (response.redirectUrl) {
                window.location.href = response.redirectUrl;
            } else {
                alert("Invalid username or password.");
            }
        },
        error: function(e) {
            alert("An error occurred while logging in.");
        }
    }); 
});

 	









/*$("#login").on('click', function(e){
    e.preventDefault();

    let username = $("input[name='username']").val();
    let password = $("input[name='password']").val();

    let details = {
        username: username,
        password: password
    };

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: '/login', // Update the URL to match the controller mapping
        data: JSON.stringify(details),
        dataType: 'json',
        success: function(response) {
            if (response.redirectUrl) {
                window.location.href = response.redirectUrl;
            } else {
                alert("Invalid username or password.");
            }
        },
        error: function(e) {
            alert("An error occurred while logging in.");
        }
    }); 
});
*/