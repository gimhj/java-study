package tutoring.Project.comment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tutoring.Project.board.entity.Board;
import tutoring.Project.member.entity.Member;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE comment SET deleted_at = CURRENT_TIME WHERE id=?")
@Where(clause = "deleted_at IS NULL")
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;
    private Boolean isLock;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments;
}
