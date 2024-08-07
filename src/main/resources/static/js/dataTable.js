$(document).ready(function () {
    // Initialize DataTable once
    initializeDataTable();

    // Fetch results from the server
    fetchResults();
});

function initializeDataTable() {
    // Initialize DataTable with pagination
    $('.zero-configuration').DataTable({
        "paging": true,  // Enable pagination
        "searching": true, // Enable searching/filtering
        "info": true,  // Show table information
        "lengthChange": true // Enable changing number of records per page
    });
}

/*function fetchResults() {
    $.ajax({
        url: '/getAllResult',
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            console.log("Data is: ", data);
            var d = '';

            for (var i = 0; i < data.length; i++) {
                // Extract the date part from the timestamp
                var date = new Date(data[i].timestamp).toISOString().split('T')[0];

                d += '<tr class="table">' +
                    '<td>' + (i + 1) + '</td>';  // Serial number

                if (data[i].userRegistrationEntity) {
                    d += '<td>' + data[i].userRegistrationEntity.firstName + ' ' + data[i].userRegistrationEntity.lastName + '</td>';
                } else {
                    d += '<td>N/A</td>';
                }

                d += '<td>' + date + '</td>' +  // Display only the date part
                    '<td>' + (data[i].topicName || 'N/A') + '</td>' +
                    '<td>' + (data[i].topicDescription || 'N/A') + '</td>' +
                    '<td>' + '<button class="label gradient-4 rounded" data-bs-toggle="modal" data-bs-target="#ResultTableModal" data-table-id="' + data[i].id + '" id="showResultTable">Click Here</button>' + '</td>' +  // Assuming 'showMarks' is part of the data
                    '<td>' + data[i].result + '</td>' +
                    '<td></td>' +  // Placeholder for action column
                    '</tr>';
            }

            // Clear existing table data and populate new data
            var table = $('.zero-configuration').DataTable();
            table.clear().draw();  // Clear the table
            table.rows.add($(d)).draw();  // Add new data and redraw the table
        },
        error: function (err) {
            console.error('Error fetching results', err);
        }
    });
}*/

function fetchResults() {
    $.ajax({
        url: '/getAllResult',
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            console.log("Data is: ", data);

            // Sort data by timestamp in descending order
            data.sort(function(a, b) {
                return new Date(b.timestamp) - new Date(a.timestamp);
            });

            var d = '';

            for (var i = 0; i < data.length; i++) {
                // Extract the date part from the timestamp
                var date = new Date(data[i].timestamp).toISOString().split('T')[0];

                d += '<tr class="table">' +
                    '<td>' + (i + 1) + '</td>';  // Serial number

                if (data[i].userRegistrationEntity) {
                    d += '<td>' + data[i].userRegistrationEntity.firstName + ' ' + data[i].userRegistrationEntity.lastName + '</td>';
                } else {
                    d += '<td>N/A</td>';
                }

                d += '<td>' + date + '</td>' +  // Display only the date part
                    '<td>' + (data[i].topicName || 'N/A') + '</td>' +
                    '<td>' + (data[i].topicDescription || 'N/A') + '</td>' +
                    '<td>' + '<button class="label gradient-4 rounded" data-bs-toggle="modal" data-bs-target="#ResultTableModal" data-table-id="' + data[i].id + '" id="showResultTable">Click Here</button>' + '</td>' +  // Assuming 'showMarks' is part of the data
                    '<td>' + data[i].result + '</td>' +
                    '<td></td>' +  // Placeholder for action column
                    '</tr>';
            }

            // Clear existing table data and populate new data
            var table = $('.zero-configuration').DataTable();
            table.clear().draw();  // Clear the table
            table.rows.add($(d)).draw();  // Add new data and redraw the table
        },
        error: function (err) {
            console.error('Error fetching results', err);
        }
    });
}


$(document).on('click', '[id^="showResultTable"]', function () {
    alert("Do you want to show Result?");

    let examResultId = $(this).data('table-id');
    console.log("Result ID is: " + examResultId);

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/ExamResultById/" + examResultId,
        dataType: 'json',
        success: function (data) {
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
        error: function () {
            alert("Error loading data...");
        }
    });
});



/*$(document).ready(function() {
            $('.zero-configuration').DataTable({
                // Optional configurations here
            });
        });
$(document).ready(function () {
			alert("Loading...");
			fetchResults();
		});

		function fetchResults() {
			alert("Data loading...");
			$.ajax({
				url: '/getAllResult',
				method: 'GET',
				dataType: 'json',
				success: function (data) {
					console.log("Data is: ", data);
					var d = '';

					for (var i = 0; i < data.length; i++) {
						// Extract the date part from the timestamp
						var date = new Date(data[i].timestamp).toISOString().split('T')[0];

						d += '<tr class="table">' +
							'<td>' + (i + 1) + '</td>';  // Serial number

						if (data[i].userRegistrationEntity) {
							d += '<td>' + data[i].userRegistrationEntity.firstName + ' ' + data[i].userRegistrationEntity.lastName + '</td>';
						} else {
							d += '<td>N/A</td>';
						}

						d += '<td>' + date + '</td>' +  // Display only the date part
							'<td>' + (data[i].topicName || 'N/A') + '</td>' +
							'<td>' + (data[i].topicDescription || 'N/A') + '</td>' +
							'<td>' + '<button class="label gradient-4 rounded" data-bs-toggle="modal" data-bs-target="#ResultTableModal" data-table-id="' + data[i].id + '" id="showResultTable">Click Here</button>' + '</td>' +  // Assuming 'showMarks' is part of the data
							'<td>' + data[i].result + '</td>' +
							'<td></td>' +  // Placeholder for action column
							'</tr>';
					}

					$('#datatable').html(d);
				},
				error: function (err) {
					console.error('Error fetching results', err);
				}
			});
		}

		$(document).on('click', '[id^="showResultTable"]', function () {
			alert("Do you want to show Result?");

			let examResultId = $(this).data('table-id');
			console.log("Result ID is: " + examResultId);

			$.ajax({
				type: "GET",
				contentType: "application/json",
				url: "/ExamResultById/" + examResultId,
				dataType: 'json',
				success: function (data) {
					console.log(data);
					var d = '<tr>' +
						'<td>' + data.totalQuestions + '</td>' +
						'<td>' + data.correctAnswers + '</td>' +
						'<td>' + data.attemptedQuestions + '</td>' +
						'<td>' + data.percentage + '</td>' +
						'<td>' + data.result + '</td>' +
						'</tr>';
					$('#attemptsResultTable').html(d);
					
					// Initialize or reinitialize DataTable
			if ($.fn.DataTable.isDataTable('.zero-configuration')) {
				$('.zero-configuration').DataTable().clear().destroy();
			}
			$('.zero-configuration').DataTable();
				},
				error: function () {
					alert("Error loading data...");
				}
			});
		});

*/