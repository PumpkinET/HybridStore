function login() {
    $('#error').html('');
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/Server/AuthController", true);

        var data = JSON.stringify({
            "username": $('#username').val(),
            "password": $('#password').val()
        });

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = xhr.responseText;
                sessionStorage.setItem("session", obj); 
                window.location.href = "items.html";
            } else if (xhr.status === 403) {
                $('#error').html('Username not found!');
            }
        }
        xhr.send(data);
    } catch (exception) {
        alert("Request failed");
    }
}