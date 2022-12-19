package tutoring.Project.comment.service;

import java.util.List;
import java.util.Optional;
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


    public List<Comment> findAll(Long boardId) {
        List<Comment> comments = commentRepository.findCommentsByBoardId(boardId);

        return comments;
    }

    public Comment save(Member member, Board board, CommentRequestDto commentRequestDto) {
        Comment comment = new Comment();
        comment.setBoard(board);
        comment.setMember(member);
        comment.setContent(commentRequestDto.getContent());
        comment.setIsLock(commentRequestDto.getIsLock());

        return commentRepository.save(comment);
    }

    public Optional<Comment> findById(Long commentId) {

        return commentRepository.findById(commentId);
    }

    public Comment addReplyComment(
        Member member,
        Board board,
        Comment comment,
        CommentRequestDto commentRequestDto
    ) {
        Comment replyComment = new Comment();
        replyComment.setBoard(board);
        replyComment.setMember(member);
//        replyComment.setParentComment(comment);
        replyComment.setContent(commentRequestDto.getContent());
        replyComment.setIsLock(commentRequestDto.getIsLock());

        return commentRepository.save(replyComment);
    }

    public Comment create(Member member, Comment comment) {
        comment.setMember(member);
        commentRepository.save(comment);

        if (comment.getHead() == null) {
            comment.setHead(comment.getId());
            commentRepository.save(comment);
        }

        return comment;
    }

    public Comment create(Member member, Comment comment, Comment parent) {
        comment.setHead(parent.getHead());
        comment.setReply(getReplyCode(parent));

        return create(member, comment);
    }


    private String getReplyCode(Comment parent) {
        String codeStr = "abcdefghijklmnopqrstuvwxyz";
        char[] chars = codeStr.toCharArray();

        String prevCode = commentRepository.getLastReplyCode(parent.getHead(), parent.getReply());
        if (prevCode == null) {
            return parent.getReply() + chars[0];
        }
        char prevChar = prevCode.toCharArray()[prevCode.length() - 1];

        char lastChar = chars[chars.length - 1];
        if (lastChar == prevChar) {
            throw new IllegalStateException("답글 생성 에러");
        }
        int index = codeStr.indexOf(prevChar);

        return parent.getReply() + chars[index + 1];
    }
}
