
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
    $('#welcome').empty();
    $('#input_session_key').append('<form action="#">\n' +
        '    <input type="text" name="sessionKey" id="sessionKey"><br>\n' +
        '    <button type="submit" onclick="sendSessionKey()">Validate key</button>\n' +
        '</form>')
}

function showOverview() {
    $('#welcome').empty().append('Choose a subject')
    fetch('/getAllSubjects')
        .then(result => result.json())
        .then(subjects => {
            for (let i = 0; i < subjects.length; i++) {
                $('#input_session_key').append(
                    '<button class="btn btn-primary" style="padding: 10px" onclick="getSubjectWithId('+subjects[i].subjectId+')">'+subjects[i].subjectName+'</button>'
                )
            }
        })
}

function getSubjectWithId(subjectId) {
    $('#session_table').empty();
    $('#session_table').css('text-align', 'center').css('padding-left', '20%').css('padding-right', '20%').css('padding-top', '2%')
    fetch('/getAttendanceBySubjectId/'+subjectId)
        .then(result => result.json())
        .then(studentSessions => {
            // All Sessions that current student has attended.
            console.log(studentSessions);
            fetch('/getAllSessionsBySubjectId/'+subjectId)
                .then(result => result.json())
                .then(allSessions => {
                    // All sessions from current Subject.
                    console.log(allSessions)
                    $('#session_table').append(
                        '<table class="table table-bordered">' +
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
                            '<tr id="session['+allSessions[i].sessionId+']" style="background: red">' +
                            '   <td>'+allSessions[i].sessionDate+'</td><td>'+allSessions[i].scheduleStart+'</td><td>'+allSessions[i].scheduleEnd+'</td>' +
                            '</tr>'
                        )
                    }
                    for (let j = 0; j < studentSessions.length; j++) {
                        document.getElementById('session['+studentSessions[j].sessionId+']').style.background = 'green';
                        $('#session['+studentSessions[j].sessionId+']').css("background", "green");
                    }
                })
        })
}