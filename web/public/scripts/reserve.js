const reservationForm = document.getElementById('reservationForm');

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

    return result;
}