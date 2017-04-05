function register() {

    const username = $("#username").val();
    const password = $("#password").val();
    const email = $("#email").val();

    const credentials = JSON.stringify({
        username: username,
        password: password,
        email: email
    });

    const client = axios.create({
        baseURL: 'http://localhost:8080/',
        headers: { 'Content-Type': 'application/json' }
    });

    client.post('students/register', credentials)
        .then((response) => Cookies.set('token', response.data))
        .then((_) => window.location.href = "complaints.html");
}


function login() {

    window.location.href = 'login.html'
}
