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