package tutoring.Project.repository;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tutoring.Project.domain.Board;
import tutoring.Project.domain.BoardStatus;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    public Long save(Board board) {
        em.persist(board);
        return board.getId();
    }

    public Board findOne(Long id) {
        return em.find(Board.class, id);
    }

    public List<Board> findAll() {
        return em.createQuery(
            "select b from Board b where b.status = tutoring.Project.domain.BoardStatus.OPEN",
                Board.class)
            .getResultList();
    }

    public List<Board> findByTitle(String title) {
        return em.createQuery("select b from Board b where b.title = :title", Board.class)
            .setParameter("title", title)
            .getResultList();
    }
}
