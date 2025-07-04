let index = {
    init: function(){
        $("#btn-save").on("click",() => {
                this.save();
        });
         $("#btn-delete").on("click", ()=>{
            this.deleteById();
        });
        $("#btn-update").on("click", ()=>{
            this.update();
        });
        $("#reply-content").on("keydown", (event) => {
            if (event.key === "Enter" && !event.shiftKey) {
                event.preventDefault(); // 기본 Enter 동작(줄바꿈) 방지
                this.replySave(); // 댓글 전송
            }
        });
    }

    ,save: function(){
        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
        };

        console.log(data);
        $.ajax({
            type: "POST",
            url: "/api/board",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"

        }).done(function(resp){ // 성공
            alert("글작성이 완료되었습니다.");
            location.href = "/";
        }).fail(function(error){ // 실패
            alert(JSON.stringify(error));
        });
    }

    ,deleteById: function(){
         let id = $("#btn-delete").data("id"); // 'id' 값을 가져옴

        console.log(id);

         $.ajax({
             type: "DELETE",
             url: "/api/board/" + id, // 서버에 DELETE 요청
             dataType: "json"
         }).done(function(resp){ // 성공
             alert("삭제가 완료되었습니다.");
             location.href = "/";
         }).fail(function(error){ // 실패
             alert("삭제 실패: " + JSON.stringify(error));
         });
    }

    ,update: function(){
        let id = $("#id").val();

        let data = {
          title: $("#title").val(),
          content: $("#content").val(),
        };

        $.ajax({
          type: "PUT",
          url: "/api/board/"+id,
          data: JSON.stringify(data),
          contentType: "application/json; charset=utf-8",
          dataType: "json"

        }).done(function(resp){ // 성공
          alert("글수정이 완료되었습니다.");
          location.href = "/";
        }).fail(function(error){ // 실패
          alert(JSON.stringify(error));
        });
    }

    ,replySave: function(){
        let data = {
            userId: $("#userId").val(),
            boardId: $("#boardId").val(),
            content: $("#reply-content").val(),
        };

        $.ajax({
            type: "POST",
            url: `/api/board/${data.boardId}/reply`,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"

        }).done(function(resp){ // 성공
            alert("댓글작성이 완료되었습니다.");
            location.href = `/board/${data.boardId}`;
        }).fail(function(error){ // 실패
            alert(JSON.stringify(error));
        });
    }

    ,replyDelete: function(boardId,replyId){
        $.ajax({
            type: "DELETE",
            url: `/api/board/${boardId}/reply/${replyId}`,
            dataType: "json"
        }).done(function(resp){ // 성공
            alert("댓글삭제 성공.");
            location.href = `/board/${boardId}`;
        }).fail(function(error){ // 실패
            alert(JSON.stringify(error));
        });
    }

}

$(document).ready(function() {
    $('.summernote').summernote({
        tabsize: 2,
        height: 500 // 높이 설정
    });
});

index.init();