(() => {
    $('#div_h1').append("Students")
})();

/*
(() => {
    fetch('/getAllStudents')
        .then(result => result.json())
        .then(students => {
            for (let i = 0;i<students.length;i++) {
                $('#div_students').append('<p style="text-align: center">'+students[i].mail+'</p>')
            }
        });
})();
 */

function generateKey() {
    console.log("Generate Key function called.")
    fetch("/setSessionKey", {
        method: "POST"
    })
        .then(result => result.text())
        .then(key => {
        console.log(key)
        $('#welcome').empty();
        $('#div_h1').empty().append("Key Generated: \n").append(key);
    })
}