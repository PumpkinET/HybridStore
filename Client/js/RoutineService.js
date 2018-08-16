var lastRow = 1;

function getAll() {
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "http://localhost:8080/Server/RoutineController?dbName=" + sessionStorage.getItem('storename'), true);

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

function render(obj) {
    var filter = [];
    for (var i = 0; i < obj.length; i++) {
        filter.push({
            title: obj[i].title,
            start: obj[i].startDate,
            end: obj[i].endDate
        });
    }

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
            }
        }

        xhr.send(data);
    } catch (exception) {
        alert("Request failed");
    }
}