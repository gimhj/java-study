package tutoring.Project.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tutoring.Project.board.entity.Board;
import tutoring.Project.board.entity.BoardDto;
import tutoring.Project.board.entity.BoardResponseDto;
import tutoring.Project.board.service.BoardService;
import tutoring.Project.member.entity.Member;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final ModelMapper modelMapper;

    @GetMapping("")
    @Operation(summary = "전체조회")
    @PreAuthorize("isAuthenticated()")
    public List<BoardResponseDto> index() {
        List<Board> boards = boardService.findAll();

        List<BoardResponseDto> result = boards.stream()
            .map(board -> modelMapper.map(board, BoardResponseDto.class))
            .collect(Collectors.toList());

        return result;
    }

    @PostMapping("")
    @Operation(summary = "게시글 생성")
    @PreAuthorize("isAuthenticated()")
    public BoardResponseDto store(@Valid @RequestBody BoardDto boardDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();

        Board board = new Board();
        board.setMember(member);
        board.setState(boardDTO.getState());
        board.setType(boardDTO.getType());
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setPublishAt(boardDTO.getPublishAt());

        boardService.save(board);

        return modelMapper.map(board, BoardResponseDto.class);
    }

    @PutMapping("/{boardId}")
    @Operation(summary = "게시글 수정")
    @PreAuthorize("isAuthenticated() and hasPermission(#boardId, 'tutoring.Project.board.entity.Board', 'update')")
    public BoardResponseDto update(
        @PathVariable("boardId") Long boardId,
        @Valid @RequestBody BoardDto boardDTO
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();

        Board board = boardService.update(boardId, member, boardDTO);

        return modelMapper.map(board, BoardResponseDto.class);
    }

    @PutMapping("/{boardId}/destroy")
    @Operation(summary = "게시글 삭제")
    public void destroy(@PathVariable("boardId") Long boardId) {

        boardService.updateDeletedAt(boardId);
    }
}
