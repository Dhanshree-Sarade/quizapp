$(document).ready(function() {
    /*alert("Load All Data....");*/
    const loggedInUser = JSON.parse(localStorage.getItem('loggedInUser'));
    console.log(loggedInUser);
    if (loggedInUser && loggedInUser.id) {
        getProfileData(loggedInUser.id);
        getUserProfileData(loggedInUser.id);
    } else {
        console.error("No logged in user found");
    }

    $('#editUserDetails').click(function() {
        // Open the modal
        $('#exampleModal').modal('show');
    });

    $('#saveChanges').on('click', function() {
        // Gather data from the input fields
        var userId = $('#userId1').val();
        var firstName = $('#firstName1').val();
        var lastName = $('#lastName1').val();
        var mobileNum = $('#mobileNum1').val();
        var password = $('#password1').val();
        var confirmPassword = $('#confirmPassword1').val();
        var email = $('#email1').val();

        // Email validation
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            alert('Please enter a valid email address.');
            return;
        }

        // Mobile number validation
        const mobileRegex = /^\d{10}$/;
        if (!mobileRegex.test(mobileNum)) {
            alert('Please enter a valid mobile number.');
            return;
        }

        // Password validation
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
        if (password !== confirmPassword) {
            alert('Passwords do not match. Please try again.');
            return;
        }

        if (!passwordRegex.test(password)) {
            alert('Password must be at least 8 characters long, include at least one uppercase letter, one lowercase letter, one number, and one special character.');
            return;
        }

        var formData = new FormData();
        formData.append('id', userId);
        formData.append('firstName', firstName);
        formData.append('lastName', lastName);
        formData.append('mobileNum', mobileNum);
        formData.append('password', password);
        formData.append('confirmPassword', confirmPassword);
        formData.append('email', email);

        // Make AJAX request to update user details
        $.ajax({
            url: '/editUserDetails', // Update with the correct endpoint
            type: 'PUT',
            processData: false, // Prevent jQuery from automatically transforming the data into a query string
            contentType: false, // Let the content type be set by FormData
            data: formData,
            success: function(response) {
                // Handle success (you can update the UI or notify the user)
                alert('User updated successfully.');
                $('#exampleModal').modal('hide');
                // Ensure the profile data is refreshed after the update
                getProfileData(loggedInUser.id);
                getUserProfileData(loggedInUser.id);
            },
            error: function(xhr, status, error) {
                // Handle error
                console.log("Error updating user: ", xhr.responseText);
                alert('Error updating user: ' + xhr.responseText);
            }
        });
    });
});

function getProfileData(id) {
    /*alert("get Details...");*/
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/getUserById/" + id,
        dataType: 'json',
        success: function(data) {
            console.log("Data is : ", data);
            if (data) {
                $('#userId').val(data.id);
                $("#firstName").val(data.firstName);
                $("#lastName").val(data.lastName);
                $("#password").val(data.password);
                $("#mobile-number").val(data.mobileNum);
                $("#email").val(data.email);
            }
        },
        error: function(e) {
            console.log("Error in fetching data for Id....");
        }
    });
}

function getUserProfileData(id) {
    console.log("user id is : ", id);
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/getUserById/" + id,
        dataType: 'json',
        success: function(data) {
            console.log("Edit Data is : ", data);
            if (data) {
                $('#userId1').val(data.id);
                $("#firstName1").val(data.firstName);
                $("#lastName1").val(data.lastName);
                $("#password1").val(data.password);
                $("#confirmPassword1").val(data.confirmPassword);
                $("#mobileNum1").val(data.mobileNum);
                $("#email1").val(data.email);
            } else {
                console.log("Data is not Present.");
            }
        },
        error: function(e) {
            console.log("Error in fetching data for current Id....");
        }
    });
}
