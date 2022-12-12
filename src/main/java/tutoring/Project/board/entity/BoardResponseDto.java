package tutoring.Project.board.entity;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import tutoring.Project.comment.entity.Comment;
import tutoring.Project.member.entity.MemberResponseDto;

@Getter
@Setter
public class BoardResponseDto {

    private Long id;
    private String title;
    private String content;

    private BoardState state;
    private BoardType type;

    private LocalDateTime publishAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    private MemberResponseDto member;
    private List<Comment> comments;
}
