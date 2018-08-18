$(document).ready(function () {
    $("#logout").click(function () {
        sessionStorage.removeItem("user_info");
        window.location = "index.html";
    });
    if (sessionStorage.getItem("user_info") == null) {
        window.location = "index.html";
    }
    $('#shopName').text(sessionStorage.getItem("storename"));

    var user = JSON.parse(sessionStorage.getItem('user_info'));
    if(user.grade == 0) {
        $(".permission").hide();
    }
});