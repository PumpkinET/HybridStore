$(document).ready(function () {
    $("#logout").click(function () {
        sessionStorage.removeItem("user_info");//remove details
        window.location = "index.html";
    });
    if (sessionStorage.getItem("user_info") == null) {
        window.location = "index.html";
    }
    $('#shopName').text(sessionStorage.getItem("storename"));

    var user = JSON.parse(sessionStorage.getItem('user_info'));
    if(user.grade == 0) {
        $(".permission").hide();//hide create, add and edit functions to the regular user
    }
    if(user.grade != 2) {
        $(".reportNav").hide();//hide report for regular and adminstrator user
        $(".settingsNav").hide();//hide report for regular and adminstrator user
    }
});