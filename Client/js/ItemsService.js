var lastRow = 1;

function getAll() {
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "http://localhost:8080/Server/ItemsController", true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                console.log(obj);
                for (var i = 0; i < obj.columns.length; i++) {
                    $("thead tr").append("<th scope='col'>" + obj.columns[i] + "</th>");
                }
                $("thead tr").append("<th scope='col'></th>");
                $("thead tr").append("<th scope='col'></th>");

                for (var i = 0; i < obj.items.length; i++) {
                    var str = "";
                    str += "<tr class='targetRow'>";
                    for (var j = 0; j < obj.items[i].length; j++) { 
                        str += "<td><span class='toggle2'>" + obj.items[i][j] + "</span><input type='text' class='toggle' value='" + obj.items[i][j] + "'></td>";
                    }   
                    str += "<td><i class='material-icons toggle' onClick='editRow(" + i + ", true)'>done</i><i class='material-icons toggle2' onClick='editRow(" + i + ", false)'>mode_edit</i></td><td><i class='material-icons' onClick='deleteRow(" + i + ")'>delete</i></td></tr>";
                    $("tbody").append(str);
                }

                lastRow = i;
            }
        }
        xhr.send();
    } catch (exception) {
        alert("Request failed");
    }
}

function deleteRow(index) {
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("DELETE", "http://localhost:8080/Server/UsersController/" + $('.targetRow:nth-child(' + (index + 1) + ') td:nth-child(1) input').val(), true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                if (obj == true) {
                    $('.targetRow:nth-child(' + (index + 1) + ')').hide();
                    lastRow--;
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
        xhr.open("POST", "http://localhost:8080/Server/UsersController", true);

        var data = JSON.stringify({
            "username": $('#add-username').val(),
            "name": $('#add-name').val(),
            "age": $('#add-age').val(),
            "grade": $('#add-grade').val(),
            "address": $('#add-address').val()
        });

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                if (obj == true) {
                    var res = JSON.parse(data);

                    $("#tbody").append("<tr class='targetRow'>" +
                        "<td>" + "<span class='toggle2'>" + res.username + "</span><input type='text' disabled class='toggle' value='" + res.username + "'></td>" +
                        "<td>" + "<span class='toggle2'>" + res.name + "</span><input type='text' class='toggle' value='" + res.name + "'></td>" +
                        "<td>" + "<span class='toggle2'>" + res.age + "</span><input type='text' class='toggle' value='" + res.age + "'></td>" +
                        "<td>" + "<span class='toggle2'>" + res.grade + "</span><input type='text' class='toggle' value='" + res.grade + "'></td>" +
                        "<td>" + "<span class='toggle2'>" + res.address + "</span><input type='text' class='toggle' value='" + res.address + "'></td>" +
                        "<td><i class='material-icons toggle' onClick='editRow(" + lastRow + ", true)'>done</i><i class='material-icons toggle2' onClick='editRow(" + lastRow + ", false)'>mode_edit</i></td><td><i class='material-icons' onClick='deleteRow(" + lastRow + ")'>delete</i></td></tr>");

                    lastRow++;
                }
            }
        }

        xhr.send(data);
    } catch (exception) {
        alert("Request failed");
    }
}

function editRow(index, isDone) {
    /*if (isDone == true) {
        try {
            var xhr = new XMLHttpRequest();
            xhr.open("PUT", "http://localhost:8080/Server/UsersController", true);

            var data = JSON.stringify({
                "username": $('.targetRow:nth-child(' + (index + 1) + ') td:nth-child(1) input').val(),
                "name": $('.targetRow:nth-child(' + (index + 1) + ') td:nth-child(2) input').val(),
                "age": $('.targetRow:nth-child(' + (index + 1) + ') td:nth-child(3) input').val(),
                "grade": $('.targetRow:nth-child(' + (index + 1) + ') td:nth-child(4) input').val(),
                "address": $('.targetRow:nth-child(' + (index + 1) + ') td:nth-child(5) input').val()
            });

            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    var obj = JSON.parse(xhr.responseText);
                    if (obj == true) {
                        var res = JSON.parse(data);
                        $('.targetRow:nth-child(' + (index + 1) + ') td:nth-child(2)').html('<span>' + res.name + "</span>");
                        $('.targetRow:nth-child(' + (index + 1) + ') td:nth-child(3)').html('<span>' + res.age + "</span>");
                        $('.targetRow:nth-child(' + (index + 1) + ') td:nth-child(4)').html('<span>' + res.grade + "</span>");
                        $('.targetRow:nth-child(' + (index + 1) + ') td:nth-child(5)').html('<span>' + res.address + "</span>");
                    }
                }
            }

            xhr.send(data);
        } catch (exception) {
            alert("Request failed");
        }
    }*/
    console.log('test');
    $('.targetRow:nth-child(' + (index + 1) + ') .toggle').toggle();
    $('.targetRow:nth-child(' + (index + 1) + ') .toggle2').toggle();
}