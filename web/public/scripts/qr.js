var queryString = decodeURIComponent(window.location.search);
queryString = queryString.substring(1);
var queries = queryString.split("?");

async function setInfo(){

    var getOptions = {
        method: 'GET',
        mode: 'cors',
        headers: { 'Content-Type': 'application/json' },
        cache: 'default'
    };

    var response = await fetch(reserve + "?id=" + queries[0], getOptions);
    var result = await response.json();

    var qrcode = new QRCode (document.getElementById("qrcode"), queries[0]);

    document.getElementById('eventName').innerText = result.event.name;
    document.getElementById('personName').innerText = result.person.firstName + " " + result.person.lastName;
}

setInfo();

