$(document).ready(function() {
	$('.dropdown-item').on('click', function(e) {
		e.preventDefault();
		const language = $(this).data('value'); // Use data-value attribute to get language
		localStorage.setItem('selectedLanguage', language);
		fetchTopics(language);
		console.log(language);
	});
});



function fetchTopics(language) {
	/*alert("Topics fetching....");*/
	$.ajax({
		type: "GET",
		contentType: "application/json",
		url: '/topics/' + language, // Correctly concatenate language parameter
		dataType: 'json',
		success: function(data) {
			console.log("Topic data:", data);

			var content = '';
			var pendingRequests = data.length;

			for (var i = 0; i < data.length; i++) {
				(function(topic) {
					$.ajax({
						type: "GET",
						contentType: "application/json",
						url: '/count/' + topic.topicId, // Correctly concatenate topicId parameter
						dataType: 'json',
						success: function(count) {
							console.log("count:", count);

							if (count > 0) {
								$.ajax({
									type: "GET",
									contentType: "application/json",
									url: '/sum/' + topic.topicId, // Correctly concatenate topicId parameter
									dataType: 'json',
									success: function(sum) {
										console.log("Sum of Marks :", sum);

										content += `
                                            <div class="col-md-12">
                                                <div class="card">
                                                    <div class="quiz_header" style="color:red">${topic.topicName}</div>
                                                    <div class="quiz_header">${topic.description}</div>
                                                    <p class="quiz_detail">
                                                        <span>Total Questions Count: </span>
                                                        <span>${count}</span>
                                                    </p>
                                                    <p class="quiz_detail">
                                                        <span>Marks: </span>
                                                        <span>${sum}</span>
                                                    </p>
                                                    <p class="quiz_detail">
                                                        <span>Duration (Minutes): </span>
                                                        <span>${topic.duration || '10'}</span>
                                                    </p>
                                                    <div class="quiz_footer">
                                                        <button class="quiz_button" id="showQuestions" data-topic-id="${topic.topicId}">Start Quiz</button>
                                                    </div>
                                                </div>
                                            </div>
                                        `;
									},
									error: function(error) {
										console.error("Error sum for topicId", topic.topicId, error);
									},
									complete: function() {
										pendingRequests--;
										if (pendingRequests === 0) {
											$('#topics-container').html(content); // Update container id to match your HTML
											$('#topics-container').trigger('destroy.owl.carousel');
											initializeOwlCarousel();
										}
									}
								});
							} else {
								pendingRequests--;
								if (pendingRequests === 0) {
									$('#topics-container').html(content); // Update container id to match your HTML
									$('#topics-container').trigger('destroy.owl.carousel');
									initializeOwlCarousel();
								}
							}
						},
						error: function(error) {
							console.error("Error count", error);
							pendingRequests--;
							if (pendingRequests === 0) {
								$('#topics-container').html(content); // Update container id to match your HTML
								$('#topics-container').trigger('destroy.owl.carousel');
								initializeOwlCarousel();
							}
						}
					});
				})(data[i]);
			}
		},
		error: function(error) {
			console.error("Error fetching topics", error);
		}
	});
}






function initializeOwlCarousel() {
	var itemCount = $('#topics-container .card').length; // Get the number of items

	$('#topics-container').owlCarousel({
		loop: false,
		margin: 10,
		nav: true,
		center: itemCount === 1, // Center the item if there is only one
		stagePadding: itemCount === 1 ? 100 : 0, // Add padding for single item
		responsive: {
			0: {
				items: 1
			},
			600: {
				items: itemCount > 1 ? 2 : 1 // Show 2 items if there are more than 1, else show 1
			},
			1000: {
				items: itemCount > 2 ? 3 : itemCount // Show up to 3 items, or the actual itemCount if less than 3
			}
		},
		navText: [
			"<button type='button' class='owl-prev ms-5'><i class='bi bi-chevron-compact-left'></i></button>",
			"<button type='button' class='owl-next'><i class='bi bi-chevron-compact-right'></i></button>"
		]
	});
}






function startQuiz(topicId) {
	// Add your logic to start the quiz based on the topicId
	console.log("Starting quiz for topic:", topicId);
}




$(document).on('click', '[id^="showQuestions"]', function() {
	/*alert("Clicked...");*/
	let topicId = $(this).data('topic-id');
	console.log("Topic ID is : " + topicId);

	const loggedInUser = localStorage.getItem('loggedInUser');
	const selectedLanguage = localStorage.getItem('selectedLanguage');
	console.log(loggedInUser);
	console.log(selectedLanguage);

	if (loggedInUser) {
		// User is logged in, redirect based on the selected language
		if (selectedLanguage === 'English') {
			window.location.href = '/exam?topicId=' + topicId;
		} else if (selectedLanguage === 'Marathi') {
			window.location.href = '/marathiPage?topicId=' + topicId;
		} else if (selectedLanguage === 'Hindi') {
			window.location.href = '/hindiPage?topicId=' + topicId;
		}
	} else {
		// User is not logged in, redirect to registration page
		window.location.href = '/registration';
	}
});




function getParameterByName(name) {
	const url = window.location.href;
	name = name.replace(/[\[\]]/g, '\\$&');
	const regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)');
	const results = regex.exec(url);
	if (!results) return null;
	if (!results[2]) return '';
	return decodeURIComponent(results[2].replace(/\+/g, ' '));
}

$(document).ready(function() {
	const topicId = getParameterByName('topicId');
	console.log("Topic ID is : " + topicId);

	// Set the topic ID as a data attribute on the #takeTest button
	$('#takeTest').data('topic-id', topicId);
});

$('#takeTest').on('click', function(e) {
	e.preventDefault();
	let topicId = $(this).data('topic-id');
	console.log("Topic ID is : " + topicId);

	window.location.href = '/examPage?topicId=' + topicId;

});

function getQuestions() {

	/**************************************************************/
	// Start the timer
	//	 startTimer(30, document.querySelector('#time'));

	// Show the timer display
	//	$('#timer').show();

	/***********************************************************/


	$.ajax({
		type: "GET",
		contentType: "application/json",
		url: "/questionsByTopicId/" + topicId + "/questions", // Update this URL to match your endpoint
		dataType: 'json',
		success: function(data) {
			console.log(topicId)
			console.log("Data Receiced :" + data);
			var content = '';
			for (var i = 0; i < data.length; i++) {
				content += `
                            <div class="card">
                                <div class="card-body">
                                    <p>
                                        <span>${data[i].queNum}</span>
                                        <span>${data[i].questions}</span>
                                    </p>
                                    <div class="basic-form">
                                        <form>
                                            <div class="form-group">
                                                <div class="radio mb-3">
                                                    <label>
                                                        <input type="radio" name="optradio${data[i].queNum}" value="${data[i].option1}">${data[i].option1}
                                                    </label>
                                                </div>
                                                <div class="radio mb-3">
                                                    <label>
                                                        <input type="radio" name="optradio${data[i].queNum}" value="${data[i].option2}">${data[i].option2}
                                                    </label>
                                                </div>
                                                <div class="radio mb-3">
                                                    <label>
                                                        <input type="radio" name="optradio${data[i].queNum}" value="${data[i].option3}">${data[i].option3}
                                                    </label>
                                                </div>
                                                <div class="radio mb-3">
                                                    <label>
                                                        <input type="radio" name="optradio${data[i].queNum}" value="${data[i].option4}">${data[i].option4}
                                                    </label>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        `;
			}
			content += '<button class="btn" id="endExam">End Exam</button>';
			$('#questions-container').html(content);





			$(document).on('click', '[id^="endExam"]', function() {
				alert("Clicked...");

				var score = 0;
				var attempted = 0;
				for (var i = 0; i < data.length; i++) {
					var selectedOption = $(`input[name="optradio${data[i].queNum}"]:checked`).val();
					if (selectedOption !== undefined) {
						attempted++;
						if (selectedOption === data[i].answer) {
							score++;
						}
					}
				}

				var percentage = (score / data.length) * 100;
				var result = percentage >= 50 ? "Pass" : "Fail";

				var examResults = {
					totalQuestions: data.length,
					correctAnswers: score,
					attemptedQuestions: attempted,
					result: result
				};
				console.log('Exam Results:', examResults); // Debugging statement

				try {
					localStorage.setItem('examResults', JSON.stringify(examResults));
					console.log('Results saved to localStorage'); // Debugging statement
				} catch (e) {
					console.error('Error saving to localStorage', e); // Debugging statement
				}

				// Redirect to report card page
				window.location.href = '/reportCard';
			});

		},
		error: function(error) {
			console.error("Error fetching questions", error);
		}
	});

};






