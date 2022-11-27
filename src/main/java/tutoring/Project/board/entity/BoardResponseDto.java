package tutoring.Project.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import tutoring.Project.member.entity.Member;

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

    @JsonIgnore
    private Member member;
}
