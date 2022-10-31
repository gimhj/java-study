package tutoring.Project.controller;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tutoring.Project.domain.Board;
import tutoring.Project.domain.BoardStatus;
import tutoring.Project.domain.BoardType;
import tutoring.Project.service.BoardService;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public List<Board> index() {

        return boardService.findAll();
    }

    @PostMapping("/board")
    public void save(@RequestBody String title, String content, BoardType type, LocalDateTime publishAt) {
        Board board = new Board();
        board.setStatus(BoardStatus.CLOSE);
        board.setType(type);
        board.setTitle(title);
        board.setContent(content);
        board.setPublishAt(publishAt);

        boardService.save(board);
    }
}
