$("#login").ajaxForm(function (data) {
    // data = JSON.stringify(data);
    if (data === "true") {
        window.location.href="/user/FromZerotoExpert";
    } else {
        alert(data);
    }
});