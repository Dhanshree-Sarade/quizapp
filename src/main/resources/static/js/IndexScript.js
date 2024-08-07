$(document).ready(function() {
    fetchUserCount();
    fetchNewUserCount();
});

function fetchUserCount() {
    $.ajax({
        type: "GET",
        url: "/getUser",
        contentType: "application/json",
        dataType: 'json',
        success: function(users) {
            var userCount = users.length;
            $('#userCount').text(userCount);
        },
        error: function(error) {
            console.error("Error fetching user count", error);
            $('#userCount').text("Error");
        }
    });
}


function fetchNewUserCount() {
    $.ajax({
        type: "GET",
        url: "/getNewUsersCount",
        contentType: "application/json",
        success: function(newUser) {
			console.log(newUser);
            $('#newUsersCount').text(newUser);
        },
        error: function(error) {
            console.error("Error fetching new user count", error);
            $('#newUsersCount').text("Error");
        }
    });
}