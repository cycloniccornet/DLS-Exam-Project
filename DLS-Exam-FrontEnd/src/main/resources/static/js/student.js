
function enterKey() {
    fetch("/enterSessionKey", {
        method: "POST",
        headers: {
            "content-type": "application/json"
        },
        body: JSON.stringify({
            key: $('#session_key').val()
        })
    })
        .then(result => result.text())
        .then(session => {
            console.log(session);
        })
}

function showSessionInputField() {
    $('#input_session_key').append()
}
