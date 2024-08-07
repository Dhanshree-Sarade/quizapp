

$('#uploadLink').click(function(e) {

	//alert("Clicked...");
	e.preventDefault(); // Prevent default anchor tag behavior
	$.ajax({
		type: "GET",
		url: "/upload", // Your controller endpoint for redirection
		success: function() {
			// Success callback: Redirected successfully
		},
		error: function() {
			// Error callback: Handle error if redirection fails
		}
	});
});






$(document).ready(function() {

	showCategory();

	//clear form field on page load
	$('#exampleInputTopic1').val();
	$('#exampleInputDescription1').val();
	$('#exampleInputDuration1').val();
	$('#exampleInputLanguage1').val();

	$('.dropdown-item').click(function(e) {
		e.preventDefault(); // Prevent the default anchor click behavior
		var selectedLanguage = $(this).data('value');
		$('#exampleInputLanguage1').val(selectedLanguage);
		$('#addlanguageDropdownButton').text(selectedLanguage);
		$('#languageError').text('');
	});

});


/*this is working function*/
/*$(document).ready(function(){
	
	 $('#formData').on('submit', function(e){
				e.preventDefault();
				let isValid = true;
				if (!$('#exampleInputTopic1').val()) {
					alert("Please fill out the topic name.");
					isValid = false;
				}
				if (!$('#exampleInputDescription1').val()) {
					alert("Please fill out the description.");
					isValid = false;
				}
				if (!$('#exampleInputDuration1').val()) {
					alert("Please fill out the description.");
					isValid = false;
				}
				if (!$('#exampleInputLanguage1').val()) {
					$('#languageError').text('Please select a language.');
					isValid = false;
				}

				if (isValid) {
					let topicName = $('#exampleInputTopic1').val();
					let description = $('#exampleInputDescription1').val();
					let duration = $('#exampleInputDuration1').val();
					let language = $('#exampleInputLanguage1').val();
					 let status = false; 
					 
					console.log(topicName);
					console.log(description);
					console.log(duration);
					console.log(language);
				    
				  let details = {
						topicName : topicName,
						description : description,
						durationString : duration,
						language : language,
						status: status
					};
				    
					console.log("Topic Details are : ", details);
				    
				    
					$.ajax({
						type: "POST",
						contentType: "application/json",
						url: '/addCategory',
						data: JSON.stringify(details),
						dataType: 'json',
						success: function(response) {
							console.log('Topic saved successfully!');
							toastr.success('Topic saved successfully!');
							// Perform any additional actions here, such as closing the modal or updating the UI
						},
						error: function(error) {
							console.error(error);
							toastr.error('Topic not saved. Please try again.'); // Displaying the error message using toastr
						}
					});
				}
			});
		});

*/

$(document).ready(function() {
	$('#formData').on('submit', function(e) {
		e.preventDefault();

		let isValid = true;
		if (!$('#exampleInputTopic1').val()) {
			alert("Please fill out the topic name.");
			isValid = false;
		}
		if (!$('#exampleInputDescription1').val()) {
			alert("Please fill out the description.");
			isValid = false;
		}
		if (!$('#exampleInputDuration1').val()) {
			alert("Please fill out the duration.");
			isValid = false;
		} else {
			const durationRegex = /^\d{2}:\d{2}:\d{2}$/;
			if (!durationRegex.test($('#exampleInputDuration1').val())) {
				alert("Invalid duration format. Please use hh:mm:ss.");
				isValid = false;
			}
		}
		if (!$('#exampleInputLanguage1').val()) {
			$('#languageError').text('Please select a language.');
			isValid = false;
		}

		if (isValid) {
			let topicName = $('#exampleInputTopic1').val();
			let description = $('#exampleInputDescription1').val();
			let durationString = $('#exampleInputDuration1').val();
			let language = $('#exampleInputLanguage1').val();
			let instituteName = $('#exampleInputInstituteName1').val();
			let status = false;

			let formData = new FormData();
			formData.append('instituteName', instituteName);
			formData.append('topicName', topicName);
			formData.append('description', description);
			formData.append('durationString', durationString);
			formData.append('language', language);
			formData.append('status', status);

			console.log("Form Data being sent:", [...formData.entries()]); // Log form data to inspect what is being sent

			$.ajax({
				type: "POST",
				url: '/addCategory',
				data: formData,
				processData: false,
				contentType: false,
				success: function(response) {
					console.log('Topic saved successfully!');
					toastr.success('Topic saved successfully!');
					// Perform any additional actions here, such as closing the modal or updating the UI
					showCategory();
					$('#formData')[0].reset(); // Reset the form fields
				},
				error: function(error) {
					console.error("Error response:", error);
					toastr.error('Topic not saved. Please try again.'); // Displaying the error message using toastr
				}
			});
		}
	});
});



//$(document).ready(function(){
//alert("Load All Data....");
showCategory();

//});

function showCategory() {

	//clear form field on page load
	$('#exampleInputInstituteName1').val();
	$('#exampleInputTopic1').val();
	$('#exampleInputDescription1').val();
	$('#exampleInputDuration1').val();
	$('#exampleInputLanguage1').val();
	//alert("Table is loading...");
	$.ajax({
		type: "GET",
		contentType: "application/json",
		url: '/getCategory',
		dataType: 'json',
		success: function(data) {



			var d = '';

			for (var i = 0; i < data.length; i++) {

				console.log("show data" + data)

				var statusText;
				if (data[i].questions == null || data[i].questions.length === 0) {
					statusText = '<button class="btn mb-1 btn-rounded btn-warning btn-rounded gradient-8" data-bs-toggle="modal" data-bs-target="#uploadFile" data-excel-id="' + data[i].topicId + '" id="openUploadModal-' + data[i].topicId + '"><span class="btn-icon-center"><i class="bi bi-cloud-arrow-up"></i></span></button>';
				} else {
					statusText = 'Uploaded';
				}

				d += '<tr>' +

					'<td > ' + data[i].topicId + '</td>' +
					'<td > ' + data[i].instituteName + '</td>' +
					'<td > ' + data[i].topicName + '</td>' +
					'<td > ' + data[i].description + '</td>' +
					'<td > ' + data[i].duration + '</td>' +
					'<td > ' + data[i].language + '</td>' +
					'<td > ' + statusText + '</td>' +
					'<td > ' + '<button class="label gradient-4 rounded" data-bs-toggle="modal" data-bs-target="#tableModal" data-table-id="' + data[i].topicId + '" id="showTable"> Click Here </button>' + '</td>' +
					/*'<td>' + '<button data-bs-toggle="modal" data-bs-target="#editModal" data-table-id="' + data[i].topicId + '"  id="editTopicQuestions-' + data[i].topicId + '"><i class="bi bi-pencil-square"></i></button>'  + 
					'<button   data-table-id="' + data[i].topicId + '" id="deleteBtn-' + data[i].topicId + '"><i class="bi bi-trash"></i></button>'  + '</td>' +
					*/
					'<td > ' + ' <button type="button" type="button" class="btn mb-1 btn-rounded btn-warning gradient-2" data-bs-toggle="modal" data-bs-target="#editModal" data-table-id="' + data[i].topicId + '"  id="editTopicQuestions-' + data[i].topicId + '"><span class="btn-icon-center"><i class="bi bi-pencil-square"></i></span></button>' +
					' <button type="button" class="btn mb-1 btn-danger gradient-3" data-table-id="' + data[i].topicId + '" id="deleteBtn-' + data[i].topicId + '"> <span class="btn-icon-center"><i class="bi bi-trash"></i></span></button>' + '</td>' +

					'</tr >'
			}

			$('#table').html(d);

		},
		error: function() {
			alert("Error loading data...");
		}
	});
}

$(document).on('click', '[id^="openUploadModal-"]', function() {

	alert("Do you want to Upload Excel ?")
	let topicId = $(this).data('excel-id');
	console.log("Topic ID is : " + topicId);

	$.ajax({
		type: "GET",
		contentType: "application/json",
		url: "/topicById/" + topicId,
		dataType: 'json',
		success: function(data) {
			if (data) {
				$("#topicIdInput").val(data.topicId);
			}
		},
		error: function(e) {
			console.log("Error in feching data for Id....");
		}

	});
});




$(document).on('click', '[id^="showTable"]', function() {
    alert("Do you want to show Questions Set ?");

    let topicId = $(this).data('table-id');
    console.log("Topic ID is: " + topicId);

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/questionsByTopicId/" + topicId + "/questions",
        dataType: 'json',
        success: function(data) {
            console.log(data);
            var d = '';
            for (var i = 0; i < data.length; i++) {
                d += '<tr>' +
                    // Uncomment this if you want to include queId
                    // '<td>' + escapeHtml(data[i].queId) + '</td>' +
                    '<td>' + escapeHtml(data[i].queNum) + '</td>' +
                    '<td>' + escapeHtml(data[i].questions) + '</td>' +
                    '<td>' + escapeHtml(data[i].option1) + '</td>' +
                    '<td>' + escapeHtml(data[i].option2) + '</td>' +
                    '<td>' + escapeHtml(data[i].option3) + '</td>' +
                    '<td>' + escapeHtml(data[i].option4) + '</td>' +
                    '<td>' + escapeHtml(data[i].answer) + '</td>' +
                    '<td>' + escapeHtml(data[i].marks) + '</td>' +
                    '</tr>';
            }
            // Use .html() here to insert the complete table row content
            $('#QuestionsTable').html(d);
        },
        error: function(xhr, status, error) {
            console.error("Error loading data:", status, error);
            alert("Error loading data...");
        }
    });
});

function escapeHtml(unsafe) {
    if (unsafe == null) return '';
    return unsafe
        .toString() // Ensure the input is a string
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}


/*//this is correct code to upload only excel sheet 
$(document).ready(function() {
	$('#uploadFileForm').submit(function(event) {
		event.preventDefault(); // Prevent default form submission
	    
		var formData = new FormData(this); // Create FormData object from form data
	    
		$.ajax({
			url: '/uploadExcel', // Your backend endpoint URL
			type: 'POST',
			data: formData,
			processData: false,
			contentType: false,
			success: function(response) {
				console.log('File uploaded successfully:', response);
				// Handle success response
				alert("File uploaded successfully");
			},
			error: function(xhr, status, error) {
				console.error('Error uploading file:', error);
				// Handle error response
				 alert("File not uploaded...");
			}
		});
	});
});
*/

$(document).ready(function() {
	$('#uploadFileForm').submit(function(event) {
		event.preventDefault(); // Prevent the form from submitting normally

		var formData = new FormData();
		var file = $('#fileInput')[0].files[0];
		var topicId = $('#topicIdInput').val();
		console.log("topicId is : " + topicId);

		formData.append('file', file);

		$.ajax({
			// Update the URL to include the topicId as a path variable
			url: '/uploadExcel/' + topicId,
			type: 'POST',
			data: formData,
			contentType: false,
			processData: false,
			success: function(response) {
				console.log(response);
				toastr.success('File uploaded successfully...');
				showCategory();
			},
			error: function(xhr, status, error) {
				console.error(xhr.responseText);
				alert('Failed to upload file: ' + xhr.responseText);
			}
		});
	});
});



$(document).on('click', '[id^="deleteBtn-"]', function() {

	alert("Do you want to delete record.....");
	let topicId = $(this).data('table-id');
	console.log("topic ID is : " + topicId);

	$.ajax({
		type: "DELETE",
		contentType: "application/json",
		url: '/deleteId/' + topicId,
		success: function(response) {
			toastr.success('Deleted successfully!');
			showCategory();
		},
		error: function(e) {
			alert("Details not deleted...");
		}
	});

});





$(document).on('click', '[id^="editTopicQuestions-"]', function() {
	alert("Do you want to edit record.....");
	let topicId = $(this).data('table-id');
	console.log("Topic ID is : " + topicId);

	$.ajax({
		type: "GET",
		contentType: "application/json",
		url: "/topicById/" + topicId,
		dataType: 'json',
		success: function(data) {
			if (data) {
				$("#topicId").val(data.topicId);
				$("#instituteName").val(data.instituteName);
				$("#topicName").val(data.topicName);
				$("#description").val(data.description);
				$("#duration").val(data.duration);
				$("#editInputLanguage1").val(data.language);
			}
		},
		error: function(e) {
			console.log("Error in feching data for Id....");
		}
	});
});

$(document).ready(function() {
	// Handle language selection from the dropdown
	$('.dropdown-item').on('click', function(e) {
		e.preventDefault(); // Prevent the default anchor click behavior
		var selectedLanguage = $(this).data('value');
		$('#editInputLanguage1').val(selectedLanguage);
		$('#editlanguageDropdownButton').text(selectedLanguage);
	});

	$('#UpdatedTopicData').on('click', function(e) {
		e.preventDefault();
		alert("Data updated....");
		let form = $('#editTopicForm')[0];
		let data = new FormData(form);

		let language = $('#editInputLanguage1').val(); // Get the value from the hidden input

		let topic = {
			topicId: $("#topicId").val(),
			 instituteName: $("#instituteName").val(),
			topicName: $("#topicName").val(),
			description: $('#description').val(),
			durationString: $('#duration').val(),
			language: language,
		};

		console.log(topic);

		let formData = new FormData();
		formData.append("topicId", topic.topicId);
		formData.append("instituteName", topic.instituteName);
		formData.append("topicName", topic.topicName);
		formData.append("description", topic.description);
		formData.append("durationString", topic.durationString);
		formData.append("language", topic.language);

		let status = false;

		let file = $('#fileUpdate')[0].files[0];
		if (file) {
			// If file is present, set status to true
			status = true;
			formData.append("file", file);
		}
		// First, update topic details
		$.ajax({
			type: "PUT",
			url: "/editDetails",
			data: formData,
			processData: false,
			contentType: false,
			success: function(response) {
				console.log("Topic details updated successfully", response);
				showCategory();

				// Then, upload the Excel file if present
				/*let file = $('#fileUpdate')[0].files[0];
				if (file) {
					let formData = new FormData();
					formData.append("file", file);
					$.ajax({
						type: "PUT",
						url: topic.topicId + "/uploadExcel",
						data: formData,
						processData: false,
						contentType: false,
						success: function(response) {
							console.log("Excel file uploaded successfully", response);
						},
						error: function(err) {
							console.error("Error uploading Excel file", err);
						}
					});
				}*/

				let file = $('#fileUpdate')[0].files[0];
				if (file) {
					// Delete the old Excel file
					$.ajax({
						type: "DELETE",
						url: '/deleteByTopic/' + topic.topicId,
						success: function(response) {
							console.log("Old Excel file deleted successfully", response);

							// Upload the new Excel file
							let formData = new FormData();
							formData.append("file", file);
							status = true;

							$.ajax({
								type: "PUT",
								url: topic.topicId + "/uploadExcel",
								data: formData,
								processData: false,
								contentType: false,
								success: function(response) {
									console.log("Excel file uploaded successfully", response);
									showCategory();
								},
								error: function(err) {
									console.error("Error uploading Excel file", err);
								}
							});
						},
						error: function(err) {
							console.error("Error deleting old Excel file", err);
						}
					});
				}

			},
			error: function(err) {
				console.error("Error updating topic details", err);
			}
		});
	})

});

/*$('#fileUpdate').on('change', function(e) {
	e.preventDefault();
	console.log("Do you want to delete old Questions ???");
	
	 let topicId =  $("#topicId").val();
	console.log("topic ID is : " + topicId);
	
	$.ajax({
				url: '/deleteByTopic/' + topicId,
				type: 'DELETE',
				success: function(response) {
					console.log("Old Questions are deleted.....",response);
				},
				error: function(xhr, status, error) {
					console.log('Failed to delete questions: ' + xhr.responseText);
				}
			});
	
	});*/


/*$(document).ready(function() {
	$('#uploadFileForm').submit(function(event) {
		event.preventDefault(); // Prevent the form from submitting normally
	    
		var formData = new FormData();
		var file = $('#fileInput')[0].files[0];
		var topicId = $('#topicIdInput').val();

		formData.append('file', file);
		formData.append('topicId', topicId);

		$.ajax({
			url: '/uploadExcel/'+ topicId,
			type: 'POST',
			data: formData,
			contentType: false,
			processData: false,
			success: function(response) {
				console.log(response);
				alert('File uploaded successfully!');
			},
			error: function(xhr, status, error) {
				console.error(xhr.responseText);
				alert('Failed to upload file: ' + xhr.responseText);
			}
		});
	});
});*/

















/*function init() {
	 //Your initialization logic here
	alert("Load All Data....");
	showCategory();
}

// Usage of 'init' method (similar to $(document).ready())
init();*/


/*function showTable(){
	$.ajax({
				type: "GET",
				contentType: "application/json",
				url: '/questions',
				dataType: 'json',
				success: function (data) {

				console.log(data)

				var d = '';

				for (var i = 0; i < data.length; i++) {

				d += '<tr >' +

				'<td > ' + data[i].queId + '</td>' +
				'<td > ' + data[i].questions + '</td>' +
				'<td > ' + data[i].option1 + '</td>' +
				'<td > ' + data[i].option2 + '</td>' +
				'<td > ' + data[i].option3 + '</td>' +
				'<td > ' + data[i].option4 + '</td>' +
				'<td > ' + data[i].answer + '</td>' +
				'<td>' + '<button data-bs-toggle="modal" data-bs-target="#exampleModal1" data-student-id="' + data[i].id + '"  id="editBtn-' + data[i].id + '"><i class="bi bi-pencil-square"></i></button>'  + 
				'<button   data-student-id="' + data[i].id + '" id="deleteBtn-' + data[i].id + '"><i class="bi bi-trash"></i></button>'  + '</td>' +
				
				'</tr >'
			 }

			$('#table').html(d); 
		},
		error: function () {
			alert("Error loading data...");
		}
	});

}*/

