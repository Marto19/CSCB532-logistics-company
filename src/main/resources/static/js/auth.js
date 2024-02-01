// auth.js

// Function to handle success response from authentication or registration
function handleSuccess(response) {
    if (response.ok) {
        return response.json().then(data => {
            const token = data.token;
            if (token) {
                // Save the token to localStorage
                localStorage.setItem('token', token);
                console.log('Token stored in localStorage:', token);
                console.log('Token:', token);
                console.log('Headers:', headers);

                // Call the function to send the authenticated request
                sendAuthenticatedRequest();

                // Redirect to a specified page or handle as needed
                redirectToPage("/main.html");
            } else {
                console.error('Token not found in response:', data);
            }
        });
    } else {
        return response.json().then(data => {
            console.error('Error:', data);
        });
    }
}



// Function to redirect to a specified page
function redirectToPage(page) {
    window.location.href = page;
}
function authenticateUser() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    // Construct JSON payload
    var payload = {
        "username": username,
        "password": password
    };

    // Log the token before making the request
    console.log("Token in localStorage:", localStorage.getItem('token'));

    // Send the JSON payload to the authentication endpoint with bearer token
    fetch("/api/v1/auth/authenticate", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + localStorage.getItem('token')
        },
        body: JSON.stringify(payload)
    })
        .then(handleSuccess)
        .catch(error => console.error('Error:', error));

    // Prevent form submission
    return false;
}



function registerUser() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    // Construct JSON payload
    var payload = {
        "username": username,
        "password": password
    };

    // Send the JSON payload to the registration endpoint
    fetch("/api/v1/auth/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(payload)
    })
        .then(response => {
            if (response.ok) {
                // Registration successful, you might not get a token here
                console.log("Registration successful");
                // Redirect to login page or handle as needed
            } else {
                // Registration failed, handle errors
                console.error('Error during registration:', response.status);
                return response.json();
            }
        })
        .then(data => {
            console.log(data); // Handle additional registration response here
        })
        .catch(error => console.error('Error:', error));

    // Prevent form submission
    return false;
}

function sendAuthenticatedRequest() {
    var token = localStorage.getItem('token');

    console.log('Token in localStorage:', token);

    if (!token) {
        console.error('Token not found. User is not authenticated.');
        // Handle the case where the user is not authenticated, maybe redirect to login
        return;
    }

    // Construct the headers with Authorization Bearer Token
    var headers = {
        'Authorization': 'Bearer ' + token,
        'Content-Type': 'application/json'
    };


// Make a GET request to /main.html with Authorization header using Fetch API
    fetch('/main.html', {
        method: 'GET',
        headers: headers
    })
        .then(response => {
            console.log('Response status:', response.status);
            if (response.ok) {
                // Handle the successful response, maybe display the content of main.html
                return response.text();
            } else {
                // Handle the case where the server responds with an error (e.g., 403)
                console.error('Error:', response.statusText);
            }
        })
        .then(data => {
            console.log('Response from /main.html:', data);
            // You can update your UI with the content of main.html here
        })
        .catch(error => console.error('Error:', error));
}



