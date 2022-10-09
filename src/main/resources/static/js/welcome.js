welcome();

// var websocket = null;
//
// if ('WebSocket' in window) {
//     websocket = new WebSocket("ws://localhost1:8088/WebSocket");
// } else {
//     alert('当前浏览器不支持 WebSocket')
// }
//
// //连接成功建立的回调方法
// websocket.onopen = function() {
//     alert("WebSocket连接成功");
// }
//
// //接收到消息的回调方法
// websocket.onmessage = function(res) {
//     res = JSON.parse(res.data);
//     if (res[0] === "1") {
//         let htmlString = "<h2>当前在线人数:</h2><br>";
//         const users = JSON.parse(res);
//         for (let i = 1; i < users.length; ++i) {
//             htmlString = htmlString + "<h3>" + users[i] + "</h3><br>";
//         }
//         document.getElementById("insertOnlineUser").innerHTML = htmlString;
//     }
// }
// //连接关闭的回调方法
// websocket.onclose = function() {
//     setMessageInnerHTML("WebSocket连接关闭");
// }
// //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
// window.onbeforeunload = function() {
//     closeWebSocket();
// }
// //将消息显示在网页上
// function setMessageInnerHTML(innerHTML) {
//     document.getElementById('message').innerHTML += innerHTML + '<br/>';
// }
// //关闭WebSocket连接
// function closeWebSocket() {
//     websocket.close();
// }
//发送消息
// function send() {
//     var message = document.getElementById('insertOnlineUser').value;
//     websocket.send('{"msg":"' + message + '"}');
//     setMessageInnerHTML(message + "&#13;");
// }

stillAlive();
setInterval(stillAlive, 5 * 1000);
showOnlineUsers();
setInterval(showOnlineUsers, 7 * 1000);

function showOnlineCounts() {
    websocket.send("1");
}

function welcome() {
    $.ajax({
        url: '/Welcome',
        method: 'get',
        success: function (res) {
            if (res.success === true) {
                document.getElementById("insertWelcome").innerHTML = "<h2 style=\"text-align: center\">嗨, " + res.content + ", 欢迎您来到 from zero to expert</h2>";
            } else {
                window.location.href = "/Login";
            }
        }
    })
}

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
