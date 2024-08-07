/*$(document).ready(function(){
$("#registration").on('submit', function(e){
    e.preventDefault();
    alert("hello...");

    let details = {
        firstName: $("input[name='firstName']").val(),
        lastName: $("input[name='lastName']").val(),
        email: $("input[name='email']").val(),
        password: $("input[name='password']").val(),
        confirmPassword: $("input[name='confirmPassword']").val()
    };

	console.log(details);

	$.ajax({
        type: "POST",
        contentType: "application/json",
        url: '/saveRegistration', // Update the URL to match the controller mapping
        data: JSON.stringify(details),
        dataType: 'json',
        success: function(response) {
			if (response.redirectUrl) {
                window.location.href = response.redirectUrl;
            } else {
                 alert("Registrtion Done Successfully...");
            }
               
        },
        error: function(e) {
            alert("Registrtion Not Done...");
        }
    }); 
    })
    });*/
    
    
    
    
    
    
    $(document).ready(function() {
    $("#registrationForm").on('submit', function(e) {
        e.preventDefault();
        
		const emailInput = document.getElementById('emailInput').value;
		const feedbackElement = document.getElementById('emailFeedback');
		const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

		if (emailRegex.test(emailInput)) {
			feedbackElement.textContent = ""; // Email is valid
		} else {
			feedbackElement.textContent = "Please enter a valid email address.";
		}
        
        let password = $("input[name='password']").val();
        let confirmPassword = $("input[name='confirmPassword']").val();

        // Password validation regex
        let passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
        
        
		const mobileNumber = document.getElementById('mobileInput').value.trim();
		const feedbackElementMob = document.getElementById('mobileFeedback');
		// Regex pattern for 10-digit phone number
		const mobileRegex = /^\d{10}$/;
		
		if (mobileRegex.test(mobileNumber)) {
			feedbackElementMob.textContent = ""; // Mobile number is valid
		} else {
			feedbackElementMob.textContent = "Please enter a valid mobile number.";
		}
        

        // Check if passwords match
        if (password !== confirmPassword) {
            toastr.error("Passwords do not match. Please try again.");
            return; // Prevent form submission
        }

        // Check if password meets complexity requirements
        if (!passwordRegex.test(password)) {
           /* alert("Password must be at least 8 characters long, include at least one uppercase letter, one lowercase letter, one number, and one special character.");
         */  
         toastr.error("Password must be at least 8 characters long, include at least one uppercase letter, one lowercase letter, one number, and one special character.");
         
		 return; // Prevent form submission
        }

        if (this.checkValidity() === false) {
            toastr.error("Please fill out all required fields correctly.");
        } else {
			
			var firstName = $("input[name='firstName']").val();
			var lastName = $("input[name='lastName']").val();
			var email= $("input[name='email']").val();
			var mobileNum= $("input[name='mobile']").val();
			
            let details = {
                firstName: firstName,
                lastName: lastName,
                email: email,
                mobileNum: mobileNum,
                password: password,
                confirmPassword: confirmPassword
            };
            console.log(details);

			var userDetails = new FormData();
			userDetails.append('firstName', firstName);
			userDetails.append('lastName', lastName);
			userDetails.append('email', email);
			userDetails.append('mobileNum', mobileNum);
			userDetails.append('password', password);
			userDetails.append('confirmPassword', confirmPassword);
			
			console.log("Form Data being sent:", [...userDetails.entries()]);
			
/*            $.ajax({
			 type: "POST",
                url: '/saveRegistration',
                data: userDetails,
                processData: false,
                contentType: false,
                success: function(response) {
					console.log(response);
                   try {
                        var jsonResponse = JSON.parse(response);
                        if (jsonResponse.redirectUrl) {
							toastr.success("Registration Done Successfully...");
                            window.location.href = jsonResponse.redirectUrl;
                        } else {
                            toastr.success("Registration Done Successfully...");
                        }
                    } catch (e) {
                        console.error("Parsing error:", e);
                        toastr.success("Registration Done Successfully...");
                    }
                },
                error: function(e) {
                    if (e.status === 409) {
                        toastr.error("Email already exists. Then Login with existing username & password.");
                    } else {
                        toastr.error("Registration Not Done...");
                    }
                }
            });*/
            
            $.ajax({
    type: "POST",
    url: '/saveRegistration',
    data: userDetails,
    processData: false,
    contentType: false,
    success: function(response) {
        console.log(response);
        try {
            var jsonResponse = JSON.parse(response);
            if (jsonResponse.redirectUrl) {
                toastr.success("Registration Done Successfully...");
                // Delay the redirection to show the toastr message
                setTimeout(function() {
                    window.location.href = jsonResponse.redirectUrl;
                }, 3000); // Adjust the delay time as needed (3000ms = 3 seconds)
            } else {
                toastr.success("Registration Done Successfully...");
            }
        } catch (e) {
            console.error("Parsing error:", e);
            toastr.success("Registration Done Successfully...");
        }
    },
    error: function(e) {
        if (e.status === 409) {
            toastr.error("Email already exists. Then Login with existing username & password.");
        } else {
            toastr.error("Registration Not Done...");
        }
    }
});

        }
    });
});


/*$(document).ready(function() {
    // When "Get OTP" button is clicked
    $('#getOtpButton').click(function() {
        var email = $('#emailInput').val();

        if (email) {
            // Hide the "Get OTP" button
            $('#getOtpButton').hide();

            // Show the OTP verification section
            $('#otpVerificationSection').show();
        } else {
            $('#emailFeedback').text('Please enter a valid email address.');
        }
    });

    // Optional: Handle OTP verification button click
    $('#verifyOtpButton').click(function() {
        var otp = $('#otpInput').val();

        if (otp) {
            // Just a placeholder action
            alert('OTP Verified!'); // Replace with your actual logic
        } else {
            $('#emailFeedback').text('Please enter the OTP.');
        }
    });
});
*/

$(document).ready(function() {
    // When "Get OTP" button is clicked
    $('#getOtpButton').click(function() {
        var email = $('#emailInput').val();

        if (email) {
            // Hide the "Get OTP" button
            $('#getOtpButton').hide();

            // Show the OTP verification section
            $('#otpVerificationSection').show();

            // Send OTP request to the server
            $.ajax({
                url: '/sendOtp',
                type: 'POST',
                data: { email: email },
                success: function(response) {
                    toastr.success(response);
                },
                error: function(xhr, status, error) {
                    toastr.error(xhr.responseText);
                    $('#getOtpButton').show();
                    $('#otpVerificationSection').hide();
                }
            });
        } else {
            $('#emailFeedback').text('Please enter a valid email address.');
        }
    });

    // Handle OTP verification button click
    $('#verifyOtpButton').click(function() {
        var otp = $('#otpInput').val();
        var email = $('#emailInput').val();

        if (otp) {
            // Send OTP verification request to the server
            $.ajax({
                url: '/verifyOtp',
                type: 'POST',
                data: { email: email, otp: otp },
                success: function(response) {
                    toastr.success(response);
                },
                error: function(xhr, status, error) {
                    toastr.error(xhr.responseText);
                }
            });
        } else {
            $('#emailFeedback').text('Please enter the OTP.');
        }
    });
});


