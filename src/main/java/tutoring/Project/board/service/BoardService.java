package tutoring.Project.board.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tutoring.Project.board.entity.Board;
import tutoring.Project.board.entity.BoardDto;
import tutoring.Project.board.repository.BoardJpaRepository;
import tutoring.Project.board.repository.BoardRepository;
import tutoring.Project.member.entity.Member;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardJpaRepository BoardJpaRepository;

    public Board findOne(Long boardId) {
        return boardRepository.findOne(boardId);
    }

    public Page<Board> findAll(Pageable pageable) {

        return BoardJpaRepository.findAll(pageable);
    }

    @Transactional
    public Board save(Board board) {

        boardRepository.save(board);
        return board;
    }

    @Transactional
    public Board update(Long boardId, Member member, BoardDto boardDTO) {

        Board board = this.findOne(boardId);
        board.setMember(member);
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
