const qrWindow = window.qrcode;

const video = document.createElement("video");
const canvasElement = document.getElementById("qr-canvas");
const canvas = canvasElement.getContext("2d");

let scanning = false;

qrWindow.callback = scannedData => {
    if (scannedData) {

        // Call API & display results
        callApi(scannedData);

        // Reset scanner ticks
        scanning = false;
        video.srcObject.getTracks().forEach(track => {
            track.stop();
        });
        startScanning();
    }
};

window.onload = function() {
    getCurrentEvent();
    startScanning();
};

function startScanning() {
    navigator.mediaDevices
        .getUserMedia({ video: { facingMode: "environment" } })
        .then(function(stream) {
            scanning = true;
            video.setAttribute("playsinline", true); // required to tell iOS safari we don't want fullscreen
            video.srcObject = stream;
            video.play();
            tick();
            scan();
        });
};

function tick() {
    canvasElement.height = video.videoHeight;
    canvasElement.width = video.videoWidth;
    canvas.drawImage(video, 0, 0, canvasElement.width, canvasElement.height);

    scanning && requestAnimationFrame(tick);
}

function scan() {
    try {
        qrWindow.decode();
    } catch (e) {
        setTimeout(scan, 300);
    }
}

async function callApi(scannedData) {
    // Call API
    const response = await fetch('https://api.ffaurora.org/api/v1/reservation?id=' + scannedData);
    const reservationDetails = await response.json();

    // Display results
    document.getElementById("name").innerText = reservationDetails.person.firstName + " " + reservationDetails.person.lastName;
    document.getElementById("mobileNo").innerText = reservationDetails.person.mobileNo;
    document.getElementById("email").innerText = reservationDetails.person.email;
    document.getElementById("city").innerText = reservationDetails.person.city;
}

async function getCurrentEvent() {
    const response = await fetch('https://api.ffaurora.org/api/v1/event/current');
    const currentEvent = await response.json();

    document.getElementById("eventName").innerText = currentEvent.name;
}