package org.example.spring_mission_join.service;

import lombok.RequiredArgsConstructor;
import org.example.spring_mission_join.domain.entity.Board;
import org.example.spring_mission_join.domain.repository.BoardRepository;
import org.example.spring_mission_join.dto.BoardDeleteRequestDto;
import org.example.spring_mission_join.dto.BoardUpdateRequestDto;
import org.example.spring_mission_join.dto.BoardWriteRequestDto;
import org.example.spring_mission_join.exception.BoardNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final FileService fileService;

    public List<Board> getBoardList(int page, int size) {
        PageRequest pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());

        return boardRepository.findAll(pageable).getContent();
    }

    public int getTotalBoards() {
        return (int) boardRepository.count();
    }

    public Board getBoardDetail(long id) {
        return boardRepository.findById(id).orElseThrow(() -> new BoardNotFoundException("[BOARD] Board not found : " + id));
    }

    @Transactional
    public void saveBoard(BoardWriteRequestDto dto) {
        String filePath = fileService.storeFile(dto.getFile());

        boardRepository.save(
                Board.builder()
                        .userId(dto.getUserId())
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .filePath(filePath)
                        .created(LocalDateTime.now())
                        .build()
        );
    }

    @Transactional
    public void deleteBoard(long id, BoardDeleteRequestDto dto) {
        if (!boardRepository.existsById(id)) {
            throw new BoardNotFoundException("[BOARD] Board not found : " + id);
        }

        boardRepository.deleteById(id);
        fileService.deleteFile(dto.getFilePath());
    }

    @Transactional
    public void updateBoard(long id, BoardUpdateRequestDto dto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new BoardNotFoundException("[BOARD] Board not found : " + id));

        String filePath = board.getFilePath();

        if (dto.isFileFlag()) {
            fileService.deleteFile(filePath);
            filePath = fileService.storeFile(dto.getFile());
        }

        board.update(dto.getTitle(), dto.getContent(), filePath);
    }
}
