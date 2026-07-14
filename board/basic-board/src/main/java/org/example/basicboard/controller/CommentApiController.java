package org.example.basicboard.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.basicboard.constant.SessionConst;
import org.example.basicboard.dto.CommentUpdateRequestDto;
import org.example.basicboard.dto.CommentWriteRequestDto;
import org.example.basicboard.service.CommentService;
import org.springframework.web.bind.annotation.*;

@Tag(name = "댓글 API", description = "게시글에 댓글 달기")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/{boardId}/comments")
public class CommentApiController {
    private final CommentService commentService;

    @PostMapping
    public void addComment(@PathVariable long boardId, @RequestBody CommentWriteRequestDto dto) {
        commentService.addComment(boardId, dto);
    }

    @PatchMapping("/{commentId}")
    public void updateComment(@PathVariable long commentId, @RequestBody CommentUpdateRequestDto dto) {
        commentService.updateComment(commentId, dto);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable long commentId, HttpSession session) {
        String userId = (String) session.getAttribute(SessionConst.USER_ID);

        commentService.deleteComment(commentId, userId);
    }
}
