package tutoring.Project.comment.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tutoring.Project.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Override
    Comment save(Comment comment);

    @Override
    List<Comment> findAll();

    @Override
    Optional<Comment> findById(Long commentId);

    List<Comment> findCommentsByBoardId(Long boardId);

    @Query("select max(reply) from Comment as c where c.head = :head and c.reply like CONCAT(:code, '_')")
    String getLastReplyCode(Long head, String code);
}
