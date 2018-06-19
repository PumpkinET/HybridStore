var lastRow = 1;

function getAll() {
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "http://localhost:8080/Server/RoutineController", true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                render(obj);
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

function render(obj) {
    $(document).ready(function () {
        $('#calendar').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            defaultDate: '2018-03-12',
            navLinks: true, // can click day/week names to navigate views
            selectable: true,
            selectHelper: true,
            select: function (start, end) {
                var title = prompt('Event Title:');
                var eventData;
                if (title) {
                    eventData = {
                        title: title,
                        start: start,
                        end: end
                    };
                    $('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
                }
                $('#calendar').fullCalendar('unselect');

            },
            editable: true,
            eventLimit: true,
            events: [{
                title: obj[2].title,
                start: obj[2].startDate,
                end: obj[2].endDate
            }]
        });
    });
}

function addDate() {
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/Server/RoutineController", true);

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
            }
        }

        xhr.send(data);
    } catch (exception) {
        alert("Request failed");
    }
}

function editRow(index, isDone) {
    if (isDone == true) {
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
    }
    $('.targetRow:nth-child(' + (index + 1) + ') .toggle').toggle();
    $('.targetRow:nth-child(' + (index + 1) + ') .toggle2').toggle();
}