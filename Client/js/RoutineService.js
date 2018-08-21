filter = [];

function validateAddRoutine() {
    let res = true;
    var check = $("#add-title").val()
    if (check == "") {
        $('#error').append("<div>Title must be filled out</div>");
        res = false;
    }
    return res;
}

/**
 * this function is used to get all the routine dates
 * status 200 : get success
 * status 0 : offline server
 */
function getAll() {
    $('#error').html("");
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "http://localhost:8080/Server/RoutineController?dbName=" + sessionStorage.getItem('storename'), true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                for (var i = 0; i < obj.length; i++) {
                    filter.push({
                        title: "[" + obj[i].targetuser + "] " + obj[i].title,
                        start: obj[i].startDate,
                        end: obj[i].endDate
                    });
                }
                render();
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
 * this function is used to render the calendar
 */
function render() {
    $(document).ready(function () {
        $('#calendar').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            navLinks: true,
            selectable: false,
            selectHelper: true,
            editable: false,
            eventLimit: true,
            events: filter
        });
    });
}

/**
 * this function is used to add date
 * status 200 : add success
 * status 401 : unauthorized user
 * status 0 : offline server
 */
function addDate() {
    $('#error').html("");
    if (validateAddRoutine() == true) {
        try {
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "http://localhost:8080/Server/RoutineController?session=" + sessionStorage.getItem('session'), true);

            var data = JSON.stringify({
                "adminuser": $('#add-adminuser').val(),
                "targetuser": $('#add-targetuser').val(),
                "title": $('#add-title').val(),
                "startDate": $('#add-start-date').val(),
                "endDate": $('#add-end-date').val()
            });

            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    var obj = JSON.parse(xhr.responseText);
                    if (obj.result == true) {
                        filter.push({
                            title: "[" + $('#add-targetuser').val() + "] " + $('#add-title').val(),
                            start: $('#add-start-date').val(),
                            end: $('#add-end-date').val()
                        });
                    }
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

/**
 * this function is used to get all the users in the store (username, grade)
 * status 200 : get success
 * status 0 : offline server
 */
function getUsersList() {
    $('#error').html("");
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "http://localhost:8080/Server/UserGradesController?dbName=" + sessionStorage.getItem('storename'), true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                var admin = "";
                var all = "";
                for (var i = 0; i < obj.length; i++) {
                    if (obj[i].grade != 0) {
                        admin += "<option value='" + obj[i].username + "'>" + obj[i].username + "</option>";
                    }
                    all += "<option value='" + obj[i].username + "'>" + obj[i].username + "</option>";
                }

                $('#add-adminuser').append(admin);
                $('#add-targetuser').append(all);
            } else if (xhr.status === 0) {
                $('#error').html('Server is offline!');
            }
        }
        xhr.send();
    } catch (exception) {
        alert("Request failed");
    }
}

$(document).ready(function () {
    document.getElementById('add-start-date').valueAsDate = new Date();
    document.getElementById('add-end-date').valueAsDate = new Date();
});