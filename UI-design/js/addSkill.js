function loadDoc() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4) {
            console.log(this.status, JSON.parse(this.responseText))
        }
    };
    xhttp.open("POST", "http://localhost:8080/ali_malikeh_war_exploded/users/1/add_skill", true);
    var request = { skillName : "HTML" };
    xhttp.send(request.toString());
}

loadDoc();