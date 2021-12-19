let attended; let absent; let toCome;


function sendSessionKey() {
    const key = $('#sessionKey').val()
    fetch("/enterSessionKey/"+key, {
        method: "POST",
        headers: {
            "content-type": "application/json"
        },
        body: JSON.stringify({
            key: key
        })
    })
        .then(result => result.text())
        .then(session => console.log(session))
}

function showSessionInputField() {
    $('#session_table').empty();
    $('#welcome').empty();
    $('#input_session_key').empty().append('<form action="#">\n' +
        '    <input type="text" name="sessionKey" id="sessionKey"><br>\n' +
        '    <button type="submit" onclick="sendSessionKey()">Validate key</button>\n' +
        '</form>')
}

function showOverview() {

    $('#welcome').empty().append('Choose a subject');
    $('#input_session_key').empty();
    fetch('/getAllSubjects')
        .then(result => result.json())
        .then(subjects => {
            for (let i = 0; i < subjects.length; i++) {
                $('#input_session_key').append(
                    '<button class="btn btn-primary" style="margin-right: 10px" onclick="getSubjectWithId('+subjects[i].subjectId+')">'+subjects[i].subjectName+'</button>'
                )
            }
        })
}

function getSubjectWithId(subjectId) {
    const today = new Date();
    const date = today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate();
    fetch('/getAttendanceBySubjectId/'+subjectId)
        .then(result => result.json())
        .then(studentSessions => {
            // All Sessions that current student has attended.
            console.log(studentSessions);
            fetch('/getAllSessionsBySubjectId/'+subjectId)
                .then(result => result.json())
                .then(allSessions => {
                    attended = studentSessions.length;
                    absent = allSessions.length - studentSessions.length;
                    // All sessions from current Subject.
                    console.log(allSessions);
                    $('#session_table').empty().append(
                        '<table class="table table-bordered" style="border: 2px solid black">' +
                        '   <thead class="thead-dark">' +
                        '       <th>Session Date</th>' +
                        '       <th>Session Start Time</th>' +
                        '       <th>Session End Time</th>' +
                        '   </thead>' +
                        '<tbody id="table_body"></tbody>' +
                        '</table>'
                    )
                    for (let i = 0; i < allSessions.length; i++) {
                        $('#table_body').append(
                            '<tr id="session['+allSessions[i].sessionId+']" style="background: #da0707">' +
                            '   <td>'+allSessions[i].sessionDate+'</td><td>'+allSessions[i].scheduleStart+'</td><td>'+allSessions[i].scheduleEnd+'</td>' +
                            '</tr>'
                        )
                        const sessionDate = new Date(allSessions[i].sessionDate)
                        if (sessionDate > today){
                            console.log("1 - Kommer du her?")
                            document.getElementById('session['+allSessions[i].sessionId+']').style.background = 'grey';
                            toCome++;
                            absent--;
                        }
                    }
                    for (let i = 0; i < studentSessions.length; i++) {
                        console.log("2 - Kommer du her?")
                        document.getElementById('session['+studentSessions[i].sessionId+']').style.background = 'green';
                        $('#session['+studentSessions[i].sessionId+']').css("background", "green");
                    }
                    Array.prototype.reduce = undefined;
                    google.charts.load('current', {'packages':['corechart']});
                    google.charts.setOnLoadCallback(drawChart);

                    function drawChart() {

                        const data = google.visualization.arrayToDataTable([
                            ['Status', 'Hours per Day'],
                            ['Attended', attended],
                            ['Absent', absent],
                            ['To Come', toCome]
                        ]);

                        const options = {
                            title: 'Oversigt over fravær i procent.',
                            slides: [{color: 'green'}, {color: 'red'}, {color: 'grey'}],
                            backgroundColor: 'transparent',
                            fontSize: '13',
                            fontName: 'Bahnschrift',
                            width: '525',
                        };

                        const chart = new google.visualization.PieChart(document.getElementById('pieChart'));
                        chart.draw(data, options);
                    }
                })
        })
}

