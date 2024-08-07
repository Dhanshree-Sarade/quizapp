/*$("#login").on('click', function(e){
    e.preventDefault();
   // alert("Hiiii.....");

    let email = $("input[name='email']").val();
    let password = $("input[name='password']").val();

    let details = {
        email: email,
        password: password
    };

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: '/UserLogin', // Update the URL to match the controller mapping
        data: JSON.stringify(details),
        dataType: 'json',
        success: function(response) {
            if (response.redirectUrl) {
				localStorage.setItem('loggedInUser', JSON.stringify(details));
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




/*$("#login").on('click', function(e) {
    e.preventDefault();

    let email = $("input[name='email']").val();
    let password = $("input[name='password']").val();

    let details = {
        email: email,
        password: password
    };

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: '/UserLogin', // Update the URL to match the controller mapping
        data: JSON.stringify(details),
       data: {
		email : email,
		password : password
	   },
        dataType: 'json',
        success: function(response) {
            if (response.redirectUrl) {
                // Store user details including user ID in localStorage
                let loggedInUser = {
                    email: email,
                    password: password,
                    id: response.userId
                };
                localStorage.setItem('loggedInUser', JSON.stringify(loggedInUser));
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

$("#login").on('click', function(e) {
    e.preventDefault();

    let email = $("input[name='email']").val();
    let password = $("input[name='password']").val();

    let formData = new FormData();
    formData.append('email', email);
    formData.append('password', password);

    $.ajax({
        type: "POST",
        url: '/UserLogin',
        data: formData,
        processData: false,  // Prevent jQuery from processing the data
        contentType: false,  // Ensure jQuery does not add a content-type header
        success: function(response) {
            if (response.redirectUrl) {
                let loggedInUser = {
                    email: email,
                    password: password,
                    id: response.userId
                };
                localStorage.setItem('loggedInUser', JSON.stringify(loggedInUser));
                
                toastr.success("Login successfully.");  // Show success message
                
                // Delay the redirection to show the toastr message
                setTimeout(function() {
                    window.location.href = response.redirectUrl;
                }, 2000);  // Adjust the delay time as needed (2000ms = 2 seconds)
            } else {
                toastr.error("Invalid username or password.");
            }
        },
        error: function(e) {
            toastr.error("An error occurred while logging in.");
        }
    });
});


$(document).ready(function() {

	 const loggedInUser = JSON.parse(localStorage.getItem('loggedInUser'));
    console.log(loggedInUser);
    if (loggedInUser && loggedInUser.id) {
  			fetchUserDetail(loggedInUser.id);
      } else {
        console.error("No logged in user found");
    }

});


function fetchUserDetail(id) {
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "/getUserById/" + id,
                success: function(data) {
                    console.log("User Details:", data);
                    $('#candidateName').text(data.firstName + ' ' + data.lastName);
                    $('#userDropdown').show();
                    $('#loginItem').hide();
                },
                error: function(error) {
                    console.error("Error fetching User details:", error);
                    $('#userDropdown').hide();
                    $('#loginItem').show();
                }
            });
        }

        $(document).ready(function() {
            let loggedInUser = JSON.parse(localStorage.getItem('loggedInUser'));

            if (loggedInUser) {
                // Fetch and display user details
                fetchUserDetails(loggedInUser.id);
            } else {
                // If user is not logged in, hide the dropdown and show the login link
                $('#userDropdown').hide();
                $('#loginItem').show();
            }

            // Handle logout
            $('#logoutButton').on('click', function(e) {
                e.preventDefault();
                // Clear local storage
                localStorage.removeItem('loggedInUser');
                // Redirect to login page
                window.location.href = '/logout';
            });
        });
        
        
        
        
        
        
        
        
        
        $(document).ready(function() {
	    $('#forgotPasswordLink').click(function() {
	        $('#loginForm').hide();
	        $('#forgotPasswordSection').show();
	    });
	
	    $('#sendOtpButton').click(function() {
	        const email = $('#forgotEmail').val();
	        $.ajax({
	            type: "POST",
	            url: '/sendOtp',
	            data: { email: email },
	            success: function(response) {
	                toastr.success(response);
	                $('#forgotPasswordSection').hide();
	                $('#otpVerificationSection').show();
	            },
	            error: function() {
	                toastr.error('Failed to send OTP');
	            }
	        });
	    });
	
	    $('#verifyOtpButton').click(function() {
	        const email = $('#forgotEmail').val();
	        const otp = $('#otpInput').val();
	        $.ajax({
	            type: "POST",
	            url: '/verifyOtp',
	            data: { email: email, otp: otp },
	            success: function(response) {
	                toastr.success(response);
	                $('#otpVerificationSection').hide();
	                $('#changePasswordSection').show();
	            },
	            error: function() {
	                toastr.error('Invalid OTP');
	            }
	        });
	    });
	
		$('#changePasswordButton').click(function() {
    const email = $('#forgotEmail').val();
    const newPassword = $('#newPassword').val();
    const confirmNewPassword = $('#confirmNewPassword').val();

    // Password validation regex
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

    if (!passwordRegex.test(newPassword)) {
        toastr.error('Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one number, and one special character.');
        return;
    }

    if (newPassword !== confirmNewPassword) {
        toastr.error('Passwords do not match');
        return;
    }

    $.ajax({
        type: "POST",
        url: '/changePassword',
        data: { email: email, newPassword: newPassword, confirmPassword: confirmNewPassword },
        success: function(response) {
            toastr.success(response);
            $('#changePasswordSection').hide();
            $('#loginForm').show();
        },
        error: function() {
            toastr.error('Failed to change password');
        }
    });
});




	});
