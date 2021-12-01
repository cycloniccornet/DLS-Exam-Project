(() => {
    $('#div_h1').append("Students")
})();

function generateKey() {
    $('#div_students').css('display', 'block');
    console.log("Generate Key function called.")
    fetch("/setSessionKey", {
        method: "POST"
    })
        .then(result => result.text())
        .then(key => {
        console.log(key)
        $('#welcome').empty();
        $('#div_h1').empty().append("Session Key \n");
        $('#div_key').empty().append(key);
        const countdownTime = new Date(new Date().valueOf() + 300000);
        // Start and update the count down every 1 second
        const x = setInterval(function () {
            const now = new Date().getTime();
            const distance = countdownTime - now;
            const minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
            const seconds = Math.floor((distance % (1000 * 60)) / 1000);
            $("#countdown").empty().append(minutes + "min " + seconds + "sec ");
            if (distance < 0) {
                clearInterval(x);
                $("#countdown").empty().append("Expired!");
            }
            getSessionStudents(key);
        }, 1000);
    })
}

function getSessionStudents(sessionKey) {
    fetch('/getSessionStudents/'+sessionKey)
        .then(result => result.json())
        .then(students => {
            console.log(students);
            // $('#student_table').empty().append(
            //    '<tr><td>'+students[0].firstname+'</td></tr>'
            // )
        })
}