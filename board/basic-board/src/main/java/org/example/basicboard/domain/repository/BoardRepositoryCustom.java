package org.example.basicboard.domain.repository;

import org.example.basicboard.dto.BoardListItemResponseDto;
import org.example.basicboard.dto.BoardSearchRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {
    Page<BoardListItemResponseDto> searchBoards(BoardSearchRequestDto requestDto, Pageable pageable);


}
