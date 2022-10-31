package tutoring.Project.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tutoring.Project.domain.Board;
import tutoring.Project.exception.AlreadyExistException;
import tutoring.Project.repository.BoardRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void save(Board board) {
        validateDuplicateTitle(board);
        boardRepository.save(board);
    }

    private void validateDuplicateTitle(Board board) {
        List<Board> findBoards = boardRepository.findByTitle(board.getTitle());
        if (!findBoards.isEmpty()) {
            throw new AlreadyExistException("이미 존재하는 글입니다.");
        }
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Board findOne(Long boardId) {
        return boardRepository.findOne(boardId);
    }
}
