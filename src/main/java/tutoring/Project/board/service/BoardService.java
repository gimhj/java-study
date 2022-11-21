package tutoring.Project.board.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tutoring.Project.board.entity.Board;
import tutoring.Project.board.entity.BoardDTO;
import tutoring.Project.board.repository.BoardRepository;
import tutoring.Project.member.entity.Member;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    public Board findOne(Long boardId) {
        return boardRepository.findOne(boardId);
    }

    public List<Board> findAll() {

        return boardRepository.findAll();
    }

    @Transactional
    public Board save(Board board) {

        boardRepository.save(board);
        return board;
    }

    @Transactional
    public Board update(Long boardId, BoardDTO boardDTO) {

        Board board = this.findOne(boardId);
        board.setState(boardDTO.getState());
        board.setType(boardDTO.getType());
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setPublishAt(boardDTO.getPublishAt());

        return board;
    }

    @Transactional
    public void updateDeletedAt(Long boardId) {

        Board board = this.findOne(boardId);
        board.setDeletedAt(LocalDateTime.now());
    }
}
