var queryString = decodeURIComponent(window.location.search);
queryString = queryString.substring(1);
var queries = queryString.split("?");

async function setStatus(){

    var getOptions = {
        method: 'GET',
        mode: 'cors',
        headers: { 'Content-Type': 'application/json' },
        cache: 'default'
    };

    var response = await fetch(event_current, getOptions);
    var result = await response.json();

    var qrcode = new QRCode (document.getElementById("qrcode"), queries[0]);

    var p_text = document.getElementById('registrationText');
    p_text.innerText = result.name;
}

setStatus();

