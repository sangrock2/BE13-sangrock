package org.example.spring_mission_join.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.spring_mission_join.domain.entity.Board;

import java.util.List;

@Getter
@Builder
public class BoardListResponseDto {
    private List<Board> boards;
    private boolean last;
    private int totalPages;
}
