package tutoring.Project.member.repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import tutoring.Project.member.entity.Member;

@Repository
@Slf4j
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Member save(Member member) {

        em.persist(member);

        return member;
    }

    public Member findOne(Long id) {

        return em.find(Member.class, id);
    }

    public List<Member> findAll() {

        return em.createQuery("select m from Member m", Member.class)
            .getResultList();
    }

    public Optional<Member> findByEmail(String email) {

        return em.createQuery("select m from Member m where m.email = :email", Member.class)
            .setParameter("email", email)
            .getResultList()
            .stream()
            .findFirst();
    }

    public Member findByEmailForAuth(String email) {

        return em.createQuery("select m from Member m where m.email = :email", Member.class)
            .setParameter("email", email)
            .getSingleResult();
    }
}
