function complain() {

    const title = $("#title").val();
    const content = $("#content").val();
    const tag = $("#tag").val();

    const complaint = JSON.stringify({
        title: title,
        content: content,
        tag: tag
    });

    const client = axios.create({
        baseURL: 'http://localhost:8080/',
        headers: { 'Content-Type': 'application/json' }
    });

    if (title.length > 0 && content.length > 0) {

        client.post('complaints', complaint)
            .then((_) => window.location.href = "complaints.html");
    } else {
        alert("You need to fill in the form before you submit")
    }
}
