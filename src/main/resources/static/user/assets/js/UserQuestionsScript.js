// const loggedInUser = localStorage.getItem('loggedInUser');
  //  console.log(loggedInUser);
    
    var loggedInUser = JSON.parse(localStorage.getItem('loggedInUser'));
	console.log('Logged In User:', loggedInUser);
	
	

	//var topicData = JSON.parse(localStorage.getItem('topicData'));
   // console.log('Topic Data:', topicData);
    //console.log('Topic Name:', topicData.topicName);
	//console.log('Topic Description:', topicData.description);
	
//	topicData.forEach(item => {
//    console.log('Topic Name:', item.topicName);
//    console.log('Topic Description:', item.description);
//	});

    
    let totalQuestions = $("#totalQuestions").val();
	let score = $("#correctAnswers").val();
	let attempted = $("#attemptedQuestions").val();
	let percentage = $("#percentage").val();
	let result = $("#result").val();
	


$(document).ready(function() {
   // console.log("Exam Page Ready");
    var topicId = getParameterByName('topicId');
    console.log("Topic ID is : " + topicId);

	 const loggedInUser = JSON.parse(localStorage.getItem('loggedInUser'));
    console.log(loggedInUser);
    if (loggedInUser && loggedInUser.id) {
  			fetchUserDetails(loggedInUser.id);
      } else {
        console.error("No logged in user found");
    }

    if (topicId) {
		fetchTopicDetails(topicId);
        fetchQuestions(topicId);
       // startTimer(60, document.querySelector('#time'));
    } else {
        console.error("No topicId found in URL");
    }
});

function fetchTopicDetails(topicId) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/topicById/" + topicId,
        success: function(data) {
            console.log("Topic Details:", data);
            $('#instituteName').text(data.instituteName);
            $('#topicName').text(data.topicName);
            $('#description').text(data.description);
        },
        error: function(error) {
            console.error("Error fetching topic details:", error);
        }
    });
}

function fetchUserDetails(id) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/getUserById/" + id,
        success: function(data) {
            console.log("User Details:", data);
            $('#candidateName').text(data.firstName + ' ' + data.lastName);
           
        },
        error: function(error) {
            console.error("Error fetching User details:", error);
        }
    });
}

function getParameterByName(name) {
    const url = window.location.href;
    name = name.replace(/[\[\]]/g, '\\$&');
    const regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)');
    const results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}

function fetchQuestions(topicId) {
    console.log("Fetching questions for topic ID: " + topicId);
    
    // Fetch topic details to get the duration
    $.ajax({
        type: "GET",
        url: `/topicById/${topicId}`,
        data: { topicId: topicId },
        success: function(topicResponse) {
            console.log("Topic details fetched successfully:", topicResponse);
            if (!topicResponse || !topicResponse.duration) {
                console.error("No topic details found or duration is missing.");
                return;
            }

            const durationString = topicResponse.duration;
            const timeParts = durationString.split(':');
            const durationInSeconds = (+timeParts[0]) * 60 * 60 + (+timeParts[1]) * 60 + (+timeParts[2]);

            // Start the timer with the fetched duration
            startTimer(durationInSeconds, document.querySelector('#time'));

            // Fetch questions for the topic
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: `/questionsByTopicId/${topicId}/questions`,
                dataType: 'json',
                success: function(data) {
                    console.log("Data Received: ", data);
                    renderQuestions(data);
                    calculateTotalMarks(data); // Call to calculate marks

                    // Event listener for ending the exam
                    $(document).on('click', '[id^="endExam"]', function() {
                        Swal.fire({
                            title: 'Are you sure?',
                            text: "Do you want to end the exam?",
                            icon: 'warning',
                            showCancelButton: false,
                            confirmButtonColor: '#3085d6',
                            cancelButtonColor: '#d33',
                            confirmButtonText: 'Yes, end it!'
                        }).then((result) => {
                            if (result.isConfirmed) {
                                handleEndExam(data, topicId);
                            }
                        });
                    });
                },
                error: function(error) {
                    console.error("Error fetching questions", error);
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: 'Error fetching questions. Please try again.'
                    });
                }
            });
        },
        error: function(error) {
            console.error("Error fetching topic details", error);
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Error fetching topic details. Please try again.'
            });
        }
    });
}


// Function to calculate total marks for questions
function calculateTotalMarks(questions) {
    let totalMarks = 0;

    questions.forEach(question => {
        if (question.marks) {
            totalMarks += question.marks; // Assuming each question has a 'marks' property
        }
    });

    console.log("Total Marks:", totalMarks);
    return totalMarks; // You can return this if needed elsewhere
}

// Updated handleEndExam function
function handleEndExam(data, topicId) {
    let score = 0;
    let attempted = 0;
    const totalQuestions = $('.card').length;
    const totalMarks = calculateTotalMarks(data); // Fetch total marks from questions

    for (let i = 0; i < totalQuestions; i++) {
        const selectedOption = $(`input[name="optradio${data[i].queNum}"]:checked`).val();
        if (selectedOption !== undefined) {
            attempted++;
            if (selectedOption === data[i].answer) {
                score += data[i].marks; // Add the question's marks to score
            }
        }
    }

    const percentage = (score / totalMarks) * 100;
    const result = percentage >= 50 ? "Pass" : "Fail";
    const loggedInUser = JSON.parse(localStorage.getItem('loggedInUser'));

    const examResults = {
        topic: {
            topicId: topicId
        },
        totalQuestions: totalQuestions,
        correctAnswers: score,
        attemptedQuestions: attempted,
        percentage: percentage,
        result: result,
        userRegistrationEntity: {
            id: loggedInUser ? loggedInUser.id : null
        }
    };

    console.log('Exam Results:', examResults);
    
    const examResultsFormData = new FormData();
    examResultsFormData.append('topicId', topicId);
    examResultsFormData.append('totalQuestions', totalQuestions);
    examResultsFormData.append('correctAnswers', score);
    examResultsFormData.append('attemptedQuestions', attempted);
    examResultsFormData.append('percentage', percentage);
    examResultsFormData.append('result', result);
    examResultsFormData.append('userId', loggedInUser ? loggedInUser.id : null);

    console.log("Form Data being sent:", [...examResultsFormData.entries()]);

    $.ajax({
        type: "POST",
        url: '/ExamResultSave',
        data: examResultsFormData,
        processData: false,
        contentType: false,
        success: function(response) {
            console.log("Report email sent successfully:", response);
            fetchLatestExamResult(loggedInUser.id);
        },
        error: function(error) {
            console.error("Error sending report email:", error);
        }
    });
	
    // Redirect to report card page
    window.location.href = '/reportCard?topicId=' + topicId;
}


/*function handleEndExam(data, topicId) {
    var score = 0;
    var attempted = 0;
    var totalQuestions = $('.card').length;

    for (var i = 0; i < totalQuestions; i++) {
        var selectedOption = $(`input[name="optradio${data[i].queNum}"]:checked`).val();
        if (selectedOption !== undefined) {
            attempted++;
            if (selectedOption === data[i].answer) {
                score++;
            }
        }
    }

    var percentage = (score / totalQuestions) * 100;
    var result = percentage >= 50 ? "Pass" : "Fail";
    var loggedInUser = JSON.parse(localStorage.getItem('loggedInUser'));

    var examResults = {
        topic: {
            topicId: topicId
        },
        totalQuestions: totalQuestions,
        correctAnswers: score,
        attemptedQuestions: attempted,
        percentage: percentage,
        result: result,
        userRegistrationEntity: {
            id: loggedInUser ? loggedInUser.id : null
        }
    };

    console.log('Exam Results:', examResults);
    
    var examResultsFormData = new FormData();
    examResultsFormData.append('topicId', topicId);
    examResultsFormData.append('totalQuestions', totalQuestions);
    examResultsFormData.append('correctAnswers', score);
    examResultsFormData.append('attemptedQuestions', attempted);
    examResultsFormData.append('percentage', percentage);
    examResultsFormData.append('result', result);
    examResultsFormData.append('userId', loggedInUser ? loggedInUser.id : null);

    console.log("Form Data being sent:", [...examResultsFormData.entries()]);

    $.ajax({
        type: "POST",
        url: '/ExamResultSave',
        data: examResultsFormData,
        processData: false,
        contentType: false,
        success: function(response) {
            console.log("Report email sent successfully:", response);
            fetchLatestExamResult(loggedInUser.id);
        },
        error: function(error) {
            console.error("Error sending report email:", error);
        }
    });

    // Redirect to report card page
    window.location.href = '/reportCard?topicId=' + topicId;
}*/




/*function renderQuestions(data) {
    var content = '';
    var navButtons = '';
    for (var i = 0; i < data.length; i++) {
        content += `
            <div class="card" id="question${i}" style="display: ${i === 0 ? 'block' : 'none'};">
                <div class="card-body">
                    <p>
                        <span>${data[i].queNum}</span>
                        <span>${data[i].questions}</span>
                    </p>
                    <div class="options">
                        <label>
                            <input type="radio" name="optradio${data[i].queNum}" value="${data[i].option1}">${data[i].option1}
                        </label>
                        <label>
                            <input type="radio" name="optradio${data[i].queNum}" value="${data[i].option2}">${data[i].option2}
                        </label>
                        <label>
                            <input type="radio" name="optradio${data[i].queNum}" value="${data[i].option3}">${data[i].option3}
                        </label>
                        <label>
                            <input type="radio" name="optradio${data[i].queNum}" value="${data[i].option4}">${data[i].option4}
                        </label>
                    </div>
                    <div class="footer">
                        <button class="save" onclick="saveAndNext(${i})">Save</button>
                    </div>
                </div>
            </div>
        `;
        navButtons += `<button class="${i === 0 ? 'current' : ''}" onclick="navigateToQuestion(${i})">${i + 1}</button>`;
    }
    $('#question-content').html(content);
    $('#nav-buttons').html(navButtons);
}

function navigateToQuestion(index) {
    $('.card').hide();
    $('#question' + index).show();
    $('.nav-buttons button').removeClass('current');
    $('.nav-buttons button').eq(index).addClass('current');
}

function saveAndNext(currentIndex) {
    var totalQuestions = $('.card').length;
    if (currentIndex + 1 < totalQuestions) {
        navigateToQuestion(currentIndex + 1);
    } else {
        alert("You have reached the last question.");
    }
}
*/
/*function renderQuestions(data) {
    var content = '';
    var navButtons = '';
    for (var i = 0; i < data.length; i++) {
        content += `
            <div class="card" id="question${i}" style="display: ${i === 0 ? 'block' : 'none'};">
                <div class="card-body">
                    <p>
                        <span>${data[i].queNum}</span>
                        <span>${data[i].questions}</span>
                    </p>
                    <div class="options">
                        <label>
                            <input type="radio" name="optradio${data[i].queNum}" value="${data[i].option1}">${data[i].option1}
                        </label>
                        <label>
                            <input type="radio" name="optradio${data[i].queNum}" value="${data[i].option2}">${data[i].option2}
                        </label>
                        <label>
                            <input type="radio" name="optradio${data[i].queNum}" value="${data[i].option3}">${data[i].option3}
                        </label>
                        <label>
                            <input type="radio" name="optradio${data[i].queNum}" value="${data[i].option4}">${data[i].option4}
                        </label>
                    </div>
                    <div class="footer">
                        <button class="save" onclick="saveAnswer(${i})">Save</button>
                        <button class="next" onclick="nextQuestion(${i})">Next</button>
                    </div>
                </div>
            </div>
        `;
        navButtons += `<button id="navButton${i}" class="${i === 0 ? 'current' : ''}" onclick="navigateToQuestion(${i})">${i + 1}</button>`;
    }
    $('#question-content').html(content);
    $('#nav-buttons').html(navButtons);
}*/

function escapeHtml(unsafe) {
    if (unsafe == null) return ''; // Handle null or undefined values
    return unsafe
        .toString() // Ensure input is a string
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}


function renderQuestions(data) {
    var content = '';
    var navButtons = '';

    for (var i = 0; i < data.length; i++) {
        // Check if image data exists and construct image HTML
        var imageHtml = '';
        if (data[i].imageData) {
            imageHtml = `<img src="data:image/png;base64,${escapeHtml(data[i].imageData)}" alt="Question Image" style="width: 30%; height: 30%; display: block; margin-bottom: 10px;">`;
        }

        // Escape special characters in the question and options
        var questionText = escapeHtml(data[i].questions);
        var option1 = escapeHtml(data[i].option1);
        var option2 = escapeHtml(data[i].option2);
        var option3 = escapeHtml(data[i].option3);
        var option4 = escapeHtml(data[i].option4);

        content += `
            <div class="card" id="question${i}" style="display: ${i === 0 ? 'block' : 'none'};">
                <div class="card-body">
                    <p>
                        <span>${escapeHtml(data[i].queNum)}</span>
                        <span>${questionText}</span>
                    </p>
                    ${imageHtml} <!-- Insert image if available -->
                    <div class="options">
                        <label>
                            <input type="radio" name="optradio${escapeHtml(data[i].queNum)}" value="${option1}">${option1}
                        </label>
                        <label>
                            <input type="radio" name="optradio${escapeHtml(data[i].queNum)}" value="${option2}">${option2}
                        </label>
                        <label>
                            <input type="radio" name="optradio${escapeHtml(data[i].queNum)}" value="${option3}">${option3}
                        </label>
                        <label>
                            <input type="radio" name="optradio${escapeHtml(data[i].queNum)}" value="${option4}">${option4}
                        </label>
                    </div>
                    <div class="footer">
                        <button class="save" onclick="saveAnswer(${i})">Save</button>
                        <button class="next" onclick="nextQuestion(${i})">Next</button>
                    </div>
                </div>
            </div>
        `;

        navButtons += `<button id="navButton${i}" class="${i === 0 ? 'current' : ''}" onclick="navigateToQuestion(${i})">${i + 1}</button>`;
    }

    $('#question-content').html(content);
    $('#nav-buttons').html(navButtons);
}




/*function renderQuestions(data) {
    var content = '';
    var navButtons = '';

    for (var i = 0; i < data.length; i++) {
        // Check if image data exists and construct image HTML
        var imageHtml = '';
        if (data[i].imageData) {
            imageHtml = `<img src="data:image/png;base64,${data[i].imageData}" alt="Question Image" style="width: 30%; height: 30%; display: block; margin-bottom: 10px;">`;
        }

        content += `
            <div class="card" id="question${i}" style="display: ${i === 0 ? 'block' : 'none'};">
                <div class="card-body">
                    <p>
                        <span>${data[i].queNum}</span>
                        <span>${data[i].questions}</span>
                    </p>
                    ${imageHtml} <!-- Insert image if available -->
                    <div class="options">
                        <label>
                            <input type="radio" name="optradio${data[i].queNum}" value="${data[i].option1}">${data[i].option1}
                        </label>
                        <label>
                            <input type="radio" name="optradio${data[i].queNum}" value="${data[i].option2}">${data[i].option2}
                        </label>
                        <label>
                            <input type="radio" name="optradio${data[i].queNum}" value="${data[i].option3}">${data[i].option3}
                        </label>
                        <label>
                            <input type="radio" name="optradio${data[i].queNum}" value="${data[i].option4}">${data[i].option4}
                        </label>
                    </div>
                    <div class="footer">
                        <button class="save" onclick="saveAnswer(${i})">Save</button>
                        <button class="next" onclick="nextQuestion(${i})">Next</button>
                    </div>
                </div>
            </div>
        `;

        navButtons += `<button id="navButton${i}" class="${i === 0 ? 'current' : ''}" onclick="navigateToQuestion(${i})">${i + 1}</button>`;
    }

    $('#question-content').html(content);
    $('#nav-buttons').html(navButtons);
}*/


var totalQuestionsCount = $('.card').length;
var attemptedCount = 0;
var visitedCount = 0;

function saveAnswer(index) {
    var selectedOption = $(`input[name="optradio${index + 1}"]:checked`).val();
    if (!selectedOption) {
        toastr.error("You haven't selected any answer");
    } else {
        // Check if the question is already answered
        var isAnswered = $(`#navButton${index}`).hasClass('answered');

        if (!isAnswered) {
            // Save the answer logic here if needed
            toastr.success(`Answer saved for question ${index + 1}`);
            $(`#navButton${index}`).removeClass('visited').addClass('answered');
            attemptedCount++;
            updateStatusCounts();
        } else {
            toastr.warning(`Question ${index + 1} has already been answered.`);
        }
    }
}




function nextQuestion(currentIndex) {
    var totalQuestions = $('.card').length;

    if (currentIndex < totalQuestions - 1) {
        // Navigate to the next question
        navigateToQuestion(currentIndex + 1);

        // Mark the current question as visited if not already answered
        if (!$(`input[name="optradio${currentIndex + 1}"]:checked`).length) {
            $(`#navButton${currentIndex}`).addClass('visited');
            visitedCount++;
            updateStatusCounts(); // Update counts after marking as visited
        }
    } else {
        alert("You have reached the last question.");
    }
}




function navigateToQuestion(index) {
	$('.card').hide();
	$('#question' + index).show();
	$('.nav-buttons button').removeClass('current');
	$('.nav-buttons button').eq(index - 1).addClass('current');
	// Mark the question as visited if not already answered
	if (!$(`input[name="optradio${index}"]:checked`).length) {
		$(`#navButton${index}`).addClass('visited');
		//visitedCount++;
		updateStatusCounts();
	}
}

function updateStatusCounts() {
	var notVisitedCount = totalQuestionsCount - visitedCount;
	var visitedNotAnsweredCount = visitedCount - attemptedCount;

	$('#attemptedCount > div:first-child').text(attemptedCount);
	//$('#visitedCount > div:first-child').text(visitedCount);
	//$('#notVisitedCount > div:first-child').text(notVisitedCount);
	//$('#visitedNotAnsweredCount > div:first-child').text(visitedNotAnsweredCount);
}


/*function saveAnswer(index) {
    var selectedOption = $(`input[name="optradio${index + 1}"]:checked`).val();
    if (!selectedOption) {
        alert("You haven't selected any answer.");
    } else {
        // Save the answer logic here if needed
        alert(`Answer saved for question ${index + 1}`);
        $(`#navButton${index}`).removeClass('visited').addClass('answered');
    }
}

function nextQuestion(currentIndex) {
    var totalQuestions = $('.card').length;
    if (currentIndex + 1 < totalQuestions) {
        navigateToQuestion(currentIndex + 1);
    } else {
        alert("You have reached the last question.");
    }
    // Mark the current question as visited if not already answered
    if (!$(`input[name="optradio${currentIndex + 1}"]:checked`).length) {
        $(`#navButton${currentIndex}`).addClass('visited');
    }
}

function navigateToQuestion(index) {
    $('.card').hide();
    $('#question' + index).show();
    $('.nav-buttons button').removeClass('current');
    $('.nav-buttons button').eq(index).addClass('current');
    // Mark the question as visited if not already answered
    if (!$(`input[name="optradio${index + 1}"]:checked`).length) {
        $(`#navButton${index}`).addClass('visited');
    }
}*/


function startTimer(duration, display) {
    let timer = duration, hours, minutes, seconds;
    let interval = setInterval(function () {
        hours = parseInt(timer / 3600, 10);
        minutes = parseInt((timer % 3600) / 60, 10);
        seconds = parseInt(timer % 60, 10);

        hours = hours < 10 ? "0" + hours : hours;
        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        display.textContent = hours + ":" + minutes + ":" + seconds;

        if (--timer < 0) {
            clearInterval(interval);
            alert("Time is up!");
            $('#endExam').click(); // Automatically end the exam
        }
    }, 1000);
}


function showExamResultTable(){
	$.ajax({
				type: "GET",
				contentType: "application/json",
				url: '/getAllResult',
				dataType: 'json',
				success: function (data) {
					
				console.log("ALL Exam Result : ", data);
				console.log("Exam result id from all data : ", data.id);
				/*examResultById(data.id);*/
				 },
        	error: function () {
            alert("Error loading all data...");
        	}
    });
    }
    
    function examResultById(data){
		console.log("Exam Result data is : ", data);
    			$.ajax({
				type: "GET",
				contentType: "application/json",
				url: '/ExamResultById' ,
				dataType: 'json',
				success: function (data) {

				console.log("Exam Result By id: ", data);

				var d = '';

				for (var i = 0; i < data.length; i++) {

				d += '<tr class="table-danger">' +

				'<td > ' + data[i].id + '</td>' +
				'<td > ' + data[i].topicName + '</td>' +
				'<td > ' + data[i].description + '</td>' +
				'<td > ' + data[i].totalQuestions + '</td>' +
				'<td > ' + data[i].correctAnswers + '</td>' +
				'<td > ' + data[i].percentage + '</td>' +
				'<td > ' + data[i].result + '</td>' +
				'</tr >'
			 }

            $('#table').html(d); 
        },
        error: function () {
            alert("Error loading by id data...");
        }
    });

}

/*function fetchLatestExamResult(userId) {
	console.log("User Id is : ", userId)
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/latestExamResult?userId=" + userId,
        success: function(data) {
            console.log("Latest Exam Result:", data);
            var d = '';

				for (var i = 0; i < data.length; i++) {

				d += '<tr class="table-danger">' +

				'<td > ' + data[i].id + '</td>' +
				'<td > ' + data[i].topicName + '</td>' +
				'<td > ' + data[i].description + '</td>' +
				'<td > ' + data[i].totalQuestions + '</td>' +
				'<td > ' + data[i].correctAnswers + '</td>' +
				'<td > ' + data[i].percentage + '</td>' +
				'<td > ' + data[i].result + '</td>' +
				'</tr >'
			 }

            $('#table').html(d); 
        },
        error: function(error) {
            console.error("Error fetching latest exam result:", error);
        }
    });
}
*/


$(document).ready(function() {
    console.log("Document is ready");

    var loggedInUser = JSON.parse(localStorage.getItem('loggedInUser'));
    console.log('Logged In User:', loggedInUser);

    if (loggedInUser && loggedInUser.id) {
        fetchLatestExamResult(loggedInUser.id);
    } else {
        console.error("No logged in user found");
    }
});

function fetchLatestExamResult(id) {
    console.log("User Id is: ", id);
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/latestExamResult/" + id,
        success: function(data) {
            console.log("Latest Exam Result:", data);
            var d = '<tr class="table-secondary">' +
                '<td>' + data.id + '</td>' +
                /*'<td>' + data.topicName + '</td>' +
                '<td>' + data.description + '</td>' +*/
                '<td>' + data.totalQuestions + '</td>' +
                '<td>' + data.attemptedQuestions + '</td>' +
                '<td>' + data.correctAnswers + '</td>' +
                '<td>' + data.percentage + '</td>' +
                '<td>' + data.result + '</td>' +
                '</tr>';

			$('#table').html(d);
			const resultImage = document.getElementById('resultImage');

			if (data.result === 'Pass') {
				resultImage.src = '../user/assets/img/congratulation.jpg';
			}
			else {
				resultImage.src = '../user/assets/img/try again.jpg';
			}
        },
        error: function(error) {
            console.error("Error fetching latest exam result:", error);
        }
    });
}
