package tutoring.Project.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tutoring.Project.board.entity.Board;
import tutoring.Project.board.entity.BoardStatus;
import tutoring.Project.board.entity.BoardType;
import tutoring.Project.board.exception.AlreadyExistException;
import tutoring.Project.board.repository.BoardRepository;
import tutoring.Project.board.service.BoardService;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired EntityManager em;
    @Autowired
    BoardService boardService;
    @Autowired BoardRepository boardRepository;

    @Test
    public void 글_생성() {
        // given
        // when
        Board board = createBoard("테스트 글", "테스트 글입니다", BoardType.NOTICE, LocalDateTime.now().plusWeeks(2));

        // then
        assertEquals(board, boardRepository.findOne(board.getId()));
    }

    @Test
    public void 중복_글_생성_에러() throws Exception {
        //given
        //when
        Board board1 = createBoard("테스트 글", "첫번째 글입니다", BoardType.NOTICE, LocalDateTime.now().plusWeeks(2));


        //then
        Assertions.assertThrows(AlreadyExistException.class, () -> {
            Board board2 = createBoard("테스트 글", "두번째 글입니다", BoardType.GUIDE, LocalDateTime.now().plusWeeks(1));
        });
    }

    @Test
    public void 게시글_전체_조회() {
        // given
        Board board1 = createBoard("테스트 글1", "첫번째 글입니다", BoardType.NOTICE, LocalDateTime.now().plusWeeks(2));
        Board board2 = createBoard("테스트 글2", "두번째 글입니다", BoardType.GUIDE, LocalDateTime.now().plusWeeks(1));

        // when
        List<Board> result = boardService.findAll();

        // then
        assertThat(result.size()).isEqualTo(2);
    }

    private Board createBoard(String title, String content, BoardType type, LocalDateTime publishAt) {
        Board board = new Board();
        board.setStatus(BoardStatus.OPEN);
        board.setType(type);
        board.setTitle(title);
        board.setContent(content);
        board.setPublishAt(publishAt);
        boardService.save(board);
        return board;
    }
}