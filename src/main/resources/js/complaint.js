 function login(){

       const title = $("#title").val();
       const content = $("#content").val();

       const complaint = JSON.stringify({
              title: title,
              content: content
          });



            $.ajax({
                   type: 'POST',
                   url: 'proclaim',
                   data: complaint,
                   contentType: 'application/json'
               })
 }
