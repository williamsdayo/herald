function login() {

    const username = $("#username").val();
    const password = $("#password").val();

    const credentials = JSON.stringify({
        username: username,
        password: password
    });

    const client = axios.create({
        baseURL: 'http://localhost:8080/',
        headers: { 'Content-Type': 'application/json' }
    });

    client.post('users/authenticate', credentials)
        .then((response) => Cookies.set('token', response.data));
}
