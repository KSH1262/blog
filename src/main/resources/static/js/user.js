let index = {
    init: function(){
        $("#btn-join").on("click", ()=>{
            this.save();
        });
        $("#btn-update").on("click", ()=>{
            this.update();
        });

    }

    ,save: function() {
         let data = {
             username: $("#username").val(),
             password: $("#password").val(),
             email: $("#email").val()
         };

         $.ajax({
             type: "POST",
             url: "/auth/join",
             data: JSON.stringify(data),
             contentType: "application/json; charset=utf-8",
             dataType: "json"
         }).done(function(resp) {
             alert(resp.message);
             location.href = "/";
         }).fail(function(xhr) {
             if (xhr.status === 400) {
                 const errors = xhr.responseJSON;
                 let message = "회원가입 실패\n";
                 for (const field in errors) {
                     message += `• ${errors[field]}\n`;
                 }
                 alert(message);
             } else {
                 alert("서버 오류가 발생했습니다.");
             }
         });
     }

    ,update: function(){
        let data = {
            id: $("#id").val(),
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };

        $.ajax({
            type: "PUT",
            url: "/user",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(resp){
            alert("회원수정이 완료되었습니다.");
            location.href = "/";
        }).fail(function(error){ // 실패
            alert(JSON.stringify(error));
        });

    }
}

index.init()