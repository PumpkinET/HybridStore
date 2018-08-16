'use strict';

class AddItem {
    constructor(column, value) {
        this.column = column;
        this.value = value;
    }
}

var dynamicData = [];
var tempIndex;

function getAll() {
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "http://localhost:8080/Server/ItemsController?dbName=" + sessionStorage.getItem('storename'), true);
        var imageIndex;
        var id;
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                console.log(obj);
                for (var i = 0; i < obj.columns.length; i++) {
                    dynamicData.push(new AddItem(obj.columns[i], '?'));
                    $("#tbody_add").append("<tr><td><input type='text' class='form-control' id='add-" + obj.columns[i] + "' placeholder='" + obj.columns[i] + "'></td></tr>");
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
                $("thead tr").append("<th scope='col'></th>");
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
                    str += "<td><i class='material-icons' data-toggle='modal' data-target='#exampleModal'>mode_edit</i></td></tr>";
                    $("#tbody_list").append(str);
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
        xhr.open("PUT", "http://localhost:8080/Server/ItemsController?dbName=" + sessionStorage.getItem('storename'), true);
        var data = [];
        for (var i = 0; i < dynamicData.length; i++) {
            data.push(new AddItem(dynamicData[i].column, $("#add-" + dynamicData[i].column).val()));
        }
        console.log(JSON.stringify(data));
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                if (obj == true) {
                    for (var i = 0; i < data.length; i++) {
                        $('#tbody_list .targetRow:nth-child(' + tempIndex + ') td:nth-child(' + (i + 1) + ')').html('<span>' + data[i].value + "</span>");
                    }
                }
            }
        }

        xhr.send(JSON.stringify(data));
    } catch (exception) {
        alert("Request failed");
    }
}


function deleteRow() {
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("DELETE", "http://localhost:8080/Server/ItemsController?dbName=" + +sessionStorage.getItem('storename') + "&item=" + $('#add-id').val(), true);
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
        xhr.open("POST", "http://localhost:8080/Server/ItemsController?dbName=" + sessionStorage.getItem('storename'), true);

        for (var i = 0; i < dynamicData.length; i++) {
            dynamicData[i].value = $("#add-" + dynamicData[i].column).val();
        }

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                if (obj == true) {
                    var str = "";
                    str += "<tr class='targetRow'>";

                    for (var i = 0; i < dynamicData.length; i++) {
                        if (dynamicData[i].column == "image") {
                            str += "<td><img src='" + dynamicData[i].value + "' height='50' width='50'></td>";
                        } else {
                            str += "<td><span>" + dynamicData[i].value + "</span></td>";
                        }
                    }
                    str += "<td><i class='material-icons' data-toggle='modal' data-target='#exampleModal'>mode_edit</i></td>";
                    $("#tbody_list").append(str);
                }
            }
        }

        xhr.send(JSON.stringify(dynamicData));
    } catch (exception) {
        alert("Request failed");
    }
}

$(document).ready(function () {
    $("#tbody_list .targetRow").click(function () {
        console.log($(this).index() + 1);
        tempIndex = $(this).index() + 1;

        for (var i = 0; i < dynamicData.length; i++) {
            if (dynamicData[i].column == "image") {
                $("#add-" + dynamicData[i].column).val($("#tbody_list .targetRow:nth-child(" + tempIndex + ") td:nth-child(" + (i + 1) + ") img").attr('src'));

            } else {
                $("#add-" + dynamicData[i].column).val($("#tbody_list .targetRow:nth-child(" + tempIndex + ") td:nth-child(" + (i + 1) + ") span").text());
            }
        }
    });
});