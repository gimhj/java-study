package tutoring.Project.comment.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tutoring.Project.board.entity.Board;
import tutoring.Project.comment.dto.CommentRequestDto;
import tutoring.Project.comment.entity.Comment;
import tutoring.Project.comment.repository.CommentRepository;
import tutoring.Project.member.entity.Member;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> findAll() {

        return commentRepository.findAll();
    }

    public Comment save(Member member, Board board, CommentRequestDto commentRequestDto) {
        Comment comment = new Comment();
        comment.setBoard(board);
        comment.setMember(member);
        comment.setContent(commentRequestDto.getContent());
        comment.setIsLock(commentRequestDto.getIsLock());

        return commentRepository.save(comment);
    }
}
