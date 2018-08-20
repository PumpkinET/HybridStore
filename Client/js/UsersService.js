'use strict';

var tempIndex;

function getAll() {
    $('#error').html("");
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "http://localhost:8080/Server/UsersController?dbName=" + sessionStorage.getItem('storename'), true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                for (var i = 0; i < obj.length; i++) {
                    $("#tbody").append("<tr class='targetRow'>" +
                        "<td><span>" + obj[i].username + "</span></td>" +
                        "<td><span>" + obj[i].password + "</span></td>" +
                        "<td><span>" + obj[i].email + "</span></td>" +
                        "<td><span style='display:none;'>" + obj[i].grade + "</span><span>" + obj[i].value + "</span></td>" +
                        "<td><span>" + obj[i].name + "</span></td>" +
                        "<td><span>" + obj[i].age + "</span></td>" +
                        "<td><span>" + obj[i].address + "</span></td>" +
                        "<td><span>" + obj[i].id + "</span></td>" +
                        "<td><i class='material-icons permission' data-toggle='modal' data-target='#editModal'>mode_edit</i></td></tr>");
                }
            }
            else if (xhr.status === 0) {
                alert('Server is offline!');
            }
        }
        xhr.send();
    } catch (exception) {
        alert("Request failed");
    }
}

function deleteRow() {
    $('#error_edit').html("");
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("DELETE", "http://localhost:8080/Server/UsersController?session="+ sessionStorage.getItem('session')+"&dbName=" + sessionStorage.getItem('storename') + "&user=" + $('#edit-username').val(), true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                if (obj.result == true) {
                    $('.targetRow:nth-child(' + tempIndex + ')').hide();
                }
                $('#error_edit').html(obj.errorMessage);
            }
            else if (xhr.status === 401) {
                alert('Unauthorized user!');
            }
            else if (xhr.status === 0) {
                $('#error_edit').html('Server is offline!');
            }
        }
        xhr.send();
    } catch (exception) {
        alert("Request failed");
    }
}

function addRow() {
    $('#error').html("");
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/Server/UsersController?session="+ sessionStorage.getItem('session')+"&dbName=" + sessionStorage.getItem('storename'), true);

        var data = JSON.stringify({
            "username": $('#add-username').val(),
            "password": $('#add-password').val(),
            "email": $('#add-email').val(),
            "grade": $('#add-grade').val(),
            "name": $('#add-name').val(),
            "age": $('#add-age').val(),
            "address": $('#add-address').val(),
            "id": $('#add-id').val()
        });

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                if (obj.result == true) {
                    var res = JSON.parse(data);

                    $("#tbody").append("<tr class='targetRow'>" +
                        "<td><span>" + res.username + "</span></td>" +
                        "<td><span>" + res.password + "</span></td>" +
                        "<td><span>" + res.email + "</span></td>" +
                        "<td><span style='display:none;'>" + res.grade + "</span><span>" + $('#add-grade option:selected').text() + "</span></td>" +
                        "<td><span>" + res.name + "</span></td>" +
                        "<td><span>" + res.age + "</span></td>" +
                        "<td><span>" + res.address + "</span></td>" +
                        "<td><span>" + res.id + "</span></td>" +
                        "<td><i class='material-icons permission' data-toggle='modal' data-target='#editModal'>mode_edit</i></td></tr>");
                }
                $('#error').html(obj.errorMessage);
            }
            else if (xhr.status === 401) {
                alert('Unauthorized user!');
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

function editRow() {
    $('#error_edit').html("");
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("PUT", "http://localhost:8080/Server/UsersController?session="+ sessionStorage.getItem('session')+"&dbName=" + sessionStorage.getItem('storename'), true);

        var data = JSON.stringify({
            "username": $('#edit-username').val(),
            "password": $('#edit-password').val(),
            "email": $('#edit-email').val(),
            "grade": $('#edit-grade').val(),
            "name": $('#edit-name').val(),
            "age": $('#edit-age').val(),
            "address": $('#edit-address').val(),
            "id": $('#edit-id').val()
        });
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                if (obj.result == true) {
                    var res = JSON.parse(data);
                    $('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(1)').html('<span>' + res.username + "</span>");
                    $('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(2)').html('<span>' + res.password + "</span>");
                    $('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(3)').html('<span>' + res.email + "</span>");
                    $('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(4)').html('<span>' + $('#add-grade option:selected').text() + "</span>");
                    $('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(5)').html('<span>' + res.name + "</span>");
                    $('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(6)').html('<span>' + res.age + "</span>");
                    $('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(7)').html('<span>' + res.address + "</span>");
                    $('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(8)').html('<span>' + res.id + "</span>");
                }
                $('#error_edit').html(obj.errorMessage);
            }
            else if (xhr.status === 401) {
                alert('Unauthorized user!');
            }
            else if (xhr.status === 0) {
                $('#error_edit').html('Server is offline!');
            }
        }
        xhr.send(data);
    } catch (exception) {
        alert("Request failed");
    }
}

function getGrades() {
    $('#error').html("");
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "http://localhost:8080/Server/GradesController?dbName=" + sessionStorage.getItem('storename'), true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                var str = "";
                for (var i = 0; i < obj.length; i++)
                    str += "<option value='" + obj[i].id + "'>" + obj[i].value + "</option>";

                $('#add-grade').append(str);
                $('#edit-grade').append(str);
            }
            else if (xhr.status === 0) {
                $('#error').html('Server is offline!');
            }
        }
        xhr.send();
    } catch (exception) {
        alert("Request failed");
    }
}
$(document).ready(function () {
    $("#tbody .targetRow").click(function () {
        tempIndex = $(this).index() + 1;
        $('#edit-username').val($('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(1) span').text());
        $('#edit-password').val($('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(2) span').text());
        $('#edit-email').val($('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(3) span').text());
        $('#edit-grade').val($('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(4) span:nth-child(1)').text());
        $('#edit-name').val($('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(5) span').text());
        $('#edit-age').val($('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(6) span').text());
        $('#edit-address').val($('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(7) span').text());
        $('#edit-id').val($('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(8) span').text());
    });
});