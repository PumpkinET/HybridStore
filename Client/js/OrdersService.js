function getAll() {
    try {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "http://localhost:8080/appBackend/OrdersController?shopName=" + sessionStorage.getItem('storename'), true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var obj = JSON.parse(xhr.responseText);
                for (var i = 0; i < obj.length; i++) {
                    $("tbody").append("<tr class='targetRow'>" +
                        "<td>" + "<span class='toggle2'>" + obj[i].email + "</span><input type='text' disabled class='toggle' value='" + obj[i].email + "'></td>" +
                        "<td>" + "<span class='toggle2'>" + obj[i].firstName + "</span><input type='text' class='toggle' value='" + obj[i].firstName + "'></td>" +
                        "<td>" + "<span class='toggle2'>" + obj[i].lastName + "</span><input type='text' class='toggle' value='" + obj[i].lastName + "'></td>" +
                        "<td>" + "<span class='toggle2'>" + obj[i].streetAdd + "</span><input type='text' class='toggle' value='" + obj[i].streetAdd + "'></td>" +
                        "<td>" + "<span class='toggle2'>" + obj[i].country + "</span><input type='text' class='toggle' value='" + obj[i].country + "'></td>" +
                        "<td>" + "<span class='toggle2'>" + obj[i].city + "</span><input type='text' class='toggle' value='" + obj[i].city + "'></td>" +
                        "<td>" + "<span class='toggle2'>" + obj[i].postalCode + "</span><input type='text' class='toggle' value='" + obj[i].postalCode + "'></td>" +
                        "<td>" + "<span class='toggle2'>" + obj[i].totalPrice + "</span><input type='text' class='toggle' value='" + obj[i].totalPrice + "'></td>" +
                        "<td>" + "<span class='toggle2'>" + obj[i].items + "</span><input type='text' class='toggle' value='" + obj[i].items + "'></td>" +
                        "</tr>");
                }
            }
        }
        xhr.send();
    } catch (exception) {
        alert("Request failed");
    }
}