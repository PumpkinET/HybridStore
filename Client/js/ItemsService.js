'use strict';



class AddItem {
    constructor(column, value) {
        this.column = column;
        this.value = value;
    }
}

var dynamicData = [];
var tempIndex;

function validateAddItem() {
    let res = true;
    for (var i = 0; i < dynamicData.length; i++) {
        var check = $("#add-" + dynamicData[i].column).val()
        if (check == "") {
            $('#error').append("<div>" + dynamicData[i].column + " must be filled out</div>");
            res = false;
        }
    }
    return res;
}
function validateEditItem() {
    let res = true;
    for (var i = 0; i < dynamicData.length; i++) {
        var check = $("#edit-" + dynamicData[i].column).val()
        if (check == "") {
            $('#error_edit').append("<div>" + dynamicData[i].column + " must be filled out</div>");
            res = false;
        }
    }
    return res;
}
/**
 * this function is used to get all the items
 * status 200 : get success
 * status 0 : offline server
 */
function getAll() {
    $('#error').html("");
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "http://localhost:8080/Server/ItemsController?dbName=" + sessionStorage.getItem('storename'), true);
        var imageIndex;
        var id;
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                for (var i = 0; i < obj.columns.length; i++) {
                    dynamicData.push(new AddItem(obj.columns[i], '?'));
                    $("#tbody_add").append("<tr><td><input type='text' class='form-control'  id='add-" + obj.columns[i] + "' placeholder='" + obj.columns[i] + "'></td></tr>");
                    $("#tbody_edit").append("<tr><td><input type='text' class='form-control' id='edit-" + obj.columns[i] + "' placeholder='" + obj.columns[i] + "'></td></tr>");
                }
                for (var i = 0; i < obj.columns.length; i++) {
                    $("#thead_list tr").append("<th scope='col'>" + obj.columns[i] + "</th>");

                    if (obj.columns[i] == "image") {
                        imageIndex = i;
                    }
                    if (obj.columns[i] == "id") {
                        id = i;
                    }
                }
                $("thead tr").append("<th scope='col' class='permission'></th>");
                for (var i = 0; i < obj.items.length; i++) {
                    var str = "";
                    str += "<tr class='targetRow'>";
                    for (var j = 0; j < obj.items[i].length; j++) {
                        if (j == imageIndex) {
                            str += "<td><img src='" + obj.items[i][j] + "' height='50' width='50'></td>";
                        } else {
                            str += "<td><span>" + obj.items[i][j] + "</span></td>";
                        }
                    }
                    str += "<td><i class='material-icons permission' data-toggle='modal' data-target='#editItemModal'>mode_edit</i></td></tr>";
                    $("#tbody_list").append(str);
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
 * this function is used to edit item
 * status 200 : edit success
 * status 401 : unauthorized user
 * status 0 : offline server
 */
function editRow() {
    $('#error_edit').html("");
    if (validateEditItem() == true) {
        try {
            var xhr = new XMLHttpRequest();
            xhr.open("PUT", "http://localhost:8080/Server/ItemsController?session=" + sessionStorage.getItem('session'), true);
            var data = [];
            for (var i = 0; i < dynamicData.length; i++) {
                data.push(new AddItem(dynamicData[i].column, $("#edit-" + dynamicData[i].column).val()));
            }
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    var obj = JSON.parse(xhr.responseText);
                    if (obj.result == true) {
                        for (var i = 0; i < data.length; i++) {
                            $('#tbody_list .targetRow:nth-child(' + tempIndex + ') td:nth-child(' + (i + 1) + ')').html('<span>' + data[i].value + "</span>");
                        }
                    }
                    $('#error_edit').html(obj.errorMessage);
                } else if (xhr.status === 401) {
                    alert('Unauthorized user!');
                } else if (xhr.status === 0) {
                    $('#error_edit').html('Server is offline!');
                }
            }

            xhr.send(JSON.stringify(data));
        } catch (exception) {
            alert("Request failed");
        }
    }
}

/**
 * this function is used to delete item
 * status 200 : delete success
 * status 401 : unauthorized user
 * status 0 : offline server
 */
function deleteRow() {
    $('#error_edit').html("");
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("DELETE", "http://localhost:8080/Server/ItemsController?session=" + sessionStorage.getItem('session') + "&item=" + $('#edit-id').val(), true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                if (obj.result == true) {
                    $('.targetRow:nth-child(' + tempIndex + ')').hide();
                }
                $('#error_edit').html(obj.errorMessage);
            } else if (xhr.status === 401) {
                alert('Unauthorized user!');
            } else if (xhr.status === 0) {
                $('#error_edit').html('Server is offline!');
            }
        }
        xhr.send();
    } catch (exception) {
        alert("Request failed");
    }
}

/**
 * this function is used to add item
 * status 200 : add success
 * status 401 : unauthorized user
 * status 0 : offline server
 */
function addRow() {
    $('#error').html("");
    if (validateAddItem() == true) {
        try {
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "http://localhost:8080/Server/ItemsController?session=" + sessionStorage.getItem('session'), true);

            for (var i = 0; i < dynamicData.length; i++) {
                dynamicData[i].value = $("#add-" + dynamicData[i].column).val();
            }

            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    var obj = JSON.parse(xhr.responseText);
                    if (obj.result == true) {
                        var str = "";
                        str += "<tr class='targetRow'>";

                        for (var i = 0; i < dynamicData.length; i++) {
                            if (dynamicData[i].column == "image") {
                                str += "<td><img src='" + dynamicData[i].value + "' height='50' width='50'></td>";
                            } else {
                                str += "<td><span>" + dynamicData[i].value + "</span></td>";
                            }
                        }
                        str += "<td><i class='material-icons permission' data-toggle='modal' data-target='#editItemModal'>mode_edit</i></td>";
                        $("#tbody_list").append(str);
                    }
                    $('#error').html(obj.errorMessage);
                } else if (xhr.status === 401) {
                    alert('Unauthorized user!');
                } else if (xhr.status === 0) {
                    $('#error').html('Server is offline!');
                }
            }

            xhr.send(JSON.stringify(dynamicData));
        } catch (exception) {
            alert("Request failed");
        }
    }
}

$(document).ready(function () {
    $("#tbody_list .targetRow").click(function () {
        $('#error_edit').html("");
        tempIndex = $(this).index() + 1;

        for (var i = 0; i < dynamicData.length; i++) {
            if (dynamicData[i].column == "image")
                $("#edit-" + dynamicData[i].column).val($("#tbody_list .targetRow:nth-child(" + tempIndex + ") td:nth-child(" + (i + 1) + ") img").attr('src'));
            else
                $("#edit-" + dynamicData[i].column).val($("#tbody_list .targetRow:nth-child(" + tempIndex + ") td:nth-child(" + (i + 1) + ") span").text());
        }
    });
});