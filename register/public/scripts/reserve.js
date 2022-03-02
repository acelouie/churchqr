const reservationForm = document.getElementById('reservationForm');

const eventNameText = document.getElementById('eventName');
const eventStatusText = document.getElementById('eventStatus');

const fieldsContainer = document.getElementById('fieldsContainer');

//inputs

const mobileNoInput = document.getElementById('mobileNo');
const emailInput = document.getElementById('email');
const firstNameInput = document.getElementById('firstName');
const lastNameInput = document.getElementById('lastName');
const birthdayInput = document.getElementById('birthday');
const fullAddressInput = document.getElementById('fullAddress');
const cityInput = document.getElementById('city');

const inputList = [
    mobileNoInput,
    emailInput,
    firstNameInput,
    lastNameInput,
    // birthdayInput,
    fullAddressInput,
    cityInput
]

//checkboxes

const symptoms = document.getElementById('symptomsCheckbox');
const contact = document.getElementById('contactCheckbox');
const vaccinated = document.getElementById('vaccinated');
const volunteer = document.getElementById('volunteer');

const checkboxList = [
    symptoms,
    contact,
    vaccinated
]

//helper texts
const mobileNoHelper = document.getElementById('mobileNoHelper');
const emailHelper = document.getElementById('emailHelper');
const firstNameHelper = document.getElementById('firstNameHelper');
const lastNameHelper = document.getElementById('lastNameHelper');
const birthdayHelper = document.getElementById('birthdayHelper');
const fullAddressHelper = document.getElementById('fullAddressHelper');
const cityHelper = document.getElementById('cityHelper');

// validation texts

const valTexts = {
    required: "This field is required",
    valid: "Must be a valid ",
    phone: "Must be an 11-digit phone number"
}

window.onload = function () {

    mobileNoInput.value = null;
    emailInput.value = null;
    firstNameInput.value = null;
    lastNameInput.value = null;
    // birthdayInput.value = null;
    fullAddressInput.value = null;
    cityInput.value = null;

    cityDropdown();
    getStatus();
};

reservationForm.addEventListener('submit', async function (e) {

    e.preventDefault();

    //Checks for empty fields, see respective functions
    checkForEmptyFields();
    var validFields = validateAllFields();

    //If none of the fields have an invalid class, proceed posting to API
    if (validFields == true) {

        vaccinated.value = true;

        if (volunteer.checked === false || volunteer.checked === undefined) {
            volunteer.value = false
        } else {
            volunteer.value = true
        }

        const formData = new FormData(reservationForm).entries();
        const formBody = JSON.stringify(Object.fromEntries(formData));

        var postOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: formBody
        };

        // 'reserve' variable found in ../settings/server.js

        var response = await fetch(reserve, postOptions);
        var result = await response.json();

        if (result.id !== undefined) {
            console.log("Redirecting to QR page....")
            window.location.href = "../../qr.html?" + result.id;
        } else {
            // with error
            window.location.href = "../../error.html?" + result.message;
        }

    }



});

async function getStatus() {

    var getOptions = {
        method: 'GET',
        mode: 'cors',
        headers: { 'Content-Type': 'application/json' },
        cache: 'default'
    };

    var response = await fetch(event_current, getOptions);
    var result = await response.json();

    // update fields
    if (result.message == null) {
        eventNameText.innerText = result.name;
        if (result.status == "OPEN") {
            eventStatusText.innerHTML = "Registration is open!";
        } else {
            eventStatusText.innerHTML = "Sorry, registration is closed." +
                "<p style='font-size:16px; margin: 30px auto 0px auto; font-style: italic;'>Registration for the upcoming Sunday opens every Friday from 12AM to 7PM.</p>" +
                "<p style='font-size:16px; margin: 20px auto 30px auto; font-style: italic;'>Registration closes when the limit of 200 people is reached.</p>";
            fieldsContainer.style.display = "none";
        }
    }

    return result;

}


// function accepts string for mobile number
// e.g. var mo_no = '09083909247';

async function getPerson(no) {

    const mobileNoBody = JSON.stringify(no);

    var getOptions = {
        method: 'GET',
        mode: 'cors',
        headers: { 'Content-Type': 'application/json' },
        cache: 'default'
    };

    var response = await fetch(findByMobileNo + no, getOptions);
    var result = await response.json();


    // fill fields
    if (result.message == null) {
        emailInput.value = result.email;
        firstNameInput.value = result.firstName;
        lastNameInput.value = result.lastName;
        // birthdayInput.value = result.birthday;
        fullAddressInput.value = result.fullAddress;
        cityInput.value = result.city;

        //changes validation colors and makes sure changes happen
        checkForEmptyFields();
        selectInit();
        focusAllFields();
        M.updateTextFields();
    }

    return result;
}

function autofill() {

    const phoneEx = /^09\d{9}$/;

    // Changes error message to match regex
    if (mobileNoInput.value.match(phoneEx)) {
        getPerson(mobileNoInput.value);
        mobileNoInput.classList.remove('invalid');
        mobileNoInput.classList.add('valid');

    } else {
        mobileNoInput.classList.remove('valid');
        mobileNoInput.classList.add('invalid');
        mobileNoHelper.dataset.error = valTexts.phone;
    }

}

// For Validation //

//Returns a boolean, used for checking if one field is empty
function validateAllFields() {

    var elemHelper;
    var i;
    var formInput;

    for (i in inputList) {

        formInput = inputList[i];
        elemHelper = document.getElementById(formInput.id + "Helper");
        if (elemHelper.classList.contains('invalid')) {
            return false;
        }

    }

    for (i in checkboxList) {

        formInput = checkboxList[i];

        if (formInput.checked == false) {
            return false;
        }
    }

    return true;

}

//Checks if field is empty or not then changes class to "invalid" or "valid" respectively
function checkForEmptyFields() {

    var elemHelper;
    var i;
    var formInput;

    for (i in inputList) {

        formInput = inputList[i];
        formInputLabel = inputList[i].parentElement.children[1].innerText;

        if (formInput.value == "") {

            elemHelper = document.getElementById(formInput.id + "Helper");
            elemHelper.classList.remove('valid');
            elemHelper.classList.add('invalid');
            elemHelper.dataset.error = valTexts.required;

        } else {

            elemHelper = document.getElementById(formInput.id + "Helper");
            elemHelper.classList.remove('invalid');
            elemHelper.classList.add('valid');

        }

    }


}

//Materialize.css only updates the validation color on focus
//Instead of changing the css, this was an easier solution
function focusAllFields() {

    var i;

    for (i in inputList) {

        formInput = inputList[i];
        formInput.focus();
        formInput.blur();
    }
}

//Avoids using focusAllFields to change validation color on focus
$.validator.setDefaults({
    errorClass: 'invalid',
    validClass: "valid",
    errorPlacement: function (error, element) {
        $(element)
            .closest("form")
            .find("label[for='" + element.attr("id") + "']")
            .attr('data-error', error.text());
    },
    submitHandler: function (form) {
        console.log('form ok');
    }
});

document.getElementById('city').select
$("#reservationForm").validate({
    rules: {
        dateField: {
            date: true
        }
    }
});

function cityDropdown() {

    var i;
    var cityOption;

    for (i in cities) {

        cityOption = document.createElement("option");
        cityOption.value = cities[i];
        cityOption.innerText = cities[i];

        cityInput.appendChild(cityOption);
    }

    selectInit();
}

function selectInit() {
    var elems = document.querySelectorAll('select');
    var options = {};
    var instances = M.FormSelect.init(elems, options);
}
M.updateTextFields();