 function login(){

       const username = $("#username").val();
       const password = $("#password").val();

       const credentials = JSON.stringify({
              username: username,
              password: password
          });

        //$.post("authenticate", credentials, (token) => { alert ("Token: " + token) })

            $.ajax({
                   type: 'POST',
                   url: 'authenticate',
                   data: credentials,
                   contentType: 'application/json'
               }).done((token) => { alert("Token: " + token) })
 }
