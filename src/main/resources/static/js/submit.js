document.getElementById('submitButton').addEventListener('click', function () {
    const name = document.getElementById('companyName').value.trim();
    const income = document.getElementById('companyIncome').value.trim();


    var errorToastEl = document.getElementById('errorToast');
    var errorToast = new bootstrap.Toast(errorToastEl);
    var successToastEl = document.getElementById('successToast');
    var successToast = new bootstrap.Toast(successToastEl);
    var fetchErrorToastEl = document.getElementById('fetchErrorToast');
    var fetchErrorToast = new bootstrap.Toast(fetchErrorToastEl);


    if (!name || !income) {
        errorToast.show();
    } else {
        const data = { name, income };

        fetch('http://localhost:8082/api/v1/logistics-companies', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Success:', data);
                successToast.show();
            })
            .catch((error) => {
                console.error('Error:', error);
                fetchErrorToast.show();
            });
    }
});

document.getElementById('submitOffice').addEventListener('click', function () {
    const address = document.getElementById('officeAddress').value.trim();
    const city = document.getElementById('officeCity').value.trim();
    const name = document.getElementById('officeName').value.trim();
    const postcode = document.getElementById('officePostcode').value.trim();

    // Initialize toasts
    var errorToastEl = document.getElementById('errorToast');
    var errorToast = new bootstrap.Toast(errorToastEl);
    var successToastEl = document.getElementById('successToast');
    var successToast = new bootstrap.Toast(successToastEl);
    var fetchErrorToastEl = document.getElementById('fetchErrorToast');
    var fetchErrorToast = new bootstrap.Toast(fetchErrorToastEl);

    if (!address || !city || !name || !postcode) {
        errorToast.show();
    } else {
        const data = { address, city, name, postcode };

        // API request
        fetch('http://localhost:8082/api/v1/offices', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Success:', data);
                successToast.show();
            })
            .catch((error) => {
                console.error('Error:', error);
                fetchErrorToast.show();
            });
    }
});