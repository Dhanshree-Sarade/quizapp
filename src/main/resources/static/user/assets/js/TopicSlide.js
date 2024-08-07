/*$(document).ready(function(){
            $('.quiz').slick({
                dots: true,
                infinite: false,
                speed: 300,
                slidesToShow: 3,
                slidesToScroll: 1,
                prevArrow: '<button class="slick-prev">Previous</button>',
                nextArrow: '<button class="slick-next">Next</button>',
                responsive: [
                    {
                        breakpoint: 1024,
                        settings: {
                            slidesToShow: 3,
                            slidesToScroll: 1,
                            infinite: true,
                            dots: true
                        }
                    },
                    {
                        breakpoint: 600,
                        settings: {
                            slidesToShow: 2,
                            slidesToScroll: 1
                        }
                    },
                    {
                        breakpoint: 480,
                        settings: {
                            slidesToShow: 1,
                            slidesToScroll: 1
                        }
                    }
                ]
            });
        });*/
        
        
        
 $(document).ready(function() {
    $('.dropdown-item').on('click', function(e) {
        e.preventDefault();
        const language = $(this).data('value'); // Use data-value attribute to get language
        fetchTopics(language);
        console.log(language);
    });

    // Initial call to initialize the Slick slider
    //initializeSlickSlider();
});

function fetchTopics(language) {
    alert("Topics fetching....");
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: '/topics/' + language, // Correctly concatenate language parameter
        dataType: 'json',
        success: function(data) {
            console.log("Topic data:", data);
            var content = '';
            for (var i = 0; i < data.length; i++) {
                content += `
                    <div class="col-md-12">
                        <a href="#" class="card">
                            <div style="color:red">${data[i].topicName}</div>
                            <div>${data[i].description}</div>
                            <p>
                                <span>Total Questions Count: </span>
                                <span>${data[i].totalQuestions || '10'}</span>
                            </p>
                            <p>
                                <span>Marks: </span>
                                <span>${data[i].marks || '10'}</span>
                            </p>
                            <p>
                                <span>Duration (Minutes): </span>
                                <span>${data[i].duration || '10'}</span>
                            </p>
                            <div class="quiz_footer">
                                <button class="quiz_button" id="showQuestions" data-topic-id="${data[i].topicId}">Start Quiz</button>
                            </div>
                        </a>
                    </div>
                `;
            }
            $('#topics-container').html(content); // Update container id to match your HTML

			 $('#topics-container').trigger('destroy.owl.carousel');
			initializeOwlCarousel();
            // Reinitialize the Slick slider after adding the content
            //initializeSlickSlider(); // Reinitialize
            
        },
        error: function(error) {
            console.error("Error fetching topics", error);
        }
    });
}

function initializeSlickSlider() {
	alert("Slick slider...");
    // Check if the slider is already initialized, if so, destroy it
    if ($('.slider').hasClass('slick-initialized')) {
        $('.slider').slick('unslick');
    }

    // Initialize the Slick slider
    $('.slider').slick({
        dots: true,
        infinite: false,
        speed: 300,
        slidesToShow: 3,
        slidesToScroll: 1,
        responsive: [
            {
                breakpoint: 1024,
                settings: {
                    slidesToShow: 3,
                    slidesToScroll: 3,
                    infinite: true,
                    dots: true
                }
            },
            {
                breakpoint: 600,
                settings: {
                    slidesToShow: 2,
                    slidesToScroll: 2
                }
            },
            {
                breakpoint: 480,
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1
                }
            }
        ]
    });
    
    alert("Slick slider end ....");
}

// Initial call to initialize the Slick slider
//$(document).ready(function() {
//    initializeSlickSlider();
//});







function initializeOwlCarousel() {
        $('#topics-container').owlCarousel({
            loop: true,
            margin: 10,
            nav: true,
            responsive: {
                0: {
                    items: 1
                },
                600: {
                    items: 2
                },
                1000: {
                    items: 3
                }
            },
            navText: ["<button type='button' class='owl-prev'><i class='bi bi-chevron-compact-left'></i></button>", "<button type='button' class='owl-next'><i class='bi bi-chevron-compact-right'></i></button>"]
        });
    }
