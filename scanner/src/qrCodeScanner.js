const qrWindow = window.qrcode;

const video = document.createElement("video");
const canvasElement = document.getElementById("qr-canvas");
const canvas = canvasElement.getContext("2d");

const btnStart = document.getElementById("btn-start");
const outputData = document.getElementById("outputData");

let scanning = false;

qrWindow.callback = scannedData => {
    if (scannedData) {

        const userAction = async () => {
            console.log("Calling API");
            const response = await fetch('https://api.ffaurora.org/api/v1/reservation?id=' + scannedData);
            const reservationDetails = await response.json();

            outputData.innerText = JSON.stringify(reservationDetails);
        }
        userAction();

        scanning = false;
        video.srcObject.getTracks().forEach(track => {
            track.stop();
        });
        startScanning();
    }
};

window.onload = function() {
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
