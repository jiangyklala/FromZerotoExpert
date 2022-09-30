

function welcome() {
    var firstLogin = getCookie("firstLogin");
    if (firstLogin === "1") {
        document.write("嗨，欢迎您再次来到 from zero to expert.")
    } else {
        setCookie("firstLogin", "1", 24);
        document.write("嗨，欢迎您来到 from zero to expert.")
    }
}

// time单位为 hour
function setCookie(cookieKey, cookieValue, time) {
    const date = new Date(new Date().getTime() + time * 60 * 60 * 1000);
    document.cookie = cookieKey + "=" + cookieValue + "; expires=" + date.toUTCString();
}

function getCookie(cookieKey) {
    var name = cookieKey + "=";
    var cookieSplit = document.cookie.split(";");
    for (var i = 0; i < cookieSplit.length; ++i) {
        var cookie = cookieSplit[i].trim(); // 去除空格
        if (cookie.indexOf(name) === 0) {
            return cookie.substring(name.length, cookie.length);
        }
    }
    return "";
}