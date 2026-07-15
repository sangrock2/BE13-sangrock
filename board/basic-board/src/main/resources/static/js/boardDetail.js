$(document).ready(() => {
    checkSession();
    loadBoardDetail();
});

let editArticle = () => {
    let resourceId = $('#hiddenId').val();
    window.location.href = "/update/" + resourceId;
}

let deleteArticle = () => {
    let resourceId = $('#hiddenId').val();
    let filePath = $('#hiddenFilePath').val();

    $.ajax({
        type: 'DELETE',
        url: '/api/boards/' + resourceId,
        data: JSON.stringify({ filePath: filePath }),
        contentType: 'application/json',
        success: () => {
            alert('리소스가 성공적으로 삭제되었습니다.');
            window.location.href = '/';
        },
        error: (error) => {
            alert('리소스 삭제 중 오류가 발생했습니다.');
            console.error('Error:', error);
        }
    });
}

let checkSession = () => {
    let hUserId = $('#hiddenUserId').val();

    if (hUserId == null || hUserId === '')
        window.location.href = "/members/login";
}

let loadBoardDetail = () => {
    let hId = $('#hiddenId').val();
    let hUserId = $('#hiddenUserId').val();

    $.ajax({
        type: 'GET',
        url: '/api/boards/' + hId + '/with-comments',
        success: (response) => {
            $('#title').text(response.title);
            $('#content').text(response.content);
            $('#userId').text(response.userId);
            $('#created').text(response.created);

            if (hUserId != response.userId) {
                $('#editBtn').prop('disabled', true);
                $('#deleteBtn').prop('disabled', true);
            }

            $('#fileList').empty();

            if (response.filePath && response.filePath.length > 0) {
                let filePath = response.filePath;
                $('#hiddenFilePath').val(filePath)

                let normalized = filePath.replace(/\\/g, '/');
                let fileName = normalized.substring(normalized.lastIndexOf('/') + 1);
                let fileElement = `
                    <li>
                        <a href="/api/boards/file/download/${fileName}">${fileName}</a>
                    </li>`;
                $('#fileList').append(fileElement);
            } else {
                $('#fileList').append('<li>첨부된 파일이 없습니다.</li>');
            }

            renderComments(response.comments);
        },
        error: (error) => {
            console.error('오류 발생:', error);
            alert('상세 데이터를 불러오는데 오류가 발생했습니다.');
        }
    });
}

let renderComments = (comments) => {
    const $list = $('#commentList');
    const hUserId = $('#hiddenUserId').val();

    $list.empty();
    $('#commentCount').text(comments && comments.length > 0 ? comments.length : '');

    if (comments == null || comments.length <= 0) {
        $list.append('<li class="no-comment">아직 댓글이 없습니다. 첫 댓글을 남겨보세요!</li>');
        return;
    }

    comments.forEach((c) => {
        const disabled = hUserId != c.userId ? 'disabled' : '';

        $list.append(`
            <li class="comment-item" data-comment-id="${c.id}">
                <div class="comment-meta">
                    <div class="comment-meta-left">
                        <strong>${c.userId}</strong>
                        <span class="comment-date">${c.created}</span>
                    </div>

                    <div class="comment-actions">
                        <button type="button"
                                class="btn comment-edit-btn"
                                onclick="startEditComment(${c.id})"
                                ${disabled}>수정</button>

                        <button type="button"
                                class="btn comment-delete-btn"
                                onclick="deleteComment(${c.id})"
                                ${disabled}>삭제</button>
                    </div>
                </div>

                <p id="comment-content-${c.id}" class="comment-content"></p>

                <div id="comment-edit-box-${c.id}" class="comment-edit-box" style="display: none;">
                    <textarea id="comment-edit-text-${c.id}" rows="3"></textarea>
                    <div class="comment-edit-actions">
                        <button type="button" class="btn comment-save-btn" onclick="submitCommentUpdate(${c.id})">저장</button>
                        <button type="button" class="btn comment-cancel-btn" onclick="cancelEditComment(${c.id})">취소</button>
                    </div>
                </div>
            </li>
        `);

        $('#comment-content-' + c.id).text(c.content);
    });
}

let startEditComment = (commentId) => {
    let content = $('#comment-content-' + commentId).text();

    $('#comment-edit-text-' + commentId).val(content);
    $('#comment-content-' + commentId).hide();
    $('#comment-edit-box-' + commentId).show();
}

let cancelEditComment = (commentId) => {
    $('#comment-edit-box-' + commentId).hide();
    $('#comment-content-' + commentId).show();
}

let submitCommentUpdate = (commentId) => {
    let hId = $('#hiddenId').val();
    let hUserId = $('#hiddenUserId').val();
    let content = $('#comment-edit-text-' + commentId).val();

    if (content == null || content.trim() === '') {
        alert('댓글 내용을 입력해주세요.');
        return;
    }

    $.ajax({
        type: 'PATCH',
        url: '/api/boards/' + hId + '/comments/' + commentId,
        contentType: 'application/json',
        data: JSON.stringify({
            userId: hUserId,
            content: content
        }),
        success: () => {
            loadBoardDetail();
        },
        error: (error) => {
            console.error('오류 발생:', error);
            alert('댓글 수정 중 오류가 발생했습니다.');
        }
    });
}

let deleteComment = (commentId) => {
    let hId = $('#hiddenId').val();
    let hUserId = $('#hiddenUserId').val();

    if (!confirm('댓글을 삭제하시겠습니까?')) {
        return;
    }

    $.ajax({
        type: 'DELETE',
        url: '/api/boards/' + hId + '/comments/' + commentId,
        contentType: 'application/json',
        data: JSON.stringify({
            userId: hUserId
        }),
        success: () => {
            loadBoardDetail();
        },
        error: (error) => {
            console.error('오류 발생:', error);
            alert('댓글 삭제 중 오류가 발생했습니다.');
        }
    });
}

let submitComment = () => {
    let hId = $('#hiddenId').val();
    let hUserId = $('#hiddenUserId').val();
    let content = $('#commentContent').val();

    if (content == null || content.trim() === '') {
        alert('댓글 내용을 입력해주세요.');
        return;
    }

    $.ajax({
        type: 'POST',
        url: '/api/boards/' + hId + '/comments',
        contentType: 'application/json',
        data: JSON.stringify({ userId: hUserId, content: content }),
        success: () => {
            $('#commentContent').val('');
            loadBoardDetail();
        },
        error: (error) => {
            console.error('오류 발생:', error);
            alert('댓글 등록 중 오류가 발생했습니다.');
        }
    });
}
