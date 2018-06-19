'use strict';
class CreateStore {
    constructor(storeName, ownerName, name, type, primary) {
        this.storeName = storeName;
        this.ownerName = ownerName;
        this.name = name;
        this.type = type;
        this.primary = primary;
    }
}
$(document).ready(function () {

    var fieldInput;
    var data = [];
    $('#addField').on('click', addField);

    $('ul').append("<li class='list-group-item'>title</li>");
    data.push(new CreateStore($('#InputName').val(), $('#InputOwner').val(), "title", "0", false));

    $('ul').append("<li class='list-group-item'>image</li>");
    data.push(new CreateStore($('#InputName').val(), $('#InputOwner').val(), "image", "2", false));

    $('ul').append("<li class='list-group-item'>description</li>");
    data.push(new CreateStore($('#InputName').val(), $('#InputOwner').val(), "description", "0", false));

    function addField() {
        if (fieldInput != null) {
            if ($('#primaryKey').is(":checked")) {
                $('ul').append("<li class='list-group-item'><span class='material-icons left'>vpn_key</span>" + fieldInput + "<span class='badge badge-danger badge-pill right'>X</span></li>");
                $('#primaryKey').prop('checked', false);
                $('#primaryKeyDiv').hide();
                 data.push(new CreateStore($('#InputName').val(), $('#InputOwner').val(), fieldInput, $('#fieldType').val(), true));
            } else {
                $('ul').append("<li class='list-group-item'>" + fieldInput + " <span class='badge badge-danger badge-pill right'>X</span></li>");
                data.push(new CreateStore($('#InputName').val(), $('#InputOwner').val(), fieldInput, $('#fieldType').val(), false));
            }
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

    function createStore() {
        try {
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "http://localhost:8080/Server/CreateStoreController", true);

            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {}
            }
            xhr.send(JSON.stringify(data));
        } catch (exception) {
            alert("Request failed");
        }
    }
});