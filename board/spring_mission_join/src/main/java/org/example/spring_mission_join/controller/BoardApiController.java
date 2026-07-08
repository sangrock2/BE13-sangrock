package org.example.spring_mission_join.controller;

import lombok.RequiredArgsConstructor;
import org.example.spring_mission_join.domain.entity.Board;
import org.example.spring_mission_join.dto.*;
import org.example.spring_mission_join.service.BoardService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardApiController {
    private final BoardService boardService;

    @GetMapping
    public BoardListResponseDto getBoardList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        List<Board> boards = boardService.getBoardList(page, size);

        int totalBoards = boardService.getTotalBoards();
        int totalPages = (int) Math.ceil((double) totalBoards / size);

        boolean last = page >= totalPages;

        return BoardListResponseDto.builder()
                .boards(boards)
                .last(last)
                .totalPages(totalPages)
                .build();
    }

    @GetMapping("/{id}")
    public BoardDetailResponseDto getBoardDetail(@PathVariable long id) {
        Board board = boardService.getBoardDetail(id);

        return BoardDetailResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .created(board.getCreated())
                .userId(board.getUserId())
                .filePath(board.getFilePath())
                .build();
    }

    @GetMapping("/file/download/{filenName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filenName) {
        Resource resource = boardService.downloadFile(filenName);

        String encoded = URLEncoder.encode(resource.getFilename(), StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename*=UTF-8''" + encoded)
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable long id, @ModelAttribute BoardDeleteRequestDto dto) {
        boardService.deleteBoard(id, dto);
    }

    @PutMapping("/{id}")
    public void updateBoard(@PathVariable long id, @ModelAttribute BoardUpdateRequestDto dto) {
        boardService.updateBoard(id, dto);
    }

    @PostMapping
    public void createBoard(@ModelAttribute BoardWriteRequestDto dto) {
        boardService.saveBoard(dto);
    }


}
