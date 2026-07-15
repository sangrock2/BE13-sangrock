package org.example.basicboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.basicboard.domain.entity.Board;
import org.example.basicboard.domain.entity.Comment;
import org.example.basicboard.domain.repository.BoardRepository;
import org.example.basicboard.domain.repository.CommentRepository;
import org.example.basicboard.dto.CommentDeleteRequestDto;
import org.example.basicboard.dto.CommentUpdateRequestDto;
import org.example.basicboard.dto.CommentWriteRequestDto;
import org.example.basicboard.exception.BoardNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void addComment(Long boardId, CommentWriteRequestDto dto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardNotFoundException("[BOARD] board not found : " + boardId));

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .userId(dto.getUserId())
                .created(LocalDateTime.now())
                .board(board)
                .build();

        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Long commentId, CommentUpdateRequestDto dto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new BoardNotFoundException("[COMMENT] comment not found : " + commentId));

        if (!comment.getUserId().equals(dto.getUserId())) {
            throw new IllegalArgumentException("[COMMENT] comment user id mismatch");
        }

        comment.update(dto.getContent());
    }

    @Transactional
    public void deleteComment(Long commentId, CommentDeleteRequestDto dto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("[COMMENT] comment not found : " + commentId));

        if (!comment.getUserId().equals(dto.getUserId())) {
            throw new IllegalArgumentException("[COMMENT] comment user id mismatch");
        }

        commentRepository.deleteById(commentId);
    }
}
