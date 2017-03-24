var request = new XMLHttpRequest();
var url = "middle.json"
var info;

request.open('GET', url);

request.onreadystatechange = function () {
    if ((request.status === 200) && (request.readyState === 4)) {
        info = JSON.parse(request.responseText);
        console.log(info);
    }
}
request.send();

