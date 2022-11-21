package tutoring.Project.board.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import tutoring.Project.board.entity.Board;

@Repository
public class BoardRepository {

    @PersistenceContext
    private EntityManager em;

    public Board save(Board board) {

        em.persist(board);

        return board;
    }

    public Board findOne(Long id) {

        return em.find(Board.class, id);
    }

    public List<Board> findAll() {

        return em.createQuery("select b from Board b", Board.class)
            .getResultList();
    }
}
