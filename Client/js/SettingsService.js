'use strict';

$(document).ready(function () {
    $('#InputName').val(sessionStorage.storename);
});

function validateForm() {
    let res = true;
    var regex = new RegExp("^[a-zA-Z0-9]*$");

    var check = document.forms["updateStoreForm"]["owner"].value;
    if (check == "") {
        $('#error').append("<div>Owner must be filled out</div>");
        res = false;
    }
    var check = document.forms["updateStoreForm"]["thumbnail"].value;
    if (check == "") {
        $('#error').append("<div>Thumbnail must be filled out</div>");
        res = false;
    }
    var check = document.forms["updateStoreForm"]["description"].value;
    if (check == "") {
        $('#error').append("<div>Description must be filled out</div>");
        res = false;
    }
    var check = document.forms["updateStoreForm"]["ip"].value;
    if (check == "") {
        $('#error').append("<div>IP must be filled out</div>");
        res = false;
    }
    var check = document.forms["updateStoreForm"]["phone"].value;
    if (check == "") {
        $('#error').append("<div>Phone must be filled out</div>");
        res = false;
    }
    var check = document.forms["updateStoreForm"]["shopaddress"].value;
    if (check == "") {
        $('#error').append("<div>Shop address must be filled out</div>");
        res = false;
    }
    return res;
}

function getShop() {
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "http://10.100.102.40:8080/appBackend/ShopController?target=" + sessionStorage.getItem('storename'), true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                $('#InputOwner').val(obj.shopOwner);
                $('#InputThumbnail').val(obj.shopThumbnail);
                $('#InputDescription').val(obj.shopDescription);
                $('#InputIP').val(obj.shopIp);
                $('#InputPhone').val(obj.shopPhone);
                $('#InputShopAddress').val(obj.shopAddress);
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
 * this function is used to update store
 * status 200 : store update success
 * status 0 : offline server
 */
function updateStore() {
    $('#error').html("");
    if (validateForm() == true) {
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("PUT", "http://10.100.102.40:8080/appBackend/ShopController", true);
        var data = JSON.stringify({
            "shopName": $('#InputName').val(),
            "shopOwner": $('#InputOwner').val(),
            "shopThumbnail": $('#InputThumbnail').val(),
            "shopDescription": $('#InputDescription').val(),
            "shopIp": $('#InputIP').val(),
            "shopPhone": $('#InputPhone').val(),
            "shopAddress": $('#InputShopAddress').val(),
        });

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                $('#error').html(obj.errorMessage);
            } else if (xhr.status === 401) {
                alert('Unauthorized user!');
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