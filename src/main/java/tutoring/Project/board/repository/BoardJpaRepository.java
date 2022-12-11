package tutoring.Project.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tutoring.Project.board.entity.Board;

@Repository
public interface BoardJpaRepository extends JpaRepository<Board, Long> {

    @Override
    Page<Board> findAll(Pageable pageable);
}
