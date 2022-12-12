package tutoring.Project.comment.contoller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tutoring.Project.board.entity.Board;
import tutoring.Project.board.service.BoardService;
import tutoring.Project.comment.dto.CommentRequestDto;
import tutoring.Project.comment.entity.Comment;
import tutoring.Project.comment.service.CommentService;
import tutoring.Project.member.entity.Member;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final BoardService boardService;
    private final ModelMapper modelMapper;

    @GetMapping("")
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public List<Comment> index() {

        return commentService.findAll();
    }

    @PostMapping("")
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public CommentRequestDto store(@Valid CommentRequestDto commentRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();
        Board board = boardService.findOne(commentRequestDto.getBoardId());

        Comment comment = commentService.save(member, board, commentRequestDto);

        return modelMapper.map(comment, CommentRequestDto.class);
    }
}
