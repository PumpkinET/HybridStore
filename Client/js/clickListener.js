$(document).ready(function () {
    $("tbody tr").click(function () {
        $(this).toggleClass("selectRow");
    });
});