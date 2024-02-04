function login() {
    // Get form data
    var username = $("#username").val();
    var password = $("#password").val();

    // Create JSON object
    var data = {
        username: username,
        password: password
    };
//TODO: FIX THE CONNECTION ERROR - 400
// Make AJAX request to your REST endpoint
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "http://localhost:8082/api/users/login",
        data: JSON.stringify(data),
        success: function(response) {
            console.log("Login successful");
        },
        error: function(error) {
            console.error("Login failed:", error.responseText);
        }
    });

}
