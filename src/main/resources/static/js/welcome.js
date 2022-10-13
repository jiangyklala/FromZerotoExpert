let flagStillAlive = true;  // 心跳信息开关按钮
let flagShowOnlineUsers = true; // 显示在线人数开关按钮
let timeStillAlive = 5 * 1000;
let timeShowOnlineUsers = 7 * 1000;

window.onload = windowOnload;
window.onbeforeunload = windowOnbeforeunload;

welcome();
stillAliveTimer();
showOnlineUsersTimer();




function windowOnload() {
    showOnlineUsers();
    showIPPVUV();

}

function windowOnbeforeunload() {
    flagStillAlive = false;
    flagShowOnlineUsers = false;
}

function setTimer(func, time, flag) {
    function exe() {
        if (!flag) return;
        func;
        setTimeout(exe, time)
    }
}

function showIPPVUV() {
    let time = new Date();
    let time2 = new Date();
    let today = getNowFormatDate("", time);
    let yesterday = getNowFormatDate("", new Date(time.setDate(time.getDate() - 1)));

    let htmlString = "<table><tr><th>DATE</th><th>IP</th><th>PV</th><th>UV</th></tr>" +
        "<tr><td>" + getNowFormatDate("-", time2) + "</td><td>" + getIP(today) + "</td><td>"+ getPV(today) + "</td><td>" + getUV(today) + "</td></tr>" +
        "<tr><td>" + getNowFormatDate("-", new Date(time2.setDate(time2.getDate() - 1))) + "</td><td>" + getIP(yesterday) + "</td><td>"+ getPV(yesterday) + "</td><td>" + getUV(yesterday) + "</td></tr></table>";


    console.log(htmlString);
    document.getElementById("insertIPUVPV").innerHTML = htmlString;
}

function getIP(date) {
    let ans = "";
    $.ajax({
        url: '/GetIP',
        method: 'get',
        data: {
            date : date
        },
        async: false,
        dataType: "json",
        success: function (res) {
            ans = String(res);
        }
    })
    return ans;
}

function getUV(date) {
    let ans;
    $.ajax({
        url: '/GetUV',
        method: 'get',
        data: {
            date : date
        },
        async: false,
        dataType: "json",
        success: function (res) {
            ans = res;
        }
    })
    return ans;
}

function getPV(date) {
    let ans;
    $.ajax({
        url: '/GetPV',
        method: 'get',
        data: {
            date : date
        },
        async: false,
        dataType: "json",
        success: function (res) {
            ans = res;
        }
    })
    return ans;
}


//获取当前时间，格式YYYY-MM-DD
function getNowFormatDate(separator, date) {
    const year = date.getFullYear();
    let month = date.getMonth() + 1;
    let strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    return year + separator + month + separator + strDate;
}

function showOnlineUsersTimer() {
    if (!flagShowOnlineUsers) return;
    stillAlive();
    setTimeout(stillAliveTimer, timeShowOnlineUsers);
}

// 显示当前在线人数
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

function stillAliveTimer() {
    if (!flagStillAlive) return;
    stillAlive();
    setTimeout(stillAliveTimer, timeStillAlive);
}

// 发送心跳信息
function stillAlive() {
    $.ajax({
        url: '/StillAlive',
        method: 'post'
    })
}

// 欢迎语句
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

// function showOnlineCounts() {
//     websocket.send("1");
// }
