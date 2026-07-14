package org.example.basicboard.mapper;

import org.example.basicboard.domain.entity.Board;
import org.example.basicboard.domain.entity.Comment;
import org.example.basicboard.dto.BoardWithCommentsResponseDto;
import org.example.basicboard.dto.CommentResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BoardMapper {
    public BoardWithCommentsResponseDto toBoardWithCommentsDto(Board board) {
        List<CommentResponseDto> comments = board.getComments().stream().map(this::toCommentResponseDto).toList();

        return BoardWithCommentsResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .userId(board.getUserId())
                .created(board.getCreated())
                .filePath(board.getFilePath())
                .comments(comments)
                .build();
    }

    public CommentResponseDto toCommentResponseDto(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .userId(comment.getUserId())
                .content(comment.getContent())
                .created(comment.getCreated())
                .build();
    }

}
