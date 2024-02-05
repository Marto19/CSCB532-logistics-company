import jwtDecode from 'jwt-decode';

async function handleSuccess(response) {
    console.log('handleSuccess function called');
    const data = await response.json();

    if (!response.ok) {
        console.error('Error:', data);
        return;
    }

    const token = data.token;

    if (!token) {
        console.error('Token not found in response:', data);
        return;
    }

    // Save the token to localStorage
    localStorage.setItem('token', token);
    console.log('Token stored in localStorage:', token);

    // Decode the token to get the user's role
    const payload = jwtDecode(token);
    const userRoles = payload.role; // assuming the roles are stored in the 'role' property



    localStorage.setItem('roles', userRoles)
    console.log('User roles: ', userRoles)

    // Redirect the user to the appropriate page based on their role
    if (userRoles.includes('ROLE_ADMIN')) {  //todo: change to admin
        redirectToPage("/admin/adminpage");
    } else if (userRoles.includes('ROLE_CUSTOMER')) {
        redirectToPage("/customer/customerpage");
    } else if (userRoles.includes('ROLE_EMPLOYEE')) {
            redirectToPage("/employee.html");
    } else {
        console.error('Unknown role:', userRoles);
    }

    // Call the function to send the authenticated request
    sendAuthenticatedRequest();
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


}