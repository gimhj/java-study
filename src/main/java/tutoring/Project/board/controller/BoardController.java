package tutoring.Project.board.controller;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tutoring.Project.board.entity.Board;
import tutoring.Project.board.entity.BoardStatus;
import tutoring.Project.board.entity.BoardType;
import tutoring.Project.board.service.BoardService;

@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards")
    public String list(Model model) {
        List<Board> boards = boardService.findAll();
        model.addAttribute("boards", boards);
        return "boards/index";
    }

    @PostMapping("/")
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
