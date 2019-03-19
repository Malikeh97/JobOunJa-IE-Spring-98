function loadDoc() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4) {
            console.log(this.status, JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", "http://localhost:8080/JobOunJa_IE_Spring_98_war/projects", true);
    xhttp.send();
}

loadDoc();