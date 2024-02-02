// Function to handle success response from authentication or registration
async function handleSuccess(response) {
    if (!response.ok) {
        console.error('Error:', await response.json());
        return;
    }

    const data = await response.json();
    const token = data.token;

    if (!token) {
        console.error('Token not found in response:', data);
        return;
    }

    // Save the token to localStorage
    localStorage.setItem('token', token);
    console.log('Token stored in localStorage:', token);

    // Call the function to send the authenticated request
    sendAuthenticatedRequest();

    // Redirect to a specified page or handle as needed
    redirectToPage("/main.html");
}

// Function to redirect to a specified page
function redirectToPage(page) {
    window.location.href = page;
}

async function authenticateUser() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    // Construct JSON payload
    const payload = {
        "username": username,
        "password": password
    };

    // Send the JSON payload to the authentication endpoint
    await fetch("/api/v1/auth/authenticate", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(payload)
    })
        .then(handleSuccess)
        .catch(error => console.error('Error:', error));

    // Prevent form submission
    return false;
}

async function registerUser() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    // Construct JSON payload
    const payload = {
        "username": username,
        "password": password
    };

    // Send the JSON payload to the registration endpoint
    await fetch("/api/v1/auth/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(payload)
    })
        .then(async response => {
            if (response.ok) {
                console.log("Registration successful");
            } else {
                console.error('Error during registration:', response.status);
                console.error(await response.json());
            }
        })
        .catch(error => console.error('Error:', error));

    // Prevent form submission
    return false;
}

async function sendAuthenticatedRequest() {
    const token = localStorage.getItem('token');

    if (!token) {
        console.error('Token not found. User is not authenticated.');
        return;
    }

    // Construct the headers with Authorization Bearer Token
    const headers = {
        'Authorization': 'Bearer ' + token,
        'Content-Type': 'application/json'
    };

    // Make a GET request to /main.html with Authorization header using Fetch API
    const response = await fetch('/main.html', {
        method: 'GET',
        headers: headers
    });

    console.log('Response status:', response.status);

    if (!response.ok) {
        console.error('Error:', response.statusText);
        return;
    }

    const data = await response.text();
    console.log('Response from /main.html:', data);
}
