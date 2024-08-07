$(document).ready(function() {
	const loggedInUser = JSON.parse(localStorage.getItem('loggedInUser'));
	console.log(loggedInUser);
	if (loggedInUser && loggedInUser.id) {
		fetchAttemptedDetails(loggedInUser.id);
	} else {
		console.error("No logged in user found");
	}
});

/*function fetchAttemptedDetails(userId) {
	$.ajax({
		type: "GET",
		contentType: "application/json",
		url: "/usersAttemptedExams/" + userId,
		success: function(data) {
			console.log("data is :", data);
			var d = '';

			for (var i = 0; i < data.length; i++) {
					// Extract the date part from the timestamp
				var timestamp = data[i].timestamp;
				var date = timestamp.split('T')[0];

				console.log("date is : ",date);
				d += '<tr>' +
					'<td>' + date + '</td>';

				if (data[i].topic) {
					d += '<td>' + data[i].topic.topicName + '</td>' +
						'<td>' + data[i].topic.description + '</td>';
				} else {
					d += '<td>N/A</td><td>N/A</td>';
				}

				d += '<td>' +
					'<button class="label gradient-4 rounded" data-bs-toggle="modal" data-bs-target="#ResultTableModal" data-table-id="' + data[i].id + '" id="showResultTable">Click Here</button>' +
					'</td></tr>';
			}
			$('#attemptsTable').html(d);
		},
		error: function(error) {
			console.error("Error fetching topic details:", error);
		}
	});
}*/

function fetchAttemptedDetails(userId) {
	$.ajax({
		type: "GET",
		contentType: "application/json",
		url: "/usersAttemptedExams/" + userId,
		success: function(data) {
			console.log("data is :", data);
			var d = '';

			for (var i = 0; i < data.length; i++) {
				// Extract the date part from the timestamp
				var timestamp = data[i].timestamp;
				var date = timestamp.split('T')[0];

				d += '<tr>' +
					'<td>' + date + '</td>';

				// Check if topic is available, else fetch from exam results
				if (data[i].topic) {
					d += '<td>' + data[i].topic.topicName + '</td>' +
						'<td>' + data[i].topic.description + '</td>';
				} else if (data[i].topicName && data[i].topicDescription) {
					d += '<td>' + data[i].topicName + '</td>' +
						'<td>' + data[i].topicDescription + '</td>';
				} else {
					d += '<td>N/A</td><td>N/A</td>';
				}

				d += '<td>' +
					'<button class="label gradient-4 rounded" data-bs-toggle="modal" data-bs-target="#ResultTableModal" data-table-id="' + data[i].id + '" id="showResultTable">Click Here</button>' +
					'</td></tr>';
			}
			$('#attemptsTable').html(d);

			// Initialize or reinitialize DataTable
			if ($.fn.DataTable.isDataTable('.zero-configuration')) {
				$('.zero-configuration').DataTable().clear().destroy();
			}
			$('.zero-configuration').DataTable();
		},
		error: function(error) {
			console.error("Error fetching topic details:", error);
		}
	});
}

$(document).on('click', '[id^="showResultTable"]', function() {
	alert("Do you want to show Result?");

	let examResultId = $(this).data('table-id');
	console.log("Result ID is: " + examResultId);

	$.ajax({
		type: "GET",
		contentType: "application/json",
		url: "/ExamResultById/" + examResultId,
		dataType: 'json',
		success: function(data) {
			console.log(data);
			var d = '<tr>' +
				'<td>' + data.totalQuestions + '</td>' +
				'<td>' + data.correctAnswers + '</td>' +
				'<td>' + data.attemptedQuestions + '</td>' +
				'<td>' + data.percentage + '</td>' +
				'<td>' + data.result + '</td>' +
				'</tr>';
			$('#attemptsResultTable').html(d);
		},
		error: function() {
			alert("Error loading data...");
		}
	});
});
