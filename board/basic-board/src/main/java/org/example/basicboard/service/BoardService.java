package org.example.basicboard.service;

import lombok.RequiredArgsConstructor;
import org.example.basicboard.domain.entity.Board;
import org.example.basicboard.domain.repository.BoardRepository;
import org.example.basicboard.dto.BoardDeleteRequestDto;
import org.example.basicboard.dto.BoardListItemResponseDto;
import org.example.basicboard.dto.BoardSearchRequestDto;
import org.example.basicboard.dto.BoardUpdateRequestDto;
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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final FileService fileService;

    public List<Board> getBoardList(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page-1, size, Sort.by("id").descending());

        return boardRepository.findAll(pageRequest).getContent();
    }

    public int getTotalBoards() {
        return (int) boardRepository.count();
    }

    @Transactional
    public void saveBoard(String userId, String title, String content, MultipartFile file) {
        String filePath = fileService.storeFile(file);

        boardRepository.save(
            Board.builder()
                    .userId(userId)
                    .title(title)
                    .content(content)
                    .filePath(filePath)
                    .created(LocalDateTime.now())
                    .build()
        );
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
    }

    @Transactional
    public void deleteBoard(long id, BoardDeleteRequestDto dto) {
        if (!boardRepository.existsById(id)) {
            throw new BoardNotFoundException("[BOARD] Board not found : " + id);
        }

        boardRepository.deleteById(id);
        fileService.deleteFile(dto.getFilePath());
    }

    public Page<BoardListItemResponseDto> searchBoard(BoardSearchRequestDto dto, Pageable pageable) {
        return boardRepository.searchBoards(dto, pageable);
    }

    public Board getBoardWithComments(long id) {
        return boardRepository.findWithComments(id).orElseThrow(() -> new BoardNotFoundException("[BOARD] Board not found : " + id));
    }
}
