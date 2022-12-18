package tutoring.Project.board.service;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tutoring.Project.board.entity.Board;
import tutoring.Project.board.entity.BoardDto;
import tutoring.Project.board.repository.BoardRepository;
import tutoring.Project.member.entity.Member;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    public Optional<Board> findById(Long boardId) {

        return boardRepository.findById(boardId);
    }

    public Page<Board> findAll(Pageable pageable) {

        return boardRepository.findAll(pageable);
    }

    @Transactional
    public Board save(Board board) {
        boardRepository.save(board);

        return board;
    }

    @Transactional
    public Optional<Board> update(Long boardId, Member member, BoardDto boardDTO) {
        Optional<Board> board = boardRepository.findById(boardId);

        if (board.isEmpty()) {
            throw new IllegalStateException();
        }

        board.get().setMember(member);
        board.get().setState(boardDTO.getState());
        board.get().setType(boardDTO.getType());
        board.get().setTitle(boardDTO.getTitle());
        board.get().setContent(boardDTO.getContent());
        board.get().setPublishAt(boardDTO.getPublishAt());

        return board;
    }

    @Transactional
    public void updateDeletedAt(Long boardId) {
        Optional<Board> board = boardRepository.findById(boardId);

        if (board.isEmpty()) {
            throw new IllegalStateException();
        }
        board.get().setDeletedAt(LocalDateTime.now());
    }
}
