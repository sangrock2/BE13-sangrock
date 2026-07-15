package org.example.basicboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.basicboard.domain.entity.Board;
import org.example.basicboard.domain.repository.BoardRepository;
import org.example.basicboard.dto.*;
import org.example.basicboard.exception.BoardNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final FileService fileService;

    public List<Board> getBoardList(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("id").descending());

        return boardRepository.findAll(pageRequest).getContent();
    }

    public int getTotalBoards() {
        return (int) boardRepository.count();
    }

    @Transactional
    public void saveBoard(String userId, String title, String content, MultipartFile file) {
        String filePath = fileService.storeFile(file);

        Board savedBoard = boardRepository.save(
                Board.builder()
                        .userId(userId)
                        .title(title)
                        .content(content)
                        .filePath(filePath)
                        .created(LocalDateTime.now())
                        .build()
        );

        log.info("Board created: boardId={}, userId={}, hasFile={}", savedBoard.getId(), userId, filePath != null);
    }

    public Board getBoardDetail(long id) {
        return boardRepository.findById(id).orElseThrow(() -> new BoardNotFoundException("[BOARD] Board not found : " + id));
    }

    @Transactional
    public void UpdateBoard(long id, BoardUpdateRequestDto dto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new BoardNotFoundException("[BOARD] Board not found : " + id));

        String filePath = board.getFilePath();

        if (dto.isFileFlag()) {
            fileService.deleteFile(filePath);
            filePath = fileService.storeFile(dto.getFile());
        }

        board.update(dto.getTitle(), dto.getContent(), filePath);
        log.info("Board updated: boardId={}, hasFileChange={}, hasFile={}", id, dto.isFileFlag(), filePath != null);
    }

    @Transactional
    public void deleteBoard(long id, BoardDeleteRequestDto dto) {
        if (!boardRepository.existsById(id)) {
            throw new BoardNotFoundException("[BOARD] Board not found : " + id);
        }

        boardRepository.deleteById(id);
        fileService.deleteFile(dto.getFilePath());
        log.info("Board deleted: boardId={}, hadFile={}", id, dto.getFilePath() != null && !dto.getFilePath().isBlank());
    }

    public Page<BoardListItemResponseDto> searchBoard(BoardSearchRequestDto dto, Pageable pageable) {
        return boardRepository.searchBoards(dto, pageable);
    }

    public Board getBoardWithComments(long id) {
        return boardRepository.findWithComments(id).orElseThrow(() -> new BoardNotFoundException("[BOARD] Board not found : " + id));
    }

    public List<BoardAuthorStatsResponseDto> getAuthorStats(long minCount) {
        return boardRepository.countByBoardAuthor(minCount);
    }
}
