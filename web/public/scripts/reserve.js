const reservationForm = document.getElementById('reservationForm');

const mobileNoInput = document.getElementById('mobileNo');
const emailInput = document.getElementById('email');
const firstNameInput = document.getElementById('firstName');
const lastNameInput = document.getElementById('lastName');
const birthdayInput = document.getElementById('birthday');
const fullAddressInput = document.getElementById('fullAddress');
const cityInput = document.getElementById('city');

window.onload = function() {
    mobileNoInput.value = null;
    emailInput.value = null;
    firstNameInput.value = null;
    lastNameInput.value = null;
    birthdayInput.value = null;
    fullAddressInput.value = null;
    cityInput.value = null;

    getStatus
};

reservationForm.addEventListener('submit', async function(e){
    e.preventDefault();

    const formData = new FormData(reservationForm).entries();
    const formBody = JSON.stringify(Object.fromEntries(formData));

    var postOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: formBody
    };

    // reserve variable found in ../settings/server.js

    var response = await fetch(reserve, postOptions);
    var result = await response.json();
    
    console.log(result.id);
    
    window.location.href = "../views/qr.html?" + result.id;

});

async function getStatus(){

    var getOptions = {
        method: 'GET',
        mode: 'cors',
        headers: { 'Content-Type': 'application/json' },
        cache: 'default'
    };

    var response = await fetch(event_current, getOptions);
    var result = await response.json();
    
    return result;

}


//function accepts string for mobile number
// e.g. var mo_no = '09083909247';

async function getPerson(no){

    const mobileNoBody = JSON.stringify(no);

    console.log(mobileNoBody);

    var getOptions = {
        method: 'GET',
        mode: 'cors',
        headers: { 'Content-Type': 'application/json' },
        cache: 'default'
    };

    var response = await fetch(findByMobileNo + no, getOptions);
    var result = await response.json();

    console.log(result);

    // fill fields
    if(result.message == null) {
        emailInput.value = result.email;
        firstNameInput.value = result.firstName;
        lastNameInput.value = result.lastName;
        birthdayInput.value = result.birthday;
        fullAddressInput.value = result.fullAddress;
        cityInput.value = result.city;
    }

    return result;
}

function autofill() {
    getPerson(mobileNoInput.value);
}