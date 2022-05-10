let postLikeClick = (id) => {
    let isLike = $(`#heart-${id}`).hasClass("fa-solid");
    if (isLike) {
        postUnLike(id);
    } else {
        postLike(id);
    }
}

let postLike = (id) => {
    // fetch();
    $(`#heart-${id}`).removeClass("far");
    $(`#heart-${id}`).addClass("fa-solid");
}

let postUnLike = (id) => {
    // fetch();
    $(`#heart-${id}`).removeClass("fa-solid");
    $(`#heart-${id}`).addClass("far");
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