let postLikeClick = (postId) => {

    let principalId = $("#principal-id").val();
    if (principalId == undefined) {
        alert("로그인이 필요합니다.");
        location.href = "/login-form";
        return; // 이걸 안붙이면 밑에 코드도 다 실행됨.
    }

    let isLike = $(`#my-heart`).hasClass("fa-solid");
    if (isLike) {
        postUnLike(postId);
    } else {
        postLike(postId);
    }
}

let postLike = async (postId) => {
    let response = await fetch(`/s/api/post/${postId}/love`, {
        method: "POST"
    })
    let responseParse = await response.json();
    if (response.status == 201) {
        $("#my-loveId").val(responseParse.loveId);
        $(`#my-heart`).removeClass("far");
        $(`#my-heart`).addClass("fa-solid");
    }
}

let postUnLike = async (postId) => {
    let loveId = $("#my-loveId").val()
    let response = await fetch(`/s/api/post/${postId}/love/${loveId}`, {
        method: "DELETE"
    });
    if (response.status == 200) {
        $(`#my-heart`).removeClass("fa-solid");
        $(`#my-heart`).addClass("far");
    }
}

// 게시글 삭제, 권한체크후 삭제 DELETE : /s/api/post/1
$("#btn-delete").click(() => {
    postDelete();
});

let postDelete = async () => {
    let postId = $("#postId").val();
    let pageOwnerId = $("#pageOwnerId").val();
    let response = await fetch(`/s/api/post/${postId}`, {
        method: "DELETE",
    });
    if (response.status == 200) {
        alert("삭제에 성공했습니다.");
        location.href = `/user/${pageOwnerId}/post`;
    } else {
        alert("삭제에 실패했습니다.");
    };
}