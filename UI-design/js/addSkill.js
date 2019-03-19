function loadDoc() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4) {
            console.log(this.status, JSON.parse(this.responseText))
        }
    };
    xhttp.open("POST", "http://localhost:8080/JobOunJa_IE_Spring_98_war/users/1/add_skill", true);
    var request = { skill : "Android" };
    xhttp.send(JSON.stringify(request));
}

loadDoc();