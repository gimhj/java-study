package tutoring.Project.comment.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tutoring.Project.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Override
    Comment save(Comment comment);

    @Override
    List<Comment> findAll();
}
