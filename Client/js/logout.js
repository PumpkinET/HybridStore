$(document).ready(function () {
    $("#logout").click(function () {
        sessionStorage.removeItem("session");
        window.location = "index.html";
    });
    if (sessionStorage.getItem("session") == null) {
        window.location = "index.html";
    }
});