var queryString = decodeURIComponent(window.location.search);
queryString = queryString.substring(1);
var queries = queryString.split("?");

async function setInfo(){
    document.getElementById('errorMsg').innerText = queries[0];
}

setInfo();

