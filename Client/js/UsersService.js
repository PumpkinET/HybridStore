'use strict';

var tempIndex;

function getAll() {
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "http://localhost:8080/Server/UsersController?dbName=" + sessionStorage.getItem('storename'), true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                for (var i = 0; i < obj.length; i++) {
                    $("#tbody").append("<tr class='targetRow'>" +
                        "<td>" + "<span>" + obj[i].username + "</span></td>" +
                        "<td>" + "<span>" + obj[i].password + "</span></td>" +
                        "<td>" + "<span>" + obj[i].email + "</span></td>" +
                        "<td>" + "<span>" + obj[i].grade + "</span></td>" +
                        "<td>" + "<span>" + obj[i].name + "</span></td>" +
                        "<td>" + "<span>" + obj[i].age + "</span></td>" +
                        "<td>" + "<span>" + obj[i].address + "</span></td>" +
                        "<td>" + "<span>" + obj[i].id + "</span></td>" +
                        "<td><i class='material-icons' data-toggle='modal' data-target='#exampleModal'>mode_edit</i></td></tr>");
                }
            }
        }
        xhr.send();
    } catch (exception) {
        alert("Request failed");
    }
}

function deleteRow() {
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("DELETE", "http://localhost:8080/Server/UsersController?dbName=" + sessionStorage.getItem('storename') + "/" + $('#add-username').val(), true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                if (obj == true) {
                    $('.targetRow:nth-child(' + tempIndex + ')').hide();
                }
            }
        }
        xhr.send();
    } catch (exception) {
        alert("Request failed");
    }
}

function addRow() {
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/Server/UsersController?dbName=" + sessionStorage.getItem('storename'), true);

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
                if (obj == true) {
                    var res = JSON.parse(data);

                    $("#tbody").append("<tr class='targetRow'>" +
                        "<td>" + "<span>" + res.username + "</span></td>" +
                        "<td>" + "<span>" + res.password + "</span></td>" +
                        "<td>" + "<span>" + res.email + "</span></td>" +
                        "<td>" + "<span>" + res.grade + "</span></td>" +
                        "<td>" + "<span>" + res.name + "</span></td>" +
                        "<td>" + "<span>" + res.age + "</span></td>" +
                        "<td>" + "<span>" + res.address + "</span></td>" +
                        "<td>" + "<span>" + res.id + "</span></td>" +
                        "<td><i class='material-icons toggle2 editItem'>mode_edit</i></td><td><i class='material-icons' onClick='deleteRow(" + lastRow + ")'>delete</i></td></tr>");

                    lastRow++;
                }
            }
        }

        xhr.send(data);
    } catch (exception) {
        alert("Request failed");
    }
}

function editRow() {
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("PUT", "http://localhost:8080/Server/UsersController?dbName=" + sessionStorage.getItem('storename'), true);

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
                if (obj == true) {
                    var res = JSON.parse(data);
                    $('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(1)').html('<span>' + res.username + "</span>");
                    $('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(2)').html('<span>' + res.password + "</span>");
                    $('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(3)').html('<span>' + res.email + "</span>");
                    $('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(4)').html('<span>' + res.grade + "</span>");
                    $('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(5)').html('<span>' + res.name + "</span>");
                    $('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(6)').html('<span>' + res.age + "</span>");
                    $('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(7)').html('<span>' + res.address + "</span>");
                    $('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(8)').html('<span>' + res.id + "</span>");
                }
            }
        }

        xhr.send(data);
    } catch (exception) {
        alert("Request failed");
    }
}

$(document).ready(function () {
    $("#tbody .targetRow").click(function () {
        console.log($(this).index() + 1);
        tempIndex = $(this).index() + 1;
        $('#add-username').val($('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(1) span').text());
        $('#add-password').val($('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(2) span').text());
        $('#add-email').val($('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(3) span').text());
        $('#add-grade').val($('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(4) span').text());
        $('#add-name').val($('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(5) span').text());
        $('#add-age').val($('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(6) span').text());
        $('#add-address').val($('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(7) span').text());
        $('#add-id').val($('#tbody .targetRow:nth-child(' + tempIndex + ') td:nth-child(8) span').text());
    });
});