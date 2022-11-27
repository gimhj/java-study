package tutoring.Project.member.entity;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponseDto {

    private Long id;
    private String name;
    private String nickname;
    private String email;

    private LocalDateTime loginAt;
    private LocalDateTime createdAt;
}
