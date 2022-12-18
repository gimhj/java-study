package tutoring.Project.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tutoring.Project.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
