$.ajax({
    url: '/Welcome',
    method: 'get',
    success: function (res) {
        if (res.success === true) {
            document.getElementById("insert").innerHTML = "<h2>嗨, " + res.content + ", 欢迎您来到 from zero to expert";
        } else {
            window.location.href = "/Login";
        }
    }
})


function checkOffline() {
    let currentTime = new Date().getTime(),
        lastTime = new Date().getTime(),
        diff = 3000;

    $(document).on('mouseover', function () {
        lastTime = new Date().getTime();
    })

    let timer = setInterval(function () {
        currentTime = new Date().getTime();
        if (currentTime - lastTime > diff) {
            window.location.href = "/Login";
            clearInterval(timer);
        }
    }, 1000)


}
