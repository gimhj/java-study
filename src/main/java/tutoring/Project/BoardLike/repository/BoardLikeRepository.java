package tutoring.Project.BoardLike.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tutoring.Project.BoardLike.entity.BoardLike;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    @Override
    BoardLike save(BoardLike boardLike);

    @Override
    Optional<BoardLike> findById(Long BoardLikeId);
}
