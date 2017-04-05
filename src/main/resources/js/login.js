const client = axios.create({
    baseURL: 'http://localhost:8080/',
    headers: { 'Content-Type': 'application/json' }
});

function studentLogin() {

    const username = $("#student_username").val();
    const password = $("#student_password").val();

    const credentials = JSON.stringify({
        username: username,
        password: password
    });

    client.post('students/authenticate', credentials)
        .then((response) => {
            if (response.data != "KO") return response.data;
            else {
                const err = "Your username or password is incorrect";
                alert(err);
                throw err
            }
        })
        .then((token) => Cookies.set('token', token))
        .then((_) => Cookies.set('userType', "Student"))
        .then((_) => window.location.href = "complaints.html");
}

function counsellorLogin() {

    const username = $("#counsellor_username").val();
    const password = $("#counsellor_password").val();

    const credentials = JSON.stringify({
        username: username,
        password: password
    });

    client.post('counsellors/authenticate', credentials)
        .then((response) => {
            if (response.data != "KO") return response.data;
            else {
                const err = "Your username or password is incorrect";
                alert(err);
                throw err
            }
        })
        .then((token) => Cookies.set('token', token))
        .then((_) => Cookies.set('userType', "Counsellor"))
        .then((_) => window.location.href = "issues.html");
}

function register() {

    window.location.href = 'register.html'
}