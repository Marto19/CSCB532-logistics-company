document.getElementById('updateOfficeButton').addEventListener('click', function () {
    // Get the values from the form fields
    const id = document.getElementById('updateOfficeId').value; // Assuming this gets populated somewhere in your code
    const address = document.getElementById('updateOfficeAddress').value.trim();
    const city = document.getElementById('updateOfficeCity').value.trim();
    const name = document.getElementById('updateOfficeName').value.trim();
    const postcode = document.getElementById('updateOfficePostcode').value.trim();

    // Check if all fields are filled
    if (!address || !city || !name || !postcode) {
        alert('Please fill in all the fields.');
        return;
    }

    // Prepare the data to send in the request
    const data = { address, city, name, postcode };

    // Send the PUT request to update the office
    fetch(`http://localhost:8082/api/v1/offices/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token') // Adjust this as per your auth setup
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(updatedOffice => {
            console.log('Office updated successfully:', updatedOffice);
            successToast.show();
        })
        .catch(error => {
            console.error('Error updating the office:', error);
            fetchErrorToast.show();
        });
});