function login() {
    $('#error').html("");
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/Server/AuthController?dbName=" + $('#storename').val(), true);
        var data = JSON.stringify({
            "username": $('#username').val(),
            "password": $('#password').val()
        });

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                sessionStorage.setItem("user_info", xhr.responseText);
                sessionStorage.setItem("session", obj.session);
                sessionStorage.setItem('storename', $('#storename').val());
                window.location.href = "items.html";
            } else if (xhr.status === 403) {
                $('#error').html('Username not found!');
            }
            else if (xhr.status === 0) {
                $('#error').html('Server is offline!');
            }
        }
        xhr.send(data);
    } catch (exception) {
        alert("Request failed");
    }
}