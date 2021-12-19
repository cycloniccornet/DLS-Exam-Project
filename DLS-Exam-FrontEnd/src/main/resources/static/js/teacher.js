(() => {
    $('#div_h1').append("Students")
})();

function generateKey() {
    $('#welcome').remove();
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
                resetSessionKey();
                console.log("Duplication?")
            }
            getSessionStudents(key);
        }, 1000);
    })
}

function getSessionStudents(sessionKey) {

    fetch('/getSessionStudents/'+sessionKey)
        .then(result => result.json())
        .then(students => {
            console.log(students)
            $('#student_table').empty()
            for (let i = 0; i < students.length; i++) {
                $('#student_table').append(
                    '<tr>' +
                    '<td>'+students[i].firstName+'</td>' +
                    '<td>'+students[i].lastName+'</td>' +
                    '<td>'+students[i].mail+'</td></tr>'
                )
            }
        })
}

function getStatistics() {
    console.log("Link activated");
    $('#Welcome').remove();
    console.log("getStatistic has been called.");
    fetch("/getDataOnStudents")
        .then(result => result.json())
        .then(result => console.log(result))
}

function resetSessionKey() {
    fetch('/resetSessionKey')
        .then(result =>
            console.log("Session key has been reset.")
        )
}
