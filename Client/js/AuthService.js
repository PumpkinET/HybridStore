'use strict';

function validateForm() {
    let res = true;
    var check = document.forms["loginForm"]["username"].value;
    if (check == "") {
        $('#error').append("<div>Username must be filled out</div>");
        res = false;
    }
    var check = document.forms["loginForm"]["password"].value;
    if (check == "") {
        $('#error').append("<div>Password must be filled out</div>");
        res = false;
    }
    var check = document.forms["loginForm"]["storename"].value;
    if (check == "") {
        $('#error').append("<div>Store name must be filled out</div>");
        res = false;
    }
    return res;
}

/**
 * this function is used to login
 * status 200 : login success
 * status 403 : login failed
 * status 0 : offline server
 */
function login() {
    $('#error').html("");
    if (validateForm() == true) {
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
                    /* store information in session storage */
                    sessionStorage.setItem("user_info", xhr.responseText); //user info
                    sessionStorage.setItem("session", obj.session); //session
                    sessionStorage.setItem('storename', $('#storename').val()); //store name
                    window.location.href = "items.html";
                } else if (xhr.status === 403) {
                    $('#error').html('Username not found!');
                } else if (xhr.status === 0) {
                    $('#error').html('Server is offline!');
                }
            }
            xhr.send(data);
        } catch (exception) {
            alert("Request failed");
        }
    }
}