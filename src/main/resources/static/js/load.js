document.getElementById('loadCompanies').addEventListener('click', function () {
    fetch('http://localhost:8082/api/v1/logistics-companies', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            const viewCompanies = document.getElementById('viewCompanies');
            const table = document.createElement('table');
            table.classList.add('table');
            const thead = document.createElement('thead');
            const tbody = document.createElement('tbody');
            const tr = document.createElement('tr');
            const th1 = document.createElement('th');
            const th2 = document.createElement('th');
            const th3 = document.createElement('th');
            th1.setAttribute('scope', 'col');
            th2.setAttribute('scope', 'col');
            th3.setAttribute('scope', 'col');
            th1.innerText = 'ID';
            th2.innerText = 'Name';
            th3.innerText = 'Income';
            tr.appendChild(th1);
            tr.appendChild(th2);
            tr.appendChild(th3);
            thead.appendChild(tr);
            table.appendChild(thead);
            data.forEach(company => {
                const tr = document.createElement('tr');
                const td1 = document.createElement('td');
                const td2 = document.createElement('td');
                const td3 = document.createElement('td');
                td1.innerText = company.id;
                td2.innerText = company.name;
                td3.innerText = company.income;
                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tbody.appendChild(tr);
            });
            table.appendChild(tbody);
            viewCompanies.appendChild(table);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
})

document.getElementById('loadOffices').addEventListener('click', function () {
    fetch('http://localhost:8082/api/v1/offices', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            const viewOffices = document.getElementById('viewOffices');
            viewOffices.innerHTML = ''; // Clear existing table/content
            const table = document.createElement('table');
            table.classList.add('table');
            const thead = document.createElement('thead');
            const tbody = document.createElement('tbody');
            const trHead = document.createElement('tr');

            // Define headers for the table
            const headers = ['ID', 'Address', 'City', 'Postcode', 'Actions'];
            headers.forEach(header => {
                const th = document.createElement('th');
                th.setAttribute('scope', 'col');
                th.innerText = header;
                trHead.appendChild(th);
            });
            thead.appendChild(trHead);
            table.appendChild(thead);

            // Populate the table body with office data
            data.forEach(office => {
                const tr = document.createElement('tr');
                const tdID = document.createElement('td');
                const tdAddress = document.createElement('td');
                const tdCity = document.createElement('td');
                const tdPostcode = document.createElement('td');
                const tdActions = document.createElement('td');

                tdID.innerText = office.id;
                tdAddress.innerText = office.address;
                tdCity.innerText = office.city;
                tdPostcode.innerText = office.postcode;

                const deleteButton = document.createElement('button');
                deleteButton.classList.add('btn', 'btn-danger');
                deleteButton.innerText = 'Delete';
                deleteButton.onclick = function () { deleteOffice(office.id); };

                const updateButton = document.createElement('button');
                updateButton.classList.add('btn', 'btn-info', 'me-2');
                updateButton.innerText = 'Update';
                updateButton.addEventListener('click', function () {
                    document.getElementById('updateOfficeId').value = office.id; // Set the office ID in the hidden input
                    document.getElementById('updateOfficeAddress').value = office.address; // Pre-fill the form with existing values
                    document.getElementById('updateOfficeCity').value = office.city;
                    document.getElementById('updateOfficeName').value = office.name;
                    document.getElementById('updateOfficePostcode').value = office.postcode;
                    document.getElementById('updateOffice').classList.remove('d-none'); // Show the update form section
                });


                // Append buttons to the Actions cell
                tdActions.appendChild(updateButton);
                tdActions.appendChild(deleteButton);
                tdActions.appendChild(updateButton);

                // Append cells to the row
                tr.appendChild(tdID);
                tr.appendChild(tdAddress);
                tr.appendChild(tdCity);
                tr.appendChild(tdPostcode);
                tr.appendChild(tdActions);

                // Append row to the tbody
                tbody.appendChild(tr);
            });

            table.appendChild(tbody);
            viewOffices.appendChild(table);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
});