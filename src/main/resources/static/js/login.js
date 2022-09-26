$("#login").ajaxForm(function (res) {
    if (res.success === true) {
        window.location.href = "/FromZerotoExpert";
    } else {
        alert(res.message);
    }
});