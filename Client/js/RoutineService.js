var lastRow = 1;
filter = [];

function getAll() {
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
            }
            else if (xhr.status === 0) {
                alert('Server is offline!');
            }
        }
        xhr.send();
    } catch (exception) {
        alert("Request failed");
    }
}

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

function addDate() {
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/Server/RoutineController?dbName=" + sessionStorage.getItem('storename'), true);

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
            }
            else if (xhr.status === 0) {
                $('#error').html('Server is offline!');
            }
        }

        xhr.send(data);
    } catch (exception) {
        alert("Request failed");
    }
}

function getUsersList() {
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
            }
            else if (xhr.status === 0) {
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
