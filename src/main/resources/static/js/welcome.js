$.ajax({
    url: '/Welcome',
    method: 'get',
    success: function (res) {
        if (res.success === true) {
            document.getElementById("insertWelcome").innerHTML = "<h2>嗨, " + res.content + ", 欢迎您来到 from zero to expert";
        } else {
            window.location.href = "/Login";
        }
    }
})

stillAlive();
setInterval(stillAlive, 5 * 60 * 1000);
showOnlineUsers();
setInterval(showOnlineUsers, 10 * 60 * 1000);

function stillAlive() {
    $.ajax({
        url: '/StillAlive',
        method: 'post'
    })
}

function showOnlineUsers() {
    $.ajax({
        url: '/ShowOnlineUsers',
        method: 'get',
        success: function (res) {
            let htmlString = "<h2>当前在线人数:</h2><br>";
            const users = JSON.parse(res);
            for (let i = 0; i < users.length; ++i) {
                htmlString = htmlString + "<h3>" + users[i] + "</h3><br>";
            }
            document.getElementById("insertOnlineUser").innerHTML = htmlString;
        }
    })
}
