'use strict';

function validateForm() {
    let res = true;
    var check = document.forms["createStoreForm"]["name"].value;
    if (check == "") {
        $('#error').append("<div>Name must be filled out</div>");
        res = false;
    }
    var check = document.forms["createStoreForm"]["owner"].value;
    if (check == "") {
        $('#error').append("<div>Owner must be filled out</div>");
        res = false;
    }
    var check = document.forms["createStoreForm"]["thumbnail"].value;
    if (check == "") {
        $('#error').append("<div>Thumbnail must be filled out</div>");
        res = false;
    }
    var check = document.forms["createStoreForm"]["description"].value;
    if (check == "") {
        $('#error').append("<div>Description must be filled out</div>");
        res = false;
    }
    var check = document.forms["createStoreForm"]["ip"].value;
    if (check == "") {
        $('#error').append("<div>IP must be filled out</div>");
        res = false;
    }
    return res;
}
class CreateStore {
    constructor(storeName, ownerName, thumbnail, description, ip, category, fields = []) {
        this.storeName = storeName;
        this.ownerName = ownerName;
        this.thumbnail = thumbnail;
        this.description = description;
        this.ip = ip;
        this.category = category;
        this.fields = fields;
    }
}
$(document).ready(function () {

    var fieldInput;
    var data = new CreateStore;

    $('#addField').on('click', addField);

    /* append basic fields  (id, title, image, description, price)*/
    $('form ul').append("<li class='list-group-item'><span class='material-icons left'>vpn_key</span>ID</li>");
    data.fields.push({
        name: "id",
        type: "0"
    });

    /* append basic fields  (id, title, image, description, price)*/
    $('form ul').append("<li class='list-group-item'>title</li>");
    data.fields.push({
        name: "title",
        type: "0"
    });

    /* append basic fields  (id, title, image, description, price)*/
    $('form ul').append("<li class='list-group-item'>image</li>");
    data.fields.push({
        name: "image",
        type: "2"
    });

    /* append basic fields  (id, title, image, description, price)*/
    $('form ul').append("<li class='list-group-item'>description</li>");
    data.fields.push({
        name: "description",
        type: "2"
    });

    /* append basic fields  (id, title, image, description, price)*/
    $('form ul').append("<li class='list-group-item'>price</li>");
    data.fields.push({
        name: "price",
        type: "1"
    });

    /**
     * this function is used to add fields to the create store list
     */
    function addField() {
        if (fieldInput != null) {
            // if ($('#primaryKey').is(":checked")) {
            //     $('form ul').append("<li class='list-group-item'><span class='material-icons left'>vpn_key</span>" + fieldInput + "<span class='badge badge-danger badge-pill right'>X</span></li>");
            //     $('#primaryKey').prop('checked', false);
            //     $('#primaryKeyDiv').hide();
            //      data.push(new CreateStore($('#InputName').val(), $('#InputOwner').val(), fieldInput, $('#fieldType').val(), true));
            // } else {
            $('form ul').append("<li class='list-group-item'>" + fieldInput + " <span class='badge badge-danger badge-pill right'>X</span></li>");
            data.fields.push({
                name: fieldInput,
                type: $('#fieldType').val()
            });
            $('#exampleInputFields1').val("");
            fieldInput = null;
        }
    }

    $('#exampleInputFields1').on('input', function () {
        setFieldInput($(this));
    });

    function setFieldInput(inBox) {
        fieldInput = inBox.val();
    }
    $("#createStore").click(function () {
        createStore();
    });

    /**
     * this function is used to create store
     * status 200 : store create success
     * status 0 : offline server
     */
    function createStore() {
        $('#error').html("");
        if (validateForm() == true) {
            try {
                var xhr = new XMLHttpRequest();
                xhr.open("POST", "http://10.0.0.21:8080/Server/CreateStoreController", true);
                data.storeName = $('#InputName').val();
                data.ownerName = $('#InputOwner').val();
                data.thumbnail = $('#InputThumbnail').val();
                data.description = $('#InputDescription').val();
                data.ip = $('#InputIP').val();
                data.category = $('#exampleFormControlSelect1').val();
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {

                    } else if (xhr.status === 0) {
                        $('#error').html('Server is offline!');
                    }
                }

                xhr.send(JSON.stringify(data));
            } catch (exception) {
                alert("Request failed");
            }
        }
    }
});

/**
 * this function is get all available categories in application
 * status 200 : get success
 * status 0 : offline server
 */
function getCategories() {
    $('#error').html("");
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "http://localhost:8080/appBackend/CategoriesController", true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                for (var i = 0; i < obj.length; i++) {
                    $("#exampleFormControlSelect1").append("<option>" + obj[i].categoryName + "</option>");
                }
            }
        }
        xhr.send();
    } catch (exception) {
        alert("Request failed");
    }
}