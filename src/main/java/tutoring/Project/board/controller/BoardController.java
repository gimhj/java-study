package tutoring.Project.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tutoring.Project.board.entity.Board;
import tutoring.Project.board.entity.BoardDTO;
import tutoring.Project.board.service.BoardService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @GetMapping("")
    @Operation(summary = "전체조회")
    public List<Board> index() {

        return boardService.findAll();
    }


    @PostMapping("")
    @Operation(summary = "게시글 생성")
    public Board store(@Valid @RequestBody BoardDTO boardDTO) {

        Board board = new Board();
        board.setState(boardDTO.getState());
        board.setType(boardDTO.getType());
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setPublishAt(boardDTO.getPublishAt());

        boardService.save(board);

        return board;
    }

    @PutMapping("/{boardId}")
    @Operation(summary = "게시글 수정")
    public Board update(@PathVariable("boardId") Long boardId, @Valid @RequestBody BoardDTO boardDTO) {

        Board board = boardService.update(boardId, boardDTO);

        return board;
    }

    @PutMapping("/{boardId}/destroy")
    @Operation(summary = "게시글 삭제")
    public void destroy(@PathVariable("boardId") Long boardId) {

        boardService.updateDeletedAt(boardId);
    }
}
