package tutoring.Project.comment.contoller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tutoring.Project.board.entity.Board;
import tutoring.Project.board.service.BoardService;
import tutoring.Project.comment.dto.CommentRequestDto;
import tutoring.Project.comment.dto.CommentResponseDto;
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
    public CommentResponseDto store(@Valid CommentRequestDto commentRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();
        Optional<Board> board = boardService.findById(commentRequestDto.getBoardId());

        if (board.isEmpty()) {
            throw new IllegalStateException();
        }

        Comment comment = commentService.save(member, board.get(), commentRequestDto);

        return modelMapper.map(comment, CommentResponseDto.class);
    }

    @PostMapping("/{commentId}/reply")
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public CommentResponseDto reply(
        @PathVariable("commentId") Long commentId,
        @Valid CommentRequestDto commentRequestDto
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();
        Optional<Board> board = boardService.findById(commentRequestDto.getBoardId());
        Optional<Comment> comment = commentService.findById(commentId);

        if (board.isEmpty()) {
            throw new IllegalStateException();
        }

        if (comment.isEmpty()) {
            throw new IllegalStateException("회신할 댓글이 존재하지 않습니다.");
        }

        Comment replyComment = commentService.addReplyComment(
            member,
            board.get(),
            comment.get(),
            commentRequestDto
        );

        return modelMapper.map(replyComment, CommentResponseDto.class);
    }
}
