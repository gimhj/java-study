package tutoring.Project.BoardLike.service;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tutoring.Project.BoardLike.entity.BoardLike;
import tutoring.Project.BoardLike.repository.BoardLikeRepository;
import tutoring.Project.board.entity.Board;
import tutoring.Project.member.entity.Member;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardLikeService {

    private final BoardLikeRepository boardLikeRepository;

    public BoardLike save(BoardLike boardLike) {

        return boardLikeRepository.save(boardLike);
    }

    public Optional<BoardLike> findById(Long boardLikeId) {

        return boardLikeRepository.findById(boardLikeId);
    }

    public BoardLike addBoardLike(Member member, Board board) {
        BoardLike boardLike = new BoardLike();
        boardLike.setMember(member);
        boardLike.setBoard(board);

        return this.save(boardLike);
    }

    public BoardLike updatedDeletedAt(BoardLike boardLike) {
        boardLike.setDeletedAt(LocalDateTime.now());

        return this.save(boardLike);
    }
}
