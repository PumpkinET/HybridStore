'use strict';
$(document).ready(function(){

    var fieldInput;

    $('#addField').on('click', addField);
    function addField() {
        if(fieldInput != null) 
        {   
            $('ul').append("<li class='list-group-item'>" + fieldInput + " <span class='badge badge-danger badge-pill right'>X</span></li>");
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


});