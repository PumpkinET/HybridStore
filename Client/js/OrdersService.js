'use strict';

var tempIndex;

function getAll() {
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "http://localhost:8080/appBackend/OrdersController?shopName=" + sessionStorage.getItem('storename'), true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                for (var i = 0; i < obj.length; i++) {
                    $("#tbody_list").append("<tr class='targetRow'>" +
                        "<td>" + "<span>" + obj[i].id + "</span></td>" +
                        "<td>" + "<span>" + obj[i].email + "</span></td>" +
                        "<td>" + "<span>" + obj[i].firstName + "</span></td>" +
                        "<td>" + "<span>" + obj[i].lastName + "</span></td>" +
                        "<td>" + "<span>" + obj[i].streetAdd + "</span></td>" +
                        "<td>" + "<span>" + obj[i].country + "</span></td>" +
                        "<td>" + "<span>" + obj[i].city + "</span></td>" +
                        "<td>" + "<span>" + obj[i].postalCode + "</span></td>" +
                        "<td>" + "<span>" + obj[i].totalPrice + "</span></td>" +
                        "<td>" + "<span>" + obj[i].items + "</span></td>" +
                        "<td>" + "<span style='display:none;'>" + obj[i].status + "</span>" +"<span>" + obj[i].statusValue + "</span></td>" +
                        "<td><i class='material-icons' data-toggle='modal' data-target='#exampleModal'>mode_edit</i></td></tr></tr>");
                }
            }
        }
        xhr.send();
    } catch (exception) {
        alert("Request failed");
    }
}

function editRow() {
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("PUT", "http://localhost:8080/appBackend/OrdersController?shopName=" + sessionStorage.getItem('storename'), true);

        var data = JSON.stringify({
            "id": $('#add-id').val(),
            "status": $('#add-status').val(),
        });

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                if (obj == true) {
                    var res = JSON.parse(data);
                    console.log();
                    $('#tbody_list .targetRow:nth-child(' + tempIndex + ') td:nth-child(11)').html('<span>' + $('#add-status option:selected').text() + "</span>");
                }
            }
        }

        xhr.send(data);
    } catch (exception) {
        alert("Request failed");
    }
}

$(document).ready(function () {
    $("#tbody_list .targetRow").click(function () {
        console.log($(this).index() + 1);
        tempIndex = $(this).index() + 1;
        $('#add-id').val($('#tbody_list .targetRow:nth-child(' + tempIndex + ') td:nth-child(1) span').text());
        $('#add-status').val($('#tbody_list .targetRow:nth-child(' + tempIndex + ') td:nth-child(11) span:nth-child(1)').text());
    });
});