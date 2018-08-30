'use strict';
var doc = new jsPDF();
var tempIndex;

function validateInvoice() {
    let res = true;
    var check = $("#add-invoice-company").val()
    if (check == "") {
        $('#error').append("<div>Invoice company must be filled out</div>");
        res = false;
    }
    var check = $("#add-invoice-number").val()
    if (check == "") {
        $('#error').append("<div>Invoice number must be filled out</div>");
        res = false;
    }
    return res;
}

/**
 * this function is used to get all orders from application database
 * status 200 : get success
 * status 0 : offline server
 */
function getAll() {
    $('#error').html("");
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "http://localhost:8080/appBackend/OrdersController?shopName=" + sessionStorage.getItem('storename'), true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                for (var i = 0; i < obj.length; i++) {
                    $("#tbody_list").append("<tr class='targetRow'>" +
                        "<td><span>" + obj[i].id + "</span></td>" +
                        "<td><span>" + obj[i].email + "</span></td>" +
                        "<td><span>" + obj[i].firstName + "</span></td>" +
                        "<td><span>" + obj[i].lastName + "</span></td>" +
                        "<td><span>" + obj[i].streetAdd + "</span></td>" +
                        "<td><span>" + obj[i].country + "</span></td>" +
                        "<td><span>" + obj[i].city + "</span></td>" +
                        "<td><span>" + obj[i].phoneNumber + "</span></td>" +
                        "<td><span>" + obj[i].postalCode + "</span></td>" +
                        "<td><span>" + obj[i].totalPrice + "</span></td>" +
                        "<td><span>" + obj[i].items + "</span></td>" +
                        "<td><span style='display:none;'>" + obj[i].status + "</span><span>" + obj[i].statusValue + "</span></td>" +
                        "<td><span>" + obj[i].date + "</span></td>" +
                        "<td><i class='material-icons' data-toggle='modal' data-target='#exampleModal'>mode_edit</i></td></tr></tr>");
                }
            } else if (xhr.status === 0) {
                alert('Server is offline!');
            }
        }
        xhr.send();
    } catch (exception) {
        alert("Request failed");
    }
}

/**
 * this function is used to edit order (update statuts)
 * status 200 : edit success
 * status 0 : offline server
 */
function editRow() {
    $('#error').html("");
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
                    $('#tbody_list .targetRow:nth-child(' + tempIndex + ') td:nth-child(11)').html('<span>' + $('#add-status option:selected').text() + "</span>");
                    $('#error').html("Succesfully updated!");
                }
            } else if (xhr.status === 0) {
                $('#error').html('Server is offline!');
            }
        }

        xhr.send(data);
    } catch (exception) {
        alert("Request failed");
    }
}

/**
 * this function is used to get items selection details
 * used to generate invoice
 * doc parameter : pdf document
 * itemsString parameter : items selection
 * status 200 : get success
 * status 0 : offline server
 */
function getItems(doc, itemsString) {
    $('#error').html("");
    try {
        var xhr = new XMLHttpRequest();
        var fixed = "";
        var items = itemsString.split(",");
        for (var key in items) {
            var itemId = items[key].split("x");
            if (!isNaN(parseInt(itemId[0], 10))) {
                fixed += itemId[0]+",";
            }
        }
        console.log("FIXED " + fixed);
        xhr.open("GET", "http://localhost:8080/Server/ItemsController?dbName=" + sessionStorage.getItem('storename') + "&filter=true&items=" + fixed, true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var lastY = 120;
                var obj = JSON.parse(xhr.responseText);
                console.log(JSON.stringify(obj));
                var items = itemsString.split(",");
                for (var key in items) {
                    var itemId = items[key].split("x");
                    if (!isNaN(parseInt(itemId[0], 10))) {
                        for (var i = 0; i < obj.length; i++) {
                            console.log(obj[i]);
                            if (obj[i].id == itemId[0]) {
                                console.log("here");
                                doc.text(20, lastY, obj[i].title + "(x" + parseInt(itemId[1], 10) + ")");
                                doc.text(200, lastY, (obj[i].price * parseInt(itemId[1], 10)) + "", null, null, 'right');
                                lastY += 10;
                            }
                        }
                    }
                }
                doc.save($('#add-invoice-company').val() + "-" + $('#add-invoice-number').val());
            } else if (xhr.status === 0) {
                alert('Server is offline!');
            }
        }
        xhr.send();
    } catch (exception) {
        alert("Request failed");
    }
}
/**
 * this function is used to generate invoice pdf
 */
function invoice() {
    $('#error').html('');
    if (validateInvoice() == true) {
        doc.setFontType("bold");
        doc.text(105, 10, 'Company name : ' + $('#add-invoice-company').val(), null, null, 'center');
        doc.text(105, 20, 'Invoice number : ' + $('#add-invoice-number').val(), null, null, 'center');
        doc.setFontType("normal");
        doc.text(105, 30, ('Email : ' + $('#tbody_list .targetRow:nth-child(' + tempIndex + ') td:nth-child(2) span').text()), null, null, 'center');
        doc.text(105, 40, ('First name : ' + $('#tbody_list .targetRow:nth-child(' + tempIndex + ') td:nth-child(3) span').text()), null, null, 'center');
        doc.text(105, 50, ('Last name : ' + $('#tbody_list .targetRow:nth-child(' + tempIndex + ') td:nth-child(4) span').text()), null, null, 'center');
        doc.text(105, 60, ('Street address : ' + $('#tbody_list .targetRow:nth-child(' + tempIndex + ') td:nth-child(5) span').text()), null, null, 'center');
        doc.text(105, 70, ('Country : ' + $('#tbody_list .targetRow:nth-child(' + tempIndex + ') td:nth-child(6) span').text()), null, null, 'center');
        doc.text(105, 80, ('City : ' + $('#tbody_list .targetRow:nth-child(' + tempIndex + ') td:nth-child(7) span').text()), null, null, 'center');
        doc.text(105, 90, ('Phone Number : ' + $('#tbody_list .targetRow:nth-child(' + tempIndex + ') td:nth-child(8) span').text()), null, null, 'center');
        doc.text(105, 100, ('Postal code : ' + $('#tbody_list .targetRow:nth-child(' + tempIndex + ') td:nth-child(9) span').text()), null, null, 'center');
        doc.setFontType("bold");
        doc.text(105, 110, ('Total price : ' + $('#tbody_list .targetRow:nth-child(' + tempIndex + ') td:nth-child(10) span').text() + " N.I.S."), null, null, 'center');

        var itemsString = $('#tbody_list .targetRow:nth-child(' + tempIndex + ') td:nth-child(11) span:nth-child(1)').text();
        getItems(doc, itemsString);
    }
}

$(document).ready(function () {
    $("#tbody_list .targetRow").click(function () {
        tempIndex = $(this).index() + 1;
        $('#add-invoice-company').val(sessionStorage.getItem('storename'));
        $('#add-id').val($('#tbody_list .targetRow:nth-child(' + tempIndex + ') td:nth-child(1) span').text());
        $('#add-status').val($('#tbody_list .targetRow:nth-child(' + tempIndex + ') td:nth-child(11) span:nth-child(1)').text());
    });
});